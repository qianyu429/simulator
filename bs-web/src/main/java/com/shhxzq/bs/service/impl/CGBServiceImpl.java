package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.mapping.cgb.PayReqSuite;
import com.shhxzq.bs.mapping.cgb.QueryReqSuite;
import com.shhxzq.bs.mapping.cgb.SMSReqSuite;
import com.shhxzq.bs.mapping.cgb.SignReqSuite;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.CGBService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.TransactionService;
import com.shhxzq.bs.util.DateUtil;
import com.shhxzq.bs.util.StreamUtil;
import com.shhxzq.bs.util.XStreamUtils;
import com.thoughtworks.xstream.XStream;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by wanglili on 17/5/3.
 */
@Component
@Log4j2
public class CGBServiceImpl implements CGBService {
    private static final String XML_DECLARATION = "<?xml version=\"1.0\"?>\n";
    private static final String ENCODING = "UTF-8";

    private XStream signReqPaser;
//    private XStream signResPaser;

    private XStream payReqParser;
//    private XStream payResParser;

    private XStream queryReqParser;
//    private XStream queryResParser;

    private XStream smsReqParser;
//    private XStream smsResParser;

//    private XStream errResParser;

    @Autowired
    private BankService bankService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private TransactionService transactionService;

    public String doHandle(HttpServletRequest request) throws Exception {
        //init xstream
        signReqPaser = XStreamUtils.getXStreamInstance(ENCODING);
//        signResPaser = XStreamUtils.getXStreamInstance(ENCODING);
        payReqParser = XStreamUtils.getXStreamInstance(ENCODING);
//        payResParser = XStreamUtils.getXStreamInstance(ENCODING);
        queryReqParser = XStreamUtils.getXStreamInstance(ENCODING);
//        queryResParser = XStreamUtils.getXStreamInstance(ENCODING);
        smsReqParser = XStreamUtils.getXStreamInstance(ENCODING);
//        smsResParser = XStreamUtils.getXStreamInstance(ENCODING);
//        errResParser = XStreamUtils.getXStreamInstance(ENCODING);

        signReqPaser.processAnnotations(SignReqSuite.class);
//        signResPaser.processAnnotations(SignResSuite.class);
        payReqParser.processAnnotations(PayReqSuite.class);
//        payResParser.processAnnotations(PayResSuite.class);
        queryReqParser.processAnnotations(QueryReqSuite.class);
//        queryResParser.processAnnotations(QueryResSuite.class);
        smsReqParser.processAnnotations(SMSReqSuite.class);
//        smsResParser.processAnnotations(SMSResSuite.class);
//        errResParser.processAnnotations(ErrorResSuite.class);

        byte[] requsetBody = StreamUtil.readBytes(request.getInputStream());
        String requestString = new String(requsetBody, ENCODING);
        log.info("Receive the request: {}", requestString);

        String responseStr = convertResponse(requestString);
        log.info("response is: {}", responseStr);

        return responseStr;
    }

    private String convertResponse(String requestString) {
        String type = determineTransType(requestString);
        switch (type) {
            //短信接口
            case "CSMSReq":
                return processCSMSReq(requestString);
            //签约鉴权接口
            case "CSVReq":
                return processCSVReq(requestString);
            //支付接口
            case "CPReq":
                return processCPReq(requestString);
            //查询接口
            case "STQReq":
                return processSTQReq(requestString);

        }

        return null;
    }

    private String processCSMSReq(String requestString) {
        log.info("=========== 广发快捷短信接口开始 ===========");
        SMSReqSuite smsReqSuite = (SMSReqSuite) smsReqParser.fromXML(requestString);
        String version = smsReqSuite.getMessage().getCsmsReq().getVersion();
        String msgID = smsReqSuite.getMessage().getId();
        String cardNo = smsReqSuite.getMessage().getCsmsReq().getBankCardNo();
        String mobile = smsReqSuite.getMessage().getCsmsReq().getMobilePhone();

        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CGB);
        Config config = configService.findBankSelectConfig(bank.getCode(), "sms");

        StringBuilder sb = new StringBuilder();
        sb.append("<SoEv>");
        sb.append("<Message id=\"").append(msgID).append("\">");
        if (config != null) {
            String retCode = config.getK();
            String retMsg = config.getVal();
            sb.append("<Error id=\"Error\">\n");
            sb.append("<version>").append(version).append("</version>");
            sb.append("<instId>GDB</instId>");
            sb.append("<certId>0001</certId>");
            sb.append("<errorCode>").append(retCode).append("</errorCode>");
            sb.append("<errorMessage>").append(retMsg).append("</errorMessage>");
            sb.append("</Error>\n");
            sb.append("</Message>");
            sb.append("<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                    "        <ds:SignedInfo>\n" +
                    "            <ds:SignatureMethod Algorithm=\"SM2\"></ds:SignatureMethod>\n" +
                    "            <ds:Reference URI=\"Error\">\n" +
                    "                <ds:DigestMethod Algorithm=\"SM3\"></ds:DigestMethod>\n" +
                    "                <ds:DigestValue>B97x45SjMH5TjcHFSuVz+I0JyytekgrOfhQw8ZGM91k=</ds:DigestValue>\n" +
                    "            </ds:Reference>\n" +
                    "        </ds:SignedInfo>\n" +
                    "        <ds:SignatureValue>MEUCIQDDXharRjmTfVcgsEZEGtBMVJhR5/TLzXOuy+Jh33InvgIgBvUsr+B15TbqzolGKUsPSr8vspbVzVt15b0QSoQOVkA=</ds:SignatureValue>\n" +
                    "    </ds:Signature>\n");
        }else{
            sb.append("<CSMSRes id=\"CSMSRes\">");
            sb.append("<version>").append(version).append("</version>");
            sb.append("<instId>GDB</instId>");
            sb.append("<certId>0001</certId>");
            sb.append("<date>").append(DateUtil.format17Date(new Date())).append("</date>");
            sb.append("<bankCardNo>").append(cardNo).append("</bankCardNo>");
            sb.append("<mobilePhone>").append(mobile).append("</mobilePhone>");
            sb.append("</CSMSRes>");
            sb.append("</Message>");
            sb.append("<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                    "        <ds:SignedInfo>\n" +
                    "            <ds:SignatureMethod Algorithm=\"SM2\"></ds:SignatureMethod>\n" +
                    "            <ds:Reference URI=\"CSMSRes\">\n" +
                    "                <ds:DigestMethod Algorithm=\"SM3\"></ds:DigestMethod>\n" +
                    "                <ds:DigestValue>ZUS5nINc+2eG6nBq/396Zv04e0WMRWetwfJZHQhf/70=</ds:DigestValue>\n" +
                    "            </ds:Reference>\n" +
                    "        </ds:SignedInfo>\n" +
                    "        <ds:SignatureValue>MEYCIQD+ivm1lYM342HIxFPA2VfX98ELNCgwwXzd4ivxFhJ8kwIhANzOuZYEo9weSgxYyCf8e5uADJlbiBa1gtu5/tTIHXGO</ds:SignatureValue>\n" +
                    "    </ds:Signature>");
        }
        sb.append("</SoEv>");

        String retResp = XML_DECLARATION + sb.toString();
        log.info("=========== 广发快捷短信接口结束 ===========");
        return retResp;
    }

    private String processCPReq(String requestString) {
        log.info("=========== 广发快捷支付接口开始 ===========");
        PayReqSuite payReqSuite = (PayReqSuite) payReqParser.fromXML(requestString);
        String msgID = payReqSuite.getMessage().getId();
        String version = payReqSuite.getMessage().getCpReq().getVersion();
        String trxId = payReqSuite.getMessage().getCpReq().getTrxId();
        String pyerAcctId = payReqSuite.getMessage().getCpReq().getPyerAcctId();
        String sgnNo = payReqSuite.getMessage().getCpReq().getSgnNo();
        String trxAmt = payReqSuite.getMessage().getCpReq().getTrxAmt();
        String trxCcyCd = payReqSuite.getMessage().getCpReq().getTrxCcyCd();
        String trxDtTm = payReqSuite.getMessage().getCpReq().getTrxDtTm();

        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CGB);
        Config config = configService.findBankSelectConfig(bank.getCode(), "pay");

        String retCode = "000000";
        String retMsg = "业务处理成功";
        String sysRtnCd = "00";
        String sysRtnDesc = "银行系统处理成功";

        if (config != null) {
            log.info("强制改变申购响应码: {}", retCode);
            retCode = config.getK();
            retMsg = config.getVal();
        } else {
            log.info("不强制改变申购响应码, 正常申购...");
        }

        String stat = "F";
        if (retCode.equals("000000")) {
            stat = "Y";
        }

        log.info("生成交易落库");
        Transaction tran = new Transaction();
        tran.setBeSer(trxId);
        tran.setBankNo(bank.getBankNo());
        tran.setAccoNo(pyerAcctId);
        tran.setTransType(TransTypeEnum.PAY.getType());
        tran.setAmount(trxAmt);
        tran.setRespCode(retCode);
        tran.setStat(stat);
        tran.setWorkDay(trxDtTm.substring(0, 10).replace("-", ""));
        tran.setSendTime(DateUtil.format19Date(new Date()));// 交易时间
        transactionService.saveTransaction(tran);

        StringBuilder sb = new StringBuilder();
        sb.append("<SoEv>");
        sb.append("<Message id=\"").append(msgID).append("\">");
        if (retCode.equals("000000")) {
            sb.append("<CPRes id=\"CPRes\">");
            sb.append("<version>").append(version).append("</version>");
            sb.append("<instId>GDB</instId>");
            sb.append("<certId>0001</certId>");
            sb.append("<TrxId>").append(trxId).append("</TrxId>>");
            sb.append("<PyerAcctId>").append(pyerAcctId).append("</PyerAcctId>");
            sb.append("<BkTrxId />");
            sb.append("<SgnNo>").append(sgnNo).append("</SgnNo>");
            sb.append("<TrxAmt>").append(trxAmt).append("</TrxAmt>");
            sb.append("<overdraft>").append("N").append("</overdraft>");
            sb.append("<TrxCcyCd>").append(trxCcyCd).append("</TrxCcyCd>");
            sb.append("<BkSttlDt>").append(DateUtil.format8Date(new Date())).append("</BkSttlDt>");
            sb.append("<SysRtnCd>").append(sysRtnCd).append("</SysRtnCd>");
            sb.append("<SysRtnDesc>").append(sysRtnDesc).append("</SysRtnDesc>");
            sb.append("<BizStsCd>").append(retCode).append("</BizStsCd>");
            sb.append("<BizStsDesc>").append(retMsg).append("</BizStsDesc>");
            sb.append("</CPRes>");

        } else {
            log.info("返回错误报文");
            sb.append("<Error id=\"Error\">");
            sb.append("<version>").append(version).append("</version>");
            sb.append("<instId>GDB</instId>");
            sb.append("<certId>0001</certId>");
            sb.append("<SysRtnCd>").append(sysRtnCd).append("</SysRtnCd>");
            sb.append("<SysRtnDesc>").append(sysRtnDesc).append("</SysRtnDesc>");
            sb.append("<BizStsCd>").append(retCode).append("</BizStsCd>");
            sb.append("<BizStsDesc>").append(retMsg).append("</BizStsDesc>");
            sb.append("</Error>");
        }
        sb.append("</Message>");
        sb.append("<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "        <ds:SignedInfo>\n" +
                "            <ds:SignatureMethod Algorithm=\"SM2\"></ds:SignatureMethod>\n" +
                "            <ds:Reference URI=\"CPRes\">\n" +
                "                <ds:DigestMethod Algorithm=\"SM3\"></ds:DigestMethod>\n" +
                "                <ds:DigestValue>ChilB5RVNwkYGQGl7vapwqN+F59CAw8PIQRr9oJYDoU=</ds:DigestValue>\n" +
                "            </ds:Reference>\n" +
                "        </ds:SignedInfo>\n" +
                "        <ds:SignatureValue>MEYCIQCYbq+qUyeJ6EF/0xTF1iWo+U9mXAuWHHFxQDdE5TXUsQIhAOSmRtSTzEcdPsv3xAn3sCx3n7MQIqXgdOBOtNjfLFsr</ds:SignatureValue>\n" +
                "    </ds:Signature>");
        sb.append("</SoEv>");

        String retResp = XML_DECLARATION + sb.toString();
        log.info("=========== 广发快捷支付接口结束 ===========");
        return retResp;
    }

    private String processCSVReq(String requestString) {
        log.info("=========== 广发快捷鉴权接口开始 ===========");
        SignReqSuite reqSuite = (SignReqSuite) signReqPaser.fromXML(requestString);
        String version = reqSuite.getMessage().getCsvReq().getVersion();
        String trxId = reqSuite.getMessage().getCsvReq().getTrxId();
        String msgID = reqSuite.getMessage().getId();
        String sgnNo = RandomStringUtils.randomAlphanumeric(34).toUpperCase();
        String bkAcctNo = reqSuite.getMessage().getCsvReq().getBkAcctNo();

        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CGB);
        Config config = configService.findBankSelectConfig(bank.getCode(), "verify");

        String retCode = "000000";
        String retMsg = "业务处理成功";
        String sysRtnCd = "00";
        String sysRtnDesc = "银行系统处理成功";

        if (config != null) {
            log.info("强制改变鉴权状态码, {}", retCode);
            retCode = config.getK();
            retMsg = config.getVal();
        } else {
            log.info("不强制改变鉴权状态码, {}", retCode);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<SoEv>");
        sb.append("<Message id=\"").append(msgID).append("\">");
        if (retCode.equals("000000")) {
            sb.append("<CSVRes id=\"CSVRes\">");
            sb.append("<version>").append(version).append("</version>");
            sb.append("<instId>GDB</instId>");
            sb.append("<certId>0001</certId>");
            sb.append("<TrxId>").append(trxId).append("</TrxId>");
            sb.append("<TrxDtTm>").append(DateUtil.format19Date(new Date())).append("</TrxDtTm>");
            sb.append("<SgnNo>").append(sgnNo).append("</SgnNo>");
            sb.append("<BkAcctTp>0</BkAcctTp>");
            sb.append("<BkAcctNo>").append(bkAcctNo).append("</BkAcctNo>");
            sb.append("<SysRtnCd>").append(sysRtnCd).append("</SysRtnCd>");
            sb.append("<SysRtnDesc>").append(sysRtnDesc).append("</SysRtnDesc>");
            sb.append("<BizStsCd>").append(retCode).append("</BizStsCd>");
            sb.append("<BizStsDesc>").append(retMsg).append("</BizStsDesc>");
            sb.append("</CSVRes>");
        } else {
            sb.append("<Error id=\"Error\">");
            sb.append("<version>").append(version).append("</version>");
            sb.append("<instId>GDB</instId>");
            sb.append("<certId>0001</certId>");
            sb.append("<SysRtnCd>").append(sysRtnCd).append("</SysRtnCd>");
            sb.append("<SysRtnDesc>").append(sysRtnDesc).append("</SysRtnDesc>");
            sb.append("<BizStsCd>").append(retCode).append("</BizStsCd>");
            sb.append("<BizStsDesc>").append(retMsg).append("</BizStsDesc>");
            sb.append("</Error>");

        }
        sb.append("</Message>");
        sb.append("<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "        <ds:SignedInfo>\n" +
                "            <ds:SignatureMethod Algorithm=\"SM2\"></ds:SignatureMethod>\n" +
                "            <ds:Reference URI=\"CSVRes\">\n" +
                "                <ds:DigestMethod Algorithm=\"SM3\"></ds:DigestMethod>\n" +
                "                <ds:DigestValue>j8swbr4WsMD/16TorYrW8J5MidyN3PRlsTFPz+Ph5+o=</ds:DigestValue>\n" +
                "            </ds:Reference>\n" +
                "        </ds:SignedInfo>    <ds:SignatureValue>MEUCIGlmlzd2Gpb0syYoNxQM6YB3cyAnfR22q4kRGyzoVVwKAiEA2eHDe9xKwehFcUmLKXyUl6plBPz7G1sVnKOrNqKpVZw=</ds:SignatureValue>\n" +
                "    </ds:Signature>");
        sb.append("</SoEv>");

        String retResp = XML_DECLARATION + sb.toString();
        log.info("=========== 广发快捷鉴权接口结束 ===========");

        return retResp;
    }

    private String processSTQReq(String requestString) {
        log.info("=========== 广发快捷查询接口开始 ===========");
        QueryReqSuite queryReqSuite = (QueryReqSuite) queryReqParser.fromXML(requestString);
        String msgID = queryReqSuite.getMessage().getId();
        String version = queryReqSuite.getMessage().getStqReq().getVersion();
        String trxId = queryReqSuite.getMessage().getStqReq().getTrxId();
        String trxDtTm = queryReqSuite.getMessage().getStqReq().getTrxDtTm();
        String oriTrxId = queryReqSuite.getMessage().getStqReq().getOriTrxId();
        String type = queryReqSuite.getMessage().getStqReq().getType();

        String retCode = "0001";
        Transaction transaction = transactionService.findTransactionBySerNo4dashboard(oriTrxId);
        String amount = "0";
        if (transaction == null) {
            retCode = "0003";
        } else {
            if (!transaction.getRespCode().equals("000000"))
                retCode = "0000";

            amount = transaction.getAmount();
        }

        String sysRtnCd = "00";
        String sysRtnDesc = "银行系统处理成功";

        StringBuilder sb = new StringBuilder();
        sb.append("<SoEv>");
        sb.append("<Message id=\"").append(msgID).append("\">");
        sb.append("<STQRes id=\"STQRes\">");
        sb.append("<version>").append(version).append("</version>");
        sb.append("<instId>GDB</instId>");
        sb.append("<certId>0001</certId>");
        sb.append("<type>").append(type).append("</type>");
        sb.append("<TrxId>").append(trxId).append("</TrxId>");
        sb.append("<OriTrxId>").append(oriTrxId).append("</OriTrxId>");
        sb.append("<date>").append(trxDtTm).append("</date>");
        sb.append("<OriBkTrxId />");
        sb.append("<signNo />");
        sb.append("<originalSerialNo />");
        sb.append("<originalDate />");
        sb.append("<TrxAmt>").append(amount).append("</TrxAmt>");
        sb.append("<charge />");
        sb.append("<TrxCcyCd />");
        sb.append("<OriBizSts>").append(retCode).append("</OriBizSts>");
        sb.append("<SysRtnCd>").append(sysRtnCd).append("</SysRtnCd>");
        sb.append("<SysRtnDesc>").append(sysRtnDesc).append("</SysRtnDesc>");
        sb.append("<BizStsCd />");
        sb.append("<BizStsDesc />");
        sb.append("</STQRes>");
        sb.append("</Message>");
        sb.append("<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "        <ds:SignedInfo>\n" +
                "            <ds:SignatureMethod Algorithm=\"SM2\"></ds:SignatureMethod>\n" +
                "            <ds:Reference URI=\"STQRes\">\n" +
                "                <ds:DigestMethod Algorithm=\"SM3\"></ds:DigestMethod>\n" +
                "                <ds:DigestValue>cXBbfLpihP0ZHwnyRRO4cGAdJo7yGG2iXPOPdACL77M=</ds:DigestValue>\n" +
                "            </ds:Reference>\n" +
                "        </ds:SignedInfo>\n" +
                "   <ds:SignatureValue>MEUCIQCv7NBi3qZSQwtSIBt25zThcfTDr5Db57YrOkLj8Ab49AIgAw89kY1C+RnbG69gyaDhFUsS6MuEzAH32i5+6jv6a/4=</ds:SignatureValue>\n" +
                "    </ds:Signature>");
        sb.append("</SoEv>");

        String retResp = XML_DECLARATION + sb.toString();
        log.info("=========== 广发快捷查询接口结束 ===========");
        return retResp;
    }


    private String determineTransType(String request) {

        if (StringUtils.contains(request, "<CSMSReq")) {
            return "CSMSReq";
        } else if (StringUtils.contains(request, "<CSVReq")) {
            return "CSVReq";
        } else if (StringUtils.contains(request, "<CPReq")) {
            return "CPReq";
        } else if (StringUtils.contains(request, "<STQReq")) {
            return "STQReq";
        }

        return null;
    }
}
