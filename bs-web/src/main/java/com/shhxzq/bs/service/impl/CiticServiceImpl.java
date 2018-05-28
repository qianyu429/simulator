package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.mapping.citicb1.ApplyNewCardRequest;
import com.shhxzq.bs.mapping.citicb1.ApplyNewCardResponse;
import com.shhxzq.bs.mapping.citicb1.BasicCheckRequest;
import com.shhxzq.bs.mapping.citicb1.BasicCheckResponse;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.CiticService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.TransactionService;
import com.shhxzq.bs.util.DesUtil;
import com.shhxzq.bs.util.StreamUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wanglili on 2017/03/03.
 */
@Component
@Log4j2
public class CiticServiceImpl implements CiticService {

    private static final String ENCODING = "UTF-8";

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

    /**
     * 中信联名卡卡bin
     */
    private static String cardBin = "622918";

    //中信加密key
    private static String desKey = "qwertyui";

    private XStream requestParser;

    private XStream responseParser;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private BankService bankService;

    /**
     * 基础校验接口
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public String handleBasicCheck(HttpServletRequest request) throws Exception {
        //init xstream
        log.info("=========== 中信联名卡基础校验开始 ============");
        requestParser = getXStreamInstance();
        requestParser.processAnnotations(BasicCheckRequest.class);
        responseParser = getXStreamInstance();
        responseParser.processAnnotations(BasicCheckResponse.class);
        byte[] requestBody = StreamUtil.readBytes(request.getInputStream());
        String requestString = new String(requestBody, ENCODING);
        log.info("Receive the request: {}", requestString);
        String respStr = processBasicCheck(requestString);
        log.info("Response is {}", respStr);
        return respStr;
    }

    /**
     * 新卡申请接口
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public String handleApplyNewCard(HttpServletRequest request) throws Exception {
        //init xstream
        log.info("=========== 新卡申请开始 ============");
        requestParser = getXStreamInstance();
        requestParser.processAnnotations(ApplyNewCardRequest.class);
        responseParser = getXStreamInstance();
        responseParser.processAnnotations(ApplyNewCardResponse.class);
        byte[] requestBody = StreamUtil.readBytes(request.getInputStream());
        String requestString = new String(requestBody, ENCODING);
        log.info("Receive the request: {}", requestString);
        String respStr = processApplyNewCard(requestString);
        log.info("Response is {}", respStr);
        return respStr;
    }

    @Override
    public HashMap<String, String> handleApplySecondCard(String partner_apply_id){
        log.info("=========== 二卡申请结果返回开始 ============");
        //默认返回成功
        String errCode = "3001";
        String errMsg = "提交成功";

        //从数据库查出选中的错误码配置
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CITICB1);
        String bankCode = bank.getCode();
        Config config = configService.findBankSelectConfig(bankCode, TransTypeEnum.REDEEM.getType()); //(bankCode + "-select-redeem");

        if (config != null) {
            errCode = config.getK();
            errMsg = config.getVal();
            log.info("强制改变二卡提交响应码:  {} - {}", errCode, errMsg);
        }else {
            log.info("不强制改变,用默认提交成功的响应码: {} - {}", errCode, errMsg);
        }

        //生成内部码, BE只看这个内部状态码来确认这次请求成功,失败还是处理中.
        String status = null;
        if(errCode.equals("3001")){
            status = "Y";
        }else{
            status = "F";
        }

        //根据申请流水号查找记录
        Transaction transaction = transactionService.findTransactionBySerNo4Query(partner_apply_id);

        if(transaction == null){
            log.error("没有找到transaction记录, beSer: {}", partner_apply_id);
            return null;
        }

        //落库
        //生成卡号
        log.info("二卡申请卡号落库: beSer - {}", partner_apply_id);
        String cardNo = cardBin + RandomStringUtils.randomNumeric(10);
        //预留字段2用来标识中信联名卡,中信全量卡
        transaction.setMemo2("中信联名卡");
        //设置交易类型
        transaction.setTransType(TransTypeEnum.PAY.getType());  //二卡
        //设置银行卡号
        transaction.setAccoNo(cardNo);
        transaction.setBankNo(bankCode);
        transaction.setTransStat(errCode);
        transaction.setStat(status);
        transactionService.updateTransaction(transaction);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("errCode", errCode);
        hashMap.put("errMsg", errMsg);

        return hashMap;
    }

    /**
     * 中信联名卡新卡申请业务处理
     *
     * @param reqStr
     * @return
     */
    private String processApplyNewCard(String reqStr) {
        ApplyNewCardRequest request = (ApplyNewCardRequest) requestParser.fromXML(reqStr);
        ApplyNewCardResponse response = new ApplyNewCardResponse();
        String idNum = request.getApplyIdNbr();
        String decryIdNum = decryptString(idNum);
        String partnerApplyId = request.getPartnerApplyId();

        //从数据库查出选中的错误码配置
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CITICB1);
        String bankCode = bank.getCode();
        Config config = configService.findBankSelectConfig(bankCode, TransTypeEnum.PAY.getType()); //(bankCode + "-select-pay");
        //错误码
        String errCode = "3001";
        //错误信息
        String errMsg = "提交成功";

        if (config != null) {
            errCode = config.getK();
            errMsg = config.getVal();
            log.info("强制改变新卡提交响应码:  {} - {}", errCode, errMsg);
        } else {
            log.info("不强制改变,用默认提交成功的响应码: {} - {}", errCode, errMsg);
        }

        //生成内部码, BE只看这个内部状态码来确认这次请求成功,失败还是处理中.
        String status = null;
        if(errCode.equals("3001")){
            status = "Y";
        }else{
            status = "F";
        }

        //落库
        //生成卡号
        String cardNo = cardBin + RandomStringUtils.randomNumeric(10);
        Transaction transaction = new Transaction();
        //预留字段2用来标识中信联名卡,中信全量卡
        transaction.setBeSer(partnerApplyId);
        transaction.setMemo2("中信联名卡");
        //设置交易类型
        transaction.setTransType(TransTypeEnum.PAY.getType());
        //设置银行卡号
        transaction.setAccoNo(cardNo);
        transaction.setBankNo(bankCode);
        transaction.setIdNum(decryIdNum);
        transaction.setTransStat(errCode);
        transaction.setStat(status);
        transactionService.saveTransaction(transaction);

        log.info("==========拼接新卡申请返回报文==========");
        response.setResultCode(errCode);
        response.setResultMsg(errMsg);
        String respStr = XML_DECLARATION + responseParser.toXML(response);
        return respStr;

    }

    /**
     * 中信联名卡基础校验接口处理业务
     *
     * @param reqStr
     * @return
     */
    private String processBasicCheck(String reqStr) {
        BasicCheckRequest request = (BasicCheckRequest) requestParser.fromXML(reqStr);
        BasicCheckResponse response = new BasicCheckResponse();
        log.info("===========请求报文==========");
        String name = decryptString(request.getApplyName());
        String partnerApplyId = request.getPartnerApplyId();
        String idNum = request.getApplyIdNbr();
        String decryIdNum = decryptString(idNum);

        //从数据库查出选中的错误码配置
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CITICB1);
        String bankCode = bank.getCode();
        Config config = configService.findBankSelectConfig(bankCode, TransTypeEnum.VERIFY.getType());

        //错误码
        String errCode = "1001";
        //错误信息
        String errMsg = "新卡客户";

        if (config != null) {
            log.info("强制改变基础校验状应码:  {} - {}", errCode, errMsg);
            errCode = config.getK();
            errMsg = config.getVal();
        }
//        else {//从transaction里面查询看是否有身份证存在,默认值是从transaction里面去查.
//            log.info("不强制改变,用默认成功的基础校验状态码, 按身份证查数据库");
//
//            List<Transaction> transaction = transactionService.findTransactionByIdNum(idNum);
//            if (transaction.size() == 0) {
//                log.info("不存在同个身份证号码, {}, 新卡", decryIdNum);
//                errCode = "1001";
//                errMsg = "新卡客户";
//            } else {
//                log.info("存在同个身份证号码, {}, 二卡", decryIdNum);
//                errCode = "1002";
//                errMsg = "二卡客户";
//            }
//            log.info("中信联名卡基础校验状态码: {} - {}", errCode, errMsg);
//        }
        log.info("查询身份证: {} 在数据库是否存在", decryIdNum);
        List<Transaction> transactions = transactionService.findTransactionByIdNum(decryIdNum);
        if (transactions.size() == 0) {
            //落库
            //生成卡号
            log.info("落库基础校验信息, 姓名: {}, 申请流水号: {}", name, partnerApplyId);
//            String cardNo = cardBin + RandomStringUtils.randomNumeric(10);
            Transaction transaction = new Transaction();
            //预留字段2用来标识中信联名卡,中信全量卡
            transaction.setMemo2(name);   //暂时保存客户姓名
            transaction.setBeSer(partnerApplyId);  //申请流水号,唯一
            //设置交易类型
            transaction.setTransType(TransTypeEnum.VERIFY.getType());
            //设置银行卡号
//            transaction.setAccoNo(cardNo);
            transaction.setBankNo(bankCode);
            transaction.setIdNum(decryIdNum);
//            transaction.setTransStat(errCode);
            transactionService.saveTransaction(transaction);
        }

        log.info("中信联名卡基础校验状态码: {} - {}", errCode, errMsg);

        log.info("=========拼接基础校验返回报文=========");
        //如果是二卡申请,就返回二卡移动端或PC端的链接地址
        //模拟器ip
        Config ipConfig = configService.findConfigByK("ip");
        //模拟器port
        Config portConfig = configService.findConfigByK("port");
        response.setResultCode(errCode);
        response.setResultMsg(errMsg);
        if (errCode.equals("1002")) {
            response.setRedirectUrl("http://" + ipConfig.getVal() + ":" + portConfig.getVal()+ "/citiccard/cardishop/secondcard/index_wap.html"); // + "?partner_id=" + partnerId + "&partner_apply_id=" + partnerApplyId);
            response.setRedirectUrlPc("http://" + ipConfig.getVal() + ":" + portConfig.getVal() + "/citiccard/cardishop/secondcard/index_wap.html"); // + "?partner_id=" + partnerId + "&partner_apply_id=" + partnerApplyId);
        }
        String respStr = XML_DECLARATION + responseParser.toXML(response);
        return respStr;

    }

    private String decryptString(String encryptIdNum){
        String decryIdNum = null;
        try{
            decryIdNum = DesUtil.decryptDESwithBase64(encryptIdNum, desKey);
            log.info("解密后为: {}", decryIdNum);
        }catch (Exception e){
            log.error("解密失败: {}", e);
        }

        return decryIdNum;
    }

    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }
}
