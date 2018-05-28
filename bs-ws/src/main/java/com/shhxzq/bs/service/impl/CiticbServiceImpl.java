package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.CiticbService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.TransService;
import com.shhxzq.bs.util.EctUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by wanglili on 17/1/9.
 */
@Service("citicbService")
@Log4j2
public class CiticbServiceImpl implements CiticbService {

    @Autowired
    private TransService transactionService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private BankService bankService;

    /**
     * 全量卡实时签约/修改/解约接口
     *
     * @param reqXml
     * @return
     */
    public String signAllHTFTxn(String reqXml) {
        //解析request xml
        log.info("request xml is: " + reqXml.toString());
        if (StringUtils.isEmpty(reqXml)) {
            log.info("request body is null.");
            return null;
        }
        Map<String, String> params = EctUtils.parseXml(reqXml);

        //获取银行的bankNo
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CITICB);

        String merchantID = params.get("merchantID"); //商户编号
        String oprType = params.get("oprType");       //交易类型
        String acctNbr = params.get("acctNbr");       //帐号
        String cardNbr = params.get("cardNbr");       //卡号
        String transDate = params.get("transDate");
        String transTime = params.get("transTime");
        String chName = params.get("chName");
        String chIdType = params.get("chIdType");
        String chIdNum = params.get("chIdNum");
        String amtInd = params.get("amtInd");
        String signNo = params.get("signNo");    //协议号
        String cardL4 = params.get("cardL4");
        String cardB8 = params.get("cardB8");
        String payAcctNo = params.get("payAcctNo");
        String payCardNo = params.get("payCardNo");
        String payBankName = params.get("payBankName");

        Transaction tran = transactionService.findTransactionBySerNo(transDate + transTime);

        //获取错误码与错误码描述
        Config config = configService.findBankSelectConfig(bank.getCode(), "sign");
        log.info("print config info: " + config.toString());

        String code = "0000000";
        String tranStatus = "Y";
        String stat = "Y";
        String val = "成功";

        if (config != null) {
            code = config.getK();
            val = config.getVal();

            tranStatus = code;
            if (code.equals("0000000")) {
                stat = "Y";
            } else {
                stat = "F";
            }

        }

        if (StringUtils.isEmpty(signNo) && StringUtils.equals(oprType, "0002")) {
            //签约
            if (tran == null) {
                //生成协议号
                signNo = RandomStringUtils.randomNumeric(10);
                Transaction transaction = new Transaction();
                transaction.setBeSer(transDate + transTime);// 基金公司流水号
                transaction.setMerId(merchantID);// 商户号
                transaction.setBankNo(bank.getBankNo());// 银行编号
                transaction.setAccoNo(acctNbr);// 账户(卡号)
                transaction.setTransType(TransTypeEnum.AUTOASSIGN.getType());// 交易类型
                transaction.setProtocolNo(signNo);// 交易金额
                transaction.setRespCode(code);// 响应码: e.g. "00"
                transaction.setTransStat(tranStatus);// 状态码: e.g. "Y"
                transaction.setStat(stat);// 内部码: e.g. "Y"
                transaction.setMemo1(amtInd);// 扣款金额指示 全额，最低。
                transaction.setStatus("able");// 显示是否解约成功。

                //签约落库
                transactionService.saveTransaction(transaction);
            }
        } else if (StringUtils.isNotEmpty(signNo) && StringUtils.equals(oprType, "0005")) {

            Transaction tr = transactionService.findTransactionByProtocolNo(bank.getBankNo(), signNo);

            if (tr == null) {
                code = "9999004";
                val = "协议号不存在";
                log.info("协议号: " + signNo + " 不存在。");

            } else {
                log.info("将原来签约那条设置成disable");
                tr.setStatus("disable");
                transactionService.updateTransaction(tr);

                log.info("解约数据落库");
                Transaction unAssignTran = new Transaction();
                unAssignTran.setBeSer(transDate + transTime);// 基金公司流水号
                unAssignTran.setMerId(merchantID);// 商户号
                unAssignTran.setBankNo(bank.getBankNo());// 银行编号
                unAssignTran.setAccoNo(acctNbr);// 账户(卡号)
                unAssignTran.setTransType(TransTypeEnum.AUTOUNASSIGN.getType());// 交易类型
                unAssignTran.setProtocolNo(signNo);// 交易金额
                unAssignTran.setRespCode(code);// 响应码: e.g. "00"
                unAssignTran.setTransStat(tranStatus);// 状态码: e.g. "Y"
                unAssignTran.setMemo1(amtInd);// 扣款金额指示 全额，最低。
                unAssignTran.setStatus("able");// 显示是否解约成功。

                transactionService.saveTransaction(unAssignTran);
            }
        }

        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String signDate = df.format(now);

        String resXml = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n" +
                "<message method=\" signAllHTFTxn \" type=\"response\">\n" +
                "<infoType>0810</infoType>\n" +
                "<merchantID>" + merchantID + "</merchantID>\n" +
                "<transDate>" + transDate + "</transDate>\n" +
                "<transTime>" + transTime + "</transTime>\n" +
                "<oprType>" + oprType + "</oprType>\n" +
                "<signNo>" + signNo + "</signNo>\n" +
                "<acctNbr>" + acctNbr + "</acctNbr>\n" +
                "<cardNbr>" + cardNbr + "</cardNbr>\n" +
                "<chName>" + chName + "</chName>\n" +
                "<currCode>156</currCode>\n" +
                "<chIdType>" + chIdType + "</chIdType>\n" +
                "<chIdNum>" + chIdNum + "</chIdNum>\n" +
                "<cardL4>" + cardL4 + "</cardL4>\n" +
                "<cardB8>" + cardB8 + "</cardB8>\n" +
                "<payAcctNo>" + payAcctNo + "</payAcctNo>\n" +
                "<payCardNo>" + payCardNo + "</payCardNo>\n" +
                "<payBankName>" + payBankName + "</payBankName>\n" +
                "<payFlag>A</payFlag>\n" +
                "<signDate>" + signDate + "</signDate>\n" +
                "<amtInd>" + amtInd + "</amtInd>\n" +
                "<retCode>" + code + "</retCode>\n" +
                "<commentRes>" + val + "</commentRes>\n" +
                "</message>";
        log.info("print response xml: " + resXml);
        return resXml;
    }

    public String queryStatement(String reqXml) {
        //解析request xml
        log.info("request xml is: " + reqXml.toString());
        if (StringUtils.isEmpty(reqXml)) {
            log.info("request body is null.");
            return null;
        }
        Map<String, String> params = EctUtils.parseXml(reqXml);

        //获取银行的bankNo
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CITICB);

        String merchantID = params.get("merchantID"); //商户编号
        String oprType = params.get("oprType");       //交易类型
        String acctNbr = params.get("acctNbr");       //帐号
        String cardNbr = params.get("cardNbr");       //卡号
        String transDate = params.get("transDate");
        String transTime = params.get("transTime");

        //获取错误码与错误码描述
        Config config = configService.findBankSelectConfig(bank.getCode(), "verify");
        log.info("print config info: " + config.toString());

        String code = "0000000";
        String val = "查询成功-有账单";

        if (config != null) {
            code = config.getK();
            val = config.getVal();
        }

        Config config1 = configService.findConfigByK(bank.getCode() + "-verify-date");
        Config config2 = configService.findConfigByK(bank.getCode() + "-verify-payAmt");
        Config config3 = configService.findConfigByK(bank.getCode() + "-verify-minAmt");

        String stmtDate = config1.getVal();
        String minPay1 = config3.getVal();
        String curAmt1 = config2.getVal();
        String serialNo = RandomStringUtils.randomNumeric(12);
        String resXml = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n" +
                "<message method=\" queryStatement \" type=\"response\">\n" +
                "<infoType>0810</infoType>\n" +
                "<serialNo>" + serialNo + "</serialNo>\n" +
                "<transTime>" + transTime + "</transTime>\n" +
                "<transDate>" + transDate + "</transDate>\n" +
                "<merchantID>" + merchantID + "</merchantID>\n" +
                "<acctNbr>" + acctNbr + "</acctNbr>\n" +
                "<cardNbr>" + cardNbr + "</cardNbr>\n" +
                "<dataset count =”1” count =”1”  startwith= ”21”>\n" +
                "<record>\n" +
                "<stmtDate>" + stmtDate + "</stmtDate>\n" +
                "<minPay1>" + minPay1 + "</minPay1>\n" +
                "<curAmt1>" + curAmt1 + "</curAmt1>\n" +
                "</record>\n" +
                "</dataset>\n" +
                "<retCode>" + code + "</retCode>\n" +
                "<commentRes>" + val + "</commentRes>\n" +
                "</message>";

        log.info("print query statement repsonse xml: " + resXml);
        return resXml;
    }
}
