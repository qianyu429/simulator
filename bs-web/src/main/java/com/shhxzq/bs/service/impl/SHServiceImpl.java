package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.mapping.sh.*;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.SHService;
import com.shhxzq.bs.service.TransactionService;
import com.shhxzq.bs.util.DateUtil;
import com.shhxzq.bs.util.SHUtil;
import com.shhxzq.bs.util.SHYQUtil;
import com.shhxzq.bs.util.StreamUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by houjiagang on 2016/11/16.
 */
@Component
@Log4j2
public class SHServiceImpl implements SHService {

    private static final String ENCODING = "UTF-8";

    private XStream parser;

    private XStream respParser;

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    @Autowired
    private BankService bankService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private TransactionService transactionService;

    private static final Pattern PATTERN_REPLACEMENT;

    static {
        PATTERN_REPLACEMENT = Pattern.compile("(?<=<OSNO>)(.*?)(?=</OSNO>)");
    }


    @Override
    public String handle(HttpServletRequest request) throws Exception {
        //init xstream
        parser = getXStreamInstance();
        parser.processAnnotations(Banksh.class);
        byte[] requestBody = StreamUtil.readBytes(request.getInputStream());
        String reqStr = new String(requestBody, ENCODING);
        log.info("Receive the request: {}", reqStr);
        Banksh req = (Banksh) parser.fromXML(reqStr);
        String respStr = converResponse(req, req.getMessage().getRequest().getId());
        log.info("Response is {}", reqStr);
        return respStr;
    }

    @Override
    public String handleYQ(HttpServletRequest request) throws Exception {
        parser = getXStreamInstance();
        parser.processAnnotations(SHYQRequest.class);

        respParser = getXStreamInstance();
        respParser.processAnnotations(SHYQResponse.class);

        byte[] requestBody = StreamUtil.readBytes(request.getInputStream());
        String reqStr = new String(requestBody, ENCODING);
        String[] temp = reqStr.split("reqData=");
        log.info("Receive the request: {}", temp[1]);
        String respStr = converRep(temp[1]);
        log.info("Response is {}", respStr);
        return respStr;
    }


    private String converResponse(Banksh request, String transCode){
        switch (transCode) {
            case "IAReq":
                return processIAReq(request);
            case "CPReq":
                return processCPReq(request);
            case "SQReq":
                return processSQReq(request);
            case "CSCReq":
                return processCSCReq(request);
        }
        return null;
    }

    private String converRep(String reqStr){
        Matcher matcher = PATTERN_REPLACEMENT.matcher(reqStr);
        if(matcher.find()){
            return processQuery(reqStr);
        } else {
            return processTransfer(reqStr);
        }

    }

    private String processIAReq(Banksh request) {
        log.info("============ 上海银行快捷鉴权开始 ============");
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_SH);
        Config config = configService.findBankSelectConfig(bank.getCode(), "verify");
        String retCode = "SUCC";
        String retMsg = "";
        if (config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
            log.info("强制改变鉴权响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常鉴权...");
        }

        //瓶装response
        Response response = new Response();
        response.setId("IARes");
        response.setVersion(request.getMessage().getRequest().getVersion());
        response.setInstId(request.getMessage().getRequest().getInstId());
        response.setCardNo(request.getMessage().getRequest().getCardNo());
        response.setCardCode(RandomStringUtils.randomNumeric(20));
        response.setDate(DateUtil.format8Date(new Date()) + " 00:00:00");
        response.setErrorCode(retCode);
        response.setErrorMessage(retMsg);
        response.setExtension("");

        Message message = new Message();
        message.setId(request.getMessage().getId());
        message.setSignature("KwMIceznUYv72phlzCgYmTZe5gF+qOdpNavxes4jw/WQOTIBIeHCk+Y5mCUOeybsx27M3BX2XO/9" +
                "Vqw2kNSMv1+QA58gcKyjW3Sh9jiW34QltpdQ34VZOCO5aCHvuti4sHyiJOfUkWUT87+IwhQVrUrF" +
                "kURG4lEKEHAU6PxRcfg=");
        message.setResponse(response);

        Banksh res = new Banksh();
        res.setMessage(message);

        String respStr = XML_DECLARATION + parser.toXML(res);
        return respStr;
    }


    private String processCPReq(Banksh request){
        log.info("============ 上海银行快捷充值开始 ============");
        String serNo = configService.getSerNo();
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_SH);
        Config config = configService.findBankSelectConfig(bank.getCode(), "pay");
        String retCode = "SUCC";
        String retMsg = "";
        if (config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
            log.info("强制改变充值响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常充值...");
        }

        Transaction tran = transactionService.findTransactionBySerNo(request.getMessage().getId());
        if(tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "2001";
        } else {
            log.info("交易不重复,生成交易并入库...");
            Transaction transaction = SHUtil.buildTransaction(request, bank, TransTypeEnum.PAY.getType(), serNo, retCode);
            transactionService.saveTransaction(transaction);
        }

        Response response = new Response();
        response.setId("CPRes");
        response.setVersion(request.getMessage().getRequest().getVersion());
        response.setInstId(request.getMessage().getRequest().getInstId());
        response.setOrderNum(request.getMessage().getRequest().getOrderNum());
        response.setCardCode(request.getMessage().getRequest().getCardCode());
        response.setSerialNo(request.getMessage().getRequest().getSerialNo());
        response.setDate(DateUtil.format8Date(new Date()) + "00:00:00");
        response.setErrorCode(retCode);
        response.setErrorMessage("");
        response.setExtension("");

        Message message = new Message();
        message.setId(request.getMessage().getId());
        message.setResponse(response);
        message.setSignature("IanVz+/e5KGHJtoo0t88/YRwS+al1oafHQtdYUKF7Cy9J81HPSa7iet4r7HLh75K9MLjWkcRxqVENOTOEmR/HWIZ/2+Bqje+dmo1LL72gPL5FF8XMUtzF0VGG8dyg6QX5nnscSBhlDjZaPFAbDtqHv/za87m9NzLVUD3RzlVge0=");

        Banksh res = new Banksh();
        res.setMessage(message);

        String respStr = XML_DECLARATION + parser.toXML(res);
        return respStr;
    }

    private String processSQReq(Banksh request){
        log.info("============ 上海银行充值查询开始 ============");
        Transaction transaction = transactionService.findTransactionBySerNo4Query(request.getMessage().getId());
        String retCode = "0000";
        String retMsg = "";
        if(transaction == null){
            retCode = "0003";
            retMsg = "银行查无此订单";
        } else {
            if(!"SUCC".equals(transaction.getRespCode())){
                retCode = "0001";
            } else {
                retCode = "0000";
            }

        }
        Response response = new Response();
        response.setId("SQRes");
        response.setVersion("1.0.1");
        response.setInstId(request.getMessage().getRequest().getInstId());
        response.setSerialNo(request.getMessage().getRequest().getSerialNo());
        response.setDate(request.getMessage().getRequest().getDate());
        response.setType("1");
        response.setOrderNum(request.getMessage().getRequest().getOrderNum());
        response.setStatus(retCode);
        response.setOrderRemak("");
        response.setCardNo(transaction.getAccoNo());
        response.setAmount(transaction.getAmount());
        response.setCharge("");
        response.setCardType("1");
        response.setErrorCode("SUCC");
        response.setErrorMessage("");
        response.setExtension("");

        Message  message = new Message();
        message.setId(request.getMessage().getId());
        message.setResponse(response);
        message.setSignature("eQ+6azB5xOhDKNCgspgTs24Sv0okBmR1UweUhv4Wq2fTZg1WG2q2oe9BCfYLm74KmOySOA8qtU+6171PuyfBD3SKI2N7o8rbVyMLuHXuFGVS5Qrq4l+E6SUA1Rc2EA03HVR4MABLYtwz97SiZLxMTlKwu2pg+mwEHtVUq4PRlUU=");

        Banksh res = new Banksh();
        res.setMessage(message);

        String respStr = XML_DECLARATION + parser.toXML(res);
        return respStr;
    }


    private String processCSCReq(Banksh request) {
        log.info("============ 上海银行快捷解约开始 ============");
        Response response = new Response();
        response.setVersion(request.getMessage().getRequest().getVersion());
        response.setInstId(request.getMessage().getRequest().getInstId());
        response.setDate(request.getMessage().getRequest().getDate());
        response.setCardCode(request.getMessage().getRequest().getCardCode());
        response.setErrorCode("SUCC");
        response.setErrorMessage("");
        response.setExtension("");

        Message message = new Message();
        message.setId(request.getMessage().getId());
        message.setResponse(response);

        Banksh res = new Banksh();
        res.setMessage(message);

        String reqStr = XML_DECLARATION + parser.toXML(res);

        return reqStr;
    }


    private String processTransfer(String reqStr){
        log.info("============ 上海银企赎回开始 ============");
        String serNo = configService.getSerNo();
        SHYQRequest shyqReq = (SHYQRequest)parser.fromXML(reqStr);
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_SH);
        Config config = configService.findBankSelectConfig(bank.getCode(), "redeem");
        String retCode = "0";
        String retMsg = "成功";
        if(config != null){
            retCode = config.getK();
            retMsg = config.getVal();
            log.info("强制改变赎回响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常赎回...");
        }

        Transaction tran = transactionService.findTransactionBySerNo(shyqReq.getOpReq().getSerialNo());
        if(tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "1";
            retMsg = "重复流水号或交易失败";
        } else {
            log.info("交易不重复,生成交易并入库...");
            Transaction transaction = SHYQUtil.buildTransaction(shyqReq, bank, TransTypeEnum.REDEEM.getType(), serNo, retCode);
            transactionService.saveTransaction(transaction);
        }

        SHYQResponse.OpResult opResult = new SHYQResponse.OpResult();
        opResult.setT24S("FT"+RandomStringUtils.randomNumeric(14));
        opResult.setT24D(DateUtil.format8Date(new Date()));

        SHYQResponse.OpRep opRep = new SHYQResponse.OpRep();
        opRep.setSerialNo(shyqReq.getOpReq().getSerialNo());
        opRep.setRetCode(retCode);
        opRep.setErrMsg(retMsg);
        opRep.setOpResult(opResult);

        SHYQResponse response = new SHYQResponse();
        response.setOpRep(opRep);

        String respStr = XML_DECLARATION + respParser.toXML(response);
        return respStr;
    }

    private String processQuery(String reqStr) {
        log.info("============ 上海银企查询开始 ============");
        SHYQRequest shyqReq = (SHYQRequest) parser.fromXML(reqStr);
        Transaction transaction = transactionService.findTransactionBySerNo4Query(shyqReq.getOpReq().getReqParam().getOSNO());
        String retCode;
        String retMsg = "成功";
        if (transaction != null) {
            retCode = transaction.getRespCode();
        } else {
            retCode = "be0021122";
            retMsg = "银行无此订单";
        }

        SHYQResponse.OpResult opResult = new SHYQResponse.OpResult();
        opResult.setT24S("FT"+RandomStringUtils.randomNumeric(14));
        opResult.setT24D(DateUtil.format8Date(new Date()));
        opResult.setT24C(retCode);
        opResult.setT24M(retMsg);
        opResult.setCNAS("");
        opResult.setCNAC("");
        opResult.setCNAR("");
        opResult.setRECO(retCode);
        opResult.setREMG(retMsg);

        SHYQResponse.OpRep opRep = new SHYQResponse.OpRep();
        opRep.setSerialNo(shyqReq.getOpReq().getSerialNo());
        opRep.setRetCode("0");
        opRep.setErrMsg("成功");
        opRep.setOpResult(opResult);

        SHYQResponse response = new SHYQResponse();
        response.setOpRep(opRep);

        String respStr = XML_DECLARATION + respParser.toXML(response);
        return respStr;
    }


    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }



}
