package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.mapping.spdb.*;
import com.shhxzq.bs.mapping.spdb.query.QueryRequest;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.SpdbService;
import com.shhxzq.bs.service.TransactionService;
import com.shhxzq.bs.util.SpdbUtil;
import com.shhxzq.bs.util.StreamUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by houjiagang on 16/7/15.
 */
@Component
@Log4j2
public class SpdbServiceImpl implements SpdbService {

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n";
    private static final String XML_DECLARATION_GB2312 = "<?xml version=\"1.0\" encoding=\"GB2312\"?>\n";
    private static final Pattern PATTERN_REPLACEMENT;
    private static final Pattern PATTERN_REPLACEMENT1;
    private static final String ENCODING = "GBK";

    private XStream requestParser;
    private XStream responseParser;
    private XStream errResponseParser;

    private XStream cRequestParser;
    private XStream cResponseParser;

    private XStream queryRequestParser;


    @Autowired
    private BankService bankService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private TransactionService transactionService;

    static {
        PATTERN_REPLACEMENT = Pattern.compile("(?<=<transName>)(.*?)(?=</transName>)");
        PATTERN_REPLACEMENT1 = Pattern.compile("(?<=<transCode>)(.*?)(?=</transCode>)");
    }

    @Override
    public String handle(HttpServletRequest request) throws Exception {
        //init xstream
        requestParser = getXStreamInstance();
        responseParser = getXStreamInstance();
        errResponseParser =getXStreamInstance();

        requestParser.processAnnotations(Request.class);
        responseParser.processAnnotations(Response.class);
        errResponseParser.processAnnotations(ErrorResponse.class);

        //receive request
        byte[] requsetBody = StreamUtil.readBytes(request.getInputStream());
        String requestString = new String(requsetBody, ENCODING);
        log.info("Receive the request: {}", requestString);

        //convert response by transName
        String responseStr = convertResponse(requestString);
        log.info("response is: {}", responseStr);

        return responseStr;
    }

    private String convertResponse(String requestString) {
        Matcher matcher = PATTERN_REPLACEMENT.matcher(requestString);
        if (!matcher.find()) {
            throw new UnsupportedOperationException("The transName cannot be found in request xml!");
        }
        String transName = matcher.group(0);
        switch (transName) {
            case "KJQY":
                return processKJQY(requestString);
            case "KPER":
                return processKPER(requestString);
            case "IQSR":
                return processIQSR(requestString);
            case "BQSR":
                return processBQSR(requestString);
            case "IDFR":
                return processIDFR(requestString);
            case "DPER":
                return processDPER(requestString);
        }

        return null;
    }

    @Override
    public String handleCompany(HttpServletRequest request) throws Exception {
        cRequestParser = getXStreamInstance();
        cResponseParser = getXStreamInstance();

        queryRequestParser = getXStreamInstance();

        cRequestParser.processAnnotations(CompanyRequest.class);
        cResponseParser.processAnnotations(CompanyResponse.class);

        queryRequestParser.processAnnotations(QueryRequest.class);

        //receive request
        byte[] requestBody = StreamUtil.readBytes(request.getInputStream());
        String requestString = new String(requestBody, ENCODING);
        log.info("Receive the request: {}", requestString);

        String responseStr = convertResponse1(requestString);
        log.info("response is: {}", responseStr.replace("&lt;", "<").replace("&gt;", ">").replace("&quot;", "\""));

        return responseStr.replace("&lt;", "<").replace("&gt;", ">").replace("&quot;", "\"");
    }

    private String convertResponse1(String requestString) {
        Matcher matcher = PATTERN_REPLACEMENT1.matcher(requestString);
        if (!matcher.find()) {
            throw new UnsupportedOperationException("The transName cannot be found in request xml!");
        }
        String transCode = matcher.group(0);
        switch (transCode) {
            case "8801":
                return process8801(requestString);
            case "EG01":
                return processEG01(requestString);
            case "8804":
                return process8804(requestString);
            case "EG30":
                return processEG30(requestString);
            case "9004":
                return process9004(requestString);

        }

        return null;
    }


    private String processDPER(String requestString) {
        log.info("=========== 浦发网关充值(DPER)接口开始 ===========");
        String serNo = configService.getSerNo();
        Request request = (Request) requestParser.fromXML(requestString);
        HashMap<String, String> plainMap = SpdbUtil.plainToMap(request.getPlain());

        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_SPDB2);
        Config config = configService.findBankSelectConfig(bank.getCode(), "pay");

        String retCode = "00";
        String retMsg = "交易成功";
        if (config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
            log.info("强制改变申购响应码为: {}", retCode);
        } else {
            log.info("不强制改变申购响应码, 正常申购...");
        }

        Transaction tran = transactionService.findTransactionBySerNo(plainMap.get("TermSsn"));
        if (tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "94";
            retMsg = "重复交易";
        } else {
            log.info("交易不重复,生成交易并入库...");
            Transaction transaction = SpdbUtil.wRequest2Transaction(plainMap, bank, serNo, TransTypeEnum.PAY.getType(), retCode);

            transactionService.saveTransaction(transaction);
        }

        String retResp = "";
        if (StringUtils.equalsIgnoreCase(retCode, "00")) {
            //交易成功的返回报文
            StringBuilder respPlain = new StringBuilder();
            respPlain.append("TranAbbr=").append(request.getTransName()).append("|");
            respPlain.append("AcqSsn=").append(RandomStringUtils.randomNumeric(12)).append("|");
            respPlain.append("MercDtTm=").append(plainMap.get("MercDtTm")).append("|");
            respPlain.append("TermSsn=").append(plainMap.get("TermSsn")).append("|");
            respPlain.append("RespCode=").append(retCode).append("|");
            respPlain.append("TermCode=0|");
            respPlain.append("MercCode=").append(plainMap.get("MercCode")).append("|");
            respPlain.append("TranAmt=").append(plainMap.get("TranAmt")).append("|");
            respPlain.append("SettDate=").append(plainMap.get("MercDtTm").substring(0, 8));

            Response response = new Response();
            response.setPlain(respPlain.toString());
            response.setTransName(request.getTransName());
            response.setSignature(request.getSignature());

            retResp = XML_DECLARATION + responseParser.toXML(response);
        } else {
            //交易失败的返回报文
            ErrorResponse response = new ErrorResponse();
            response.setErrorCode(retCode);
            response.setErrorMsg(retMsg);
            retResp = XML_DECLARATION + errResponseParser.toXML(response);
        }
        log.info("=========== 浦发网关充值(DPER)接口结束 ===========");
        return retResp;
    }

    private String process9004(String requestString) {
        log.info("============ 浦发银企日间账户明细下载(9004) 开始 ============");
        CompanyRequest request = (CompanyRequest) cRequestParser.fromXML(requestString);
        String accNo = request.getBody().getSignature().getBody().getAcctNo();
        String workDay = request.getBody().getSignature().getBody().getBeginDate();

        Example example = new Example(Transaction.class);
        Example.Criteria criteria = example.createCriteria();
        List types = new ArrayList();
        types.add("redeem");
        types.add("interRedeem");
        criteria.andIn("transType", types);
        criteria.andEqualTo("bankNo", "887");
        criteria.andEqualTo("accoNo", accNo);
        criteria.andEqualTo("workDay", workDay);

        List<Transaction> transactions = transactionService.findTransatctionByExample(example);
        StringBuilder head = new StringBuilder();
        head.append("<packet>");
        head.append("<head>");
        head.append("<transCode>9004</transCode>");
        head.append("<signFlag>0</signFlag>");
        head.append("<packetID>").append(request.getHead().getPacketID()).append("</packetID>");
        head.append("<timeStamp>").append(request.getHead().getTimeStamp()).append("</timeStamp>");
        head.append("<returnCode>AAAAAAA</returnCode>");
        head.append("</head>");
        head.append("<body>" +
                "<whetherFinishMark>1</whetherFinishMark>" +
                "<fileName></fileName>" +
                "<lists name=\"LoopResult\">");

        StringBuilder temp = new StringBuilder();
        int i = 1;
        for (Transaction tran : transactions) {
            temp.append("<list>");
            temp.append("<fileContent>");

            temp.append(i).append("|");
            temp.append(tran.getWorkDay()).append("|");
            temp.append(tran.getSendTime().substring(0, 4)).append("|");
            temp.append(tran.getBsSer()).append("|");
            temp.append("1|");
            String code = "8801";
            if ("interRedeem".equals(tran.getTransType())) {
                code = "EG01";
            }
            temp.append(code).append("|");
            temp.append("||||");
            temp.append("0").append("|");
            temp.append(tran.getAmount()).append("|");
            temp.append("8888").append("||");
            temp.append("testtest").append("|");
            temp.append(tran.getBeSer()).append("|");
            temp.append("1|other|");

            temp.append("</fileContent>");
            temp.append("</list>");

        }

        StringBuilder end = new StringBuilder();
        end.append("</lists></body></packet>");

        return XML_DECLARATION_GB2312 + head.toString() + temp.toString() + end.toString();

    }


    private String processEG30(String requestString) {
        log.info("============ 浦发银企查询(EG30) 开始 ============");
        QueryRequest request = (QueryRequest) queryRequestParser.fromXML(requestString);

        Transaction transaction = transactionService.findTransactionBySerNo4Query(request.getBody().getSignature().getBody().getElectronNumber());
        log.info("查询的交易为: {}", transaction);
        String retCode = "1";
        if (transaction == null) {
            retCode = "0";
        } else {
            retCode = transaction.getRespCode();
        }

        String htmlBegin = "<![CDATA[<html><head><title>验签名结果</title><result>0</result></head><body><sic><body>";

        String htmlEnd = "</body></sic><cert></cert><certdn>CN=041@714291200-678@2000040752@01000115,OU=Enterprises,OU=SPDB,O=CFCA TEST CA,C=CN</certdn>" +
                "<issuer>CN=CFCA TEST OCA11,O=China Financial Certification Authority,C=CN</issuer>" +
                "<starttime>May 20 07:08:40 2016 GMT</starttime><endtime>May 19 16:00:00 2018 GMT</endtime>" +
                "<certsn>10000000000000000000002011965799</certsn></body></html>]]>";

        StringBuilder html = new StringBuilder();
        html.append("<businessNo>").append(transaction.getBsSer()).append("</businessNo>");
        html.append("<transDate>").append(transaction.getWorkDay()).append("</transDate>");

        html.append("<accountFlag>").append("0").append("</accountFlag>");
        html.append("<messageType>").append("101").append("</messageType>");
        html.append("<businessStatus>").append(retCode).append("</businessStatus>");
        html.append("<refuseCode></refuseCode>");
        html.append("<businessTypeState></businessTypeState>");
        html.append("<protocolNo></protocolNo>" +
                "<businessTypeCode>C200</businessTypeCode>" +
                "<businessLineCode>02001</businessLineCode>" +
                "<currencyType>01</currencyType>");
        html.append("<transAmnt>").append(transaction.getAmount()).append("</transAmnt>");
        html.append("<serviceFee>").append("5.00").append("</serviceFee>");
        html.append("<payerAcctType>").append("0").append("</payerAcctType>");
        html.append("<payerAcctNo>").append(transaction.getAccoNo()).append("</payerAcctNo>");
        html.append("<payerName>").append(transaction.getAccoNo()).append("</payerName>");
        html.append("<payerLiquidationBank>").append(transaction.getBankNo()).append("</payerLiquidationBank>");
        html.append("<payerBankNo>").append(transaction.getBankNo()).append("</payerBankNo>");
        html.append("<payerBankName>").append(transaction.getBankNo()).append("</payerBankName>");
        html.append("<payerBankCity>").append("330100").append("</payerBankCity>");
        html.append("<identityMode></identityMode>" +
                "<identityInfo></identityInfo>" +
                "<payeeType>1</payeeType>");
        html.append("<payeeAcctNo>").append(transaction.getAccoNo()).append("</payeeAcctNo>");
        html.append("<payeeName>").append(transaction.getAccoNo()).append("</payeeName>");
        html.append("<payeeName>钟煦镠</payeeName>" +
                "<ePayeeBankNo>102100099996</ePayeeBankNo>" +
                "<payeeBankName>中国工商银行</payeeBankName>" +
                "<fundUse></fundUse>" +
                "<note>DB_2016071100102842</note>" +
                "<remark></remark>>");
        html.append("<electronNumber>").append(request.getBody().getSignature().getBody().getElecChequeNo()).append("</electronNumber>");

        CompanyResponse.ResponseHead head = new CompanyResponse.ResponseHead();
        head.setTransCode("EG30");
        head.setSignFlag("1");
        head.setPacketID(request.getHead().getPacketID());
        head.setTimeStamp(request.getHead().getTimeStamp());
        head.setReturnCode("AAAAAAA");

        CompanyResponse.ResponseBody body = new CompanyResponse.ResponseBody();
        body.setSignature(htmlBegin + html.toString() + htmlEnd);

        CompanyResponse response = new CompanyResponse();
        response.setHead(head);
        response.setBody(body);

        String retResp = XML_DECLARATION_GB2312 + cResponseParser.toXML(response);

        return retResp;
    }


    private String process8804(String requestString) {
        log.info("============ 浦发银企查询(8804) 开始 ============");
        QueryRequest request = (QueryRequest) queryRequestParser.fromXML(requestString);

        Transaction transaction = transactionService.findTransactionBySerNo4Query(request.getBody().getSignature().getBody().getElecChequeNo());
        log.info("查询的交易为: {}", transaction);
        String retCode = "4";
        if (transaction == null) {
            retCode = "8";
        } else {
            retCode = transaction.getRespCode();
        }

        String htmlBegin = "<![CDATA[<html><head><title>验签名结果</title><result>0</result></head>" +
                "<body><sic><body><totalCount>1</totalCount><lists name=\"LoopResult\"><list>";

        String htmlEnd = "</list></lists></body></sic><cert>12345678910</cert>" +
                "<certdn>CN=041@714291200-678@2000040752@01000115,OU=Enterprises,OU=SPDB,O=CFCA TEST CA,C=CN</certdn>" +
                "<issuer>CN=CFCA TEST OCA11,O=China Financial Certification Authority,C=CN</issuer>" +
                "<starttime>May 20 07:08:40 2016 GMT</starttime><endtime>May 19 16:00:00 2018 GMT</endtime>" +
                "<certsn>10000000000000000000002011965799</certsn></body></html>]]>";

        StringBuilder html = new StringBuilder();
        html.append("<elecChequeNo>").append(request.getBody().getSignature().getBody().getElecChequeNo()).append("</elecChequeNo>");
        html.append("<acceptNo>").append(request.getBody().getSignature().getBody().getElecChequeNo()).append("</acceptNo>");
        html.append("<serialNo>1</serialNo>");
        html.append("<transDate>").append(transaction.getWorkDay()).append("</transDate>");
        html.append("<bespeakDate></bespeakDate>");
        html.append("<PromiseDate>").append(transaction.getWorkDay()).append("</PromiseDate>");
        html.append("<acctNo>").append(transaction.getAccoNo()).append("</acctNo>");
        html.append("<acctName>").append(transaction.getAccoNo()).append("</acctName>");
        html.append("<payeeAcctNo>").append(transaction.getBankNo()).append("</payeeAcctNo>");
        html.append("<payeeName>").append(transaction.getBankNo()).append("</payeeName>");
        html.append("<payeeType>1</payeeType>" +
                "<payeeBankName>浦发上海空港支行营业部</payeeBankName>" +
                "<payeeAddress></payeeAddress>");
        html.append("<amount>").append(transaction.getAmount()).append("</amount>");
        html.append("<sysFlag>0</sysFlag>" +
                "<remitLocation>1</remitLocation>" +
                "<note>SB_201600102840</note>");
        html.append("<transStatus>").append(transaction.getRespCode()).append("</transStatus>");
        html.append("<seqNo>").append(transaction.getBsSer()).append("</seqNo>");

        CompanyResponse.ResponseHead head = new CompanyResponse.ResponseHead();
        head.setTransCode("8804");
        head.setSignFlag("1");
        head.setPacketID(request.getHead().getPacketID());
        head.setTimeStamp(request.getHead().getTimeStamp());
        head.setReturnCode("AAAAAAA");

        CompanyResponse.ResponseBody body = new CompanyResponse.ResponseBody();
        body.setSignature(htmlBegin + html.toString() + htmlEnd);

        CompanyResponse response = new CompanyResponse();
        response.setHead(head);
        response.setBody(body);

        String retResp = XML_DECLARATION_GB2312 + cResponseParser.toXML(response);

        return retResp;
    }

    private String processEG01(String requestString) {
        log.info("============ 浦发银企跨行转账(EG01)-跨行 开始 ============");
        String serNo = configService.getSerNo();
        CompanyRequest request = (CompanyRequest) cRequestParser.fromXML(requestString);

        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_SPDB);
        Config config = configService.findBankSelectConfig(bank.getCode(), "interRedeem");

        String retCode = "1";
        /**
         *   0-失败
         *   1-成功
         *   2-待认证
         *   3-在途
         */
        if (config != null) {
            retCode = config.getK();
            log.info("强制改变赎回(跨行)响应码为: {}", retCode);
        } else {
            log.info("不强制改变赎回(跨行)响应码, 正常赎回...");

        }

        Transaction tran = transactionService.findTransactionBySerNo(request.getBody().getSignature().getBody().getElectronNumber());
        if (tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "8";
        } else {
            log.info("交易不重复,身体呈交易并入库...");
            Transaction transaction = SpdbUtil.cRequest2Transaction(request, bank, serNo, TransTypeEnum.INTERREDEEM.getType(), retCode);
            transactionService.saveTransaction(transaction);
        }

        String html = "<![CDATA[<html><head><title>验签名结果</title><result>0</result></head>" +
                "<body><sic><body><businessNo>IB01607115202588</businessNo></body></sic>" +
                "<cert>12345678910</cert><" +
                "certdn>CN=041@714291200-678@2000040752@01000115,OU=Enterprises,OU=SPDB,O=CFCA TEST CA,C=CN</certdn>" +
                "<issuer>CN=CFCA TEST OCA11,O=China Financial Certification Authority,C=CN</issuer>" +
                "<starttime>May 20 07:08:40 2016 GMT</starttime><endtime>May 19 16:00:00 2018 GMT</endtime>" +
                "<certsn>10000000000000000000002011965799</certsn></body></html>]]>";


        CompanyResponse.ResponseHead head = new CompanyResponse.ResponseHead();
        head.setTransCode("EG01");
        head.setSignFlag("1");
        head.setPacketID(request.getHead().getPacketID());
        head.setTimeStamp(request.getHead().getTimeStamp());
        head.setReturnCode("AAAAAAA");

        CompanyResponse.ResponseBody body = new CompanyResponse.ResponseBody();
        body.setSignature(html);

        CompanyResponse response = new CompanyResponse();
        response.setHead(head);
        response.setBody(body);

        String retResp = XML_DECLARATION_GB2312 + cResponseParser.toXML(response);

        return retResp;
    }

    private String process8801(String requestString) {
        log.info("============ 浦发银企单笔支付(8801)-同行 开始 ============");
        String serNo = configService.getSerNo();
        CompanyRequest request = (CompanyRequest) cRequestParser.fromXML(requestString);

        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_SPDB);
        Config config = configService.findBankSelectConfig(bank.getCode(), "redeem");

        String retCode = "4";
        if (config != null) {
            retCode = config.getK();
            log.info("强制改变赎回响应码为: {}", retCode);
        } else {
            log.info("不强制改变赎回响应码, 正常赎回...");

        }

        Transaction tran = transactionService.findTransactionBySerNo(request.getBody().getSignature().getBody().getElecChequeNo());
        if (tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "8";
        } else {
            log.info("交易不重复,身体呈交易并入库...");
            Transaction transaction = SpdbUtil.cRequest2Transaction(request, bank, serNo, TransTypeEnum.REDEEM.getType(), retCode);
            transactionService.saveTransaction(transaction);
        }

        String html = "<![CDATA[<html><head><title>验签名结果</title><result>0</result></head><body><sic><body>" +
                "<transStatus>" + retCode + "</transStatus><acceptNo>" + RandomStringUtils.randomNumeric(18) + "</acceptNo></body>" +
                "</sic><cert>12345678910</cert>" +
                "<certdn>CN=041@714291200-678@2000040752@01000115,OU=Enterprises,OU=SPDB,O=CFCA TEST CA,C=CN</certdn>" +
                "<issuer>CN=CFCA TEST OCA11,O=China Financial Certification Authority,C=CN</issuer>" +
                "<starttime>May 20 07:08:40 2016 GMT</starttime>" +
                "<endtime>May 19 16:00:00 2018 GMT</endtime>" +
                "<certsn>10000000000000000000002011965799</certsn></body></html>]]>";


        CompanyResponse.ResponseHead head = new CompanyResponse.ResponseHead();
        head.setTransCode("8801");
        head.setSignFlag("1");
        head.setPacketID(request.getHead().getPacketID());
        head.setTimeStamp(request.getHead().getTimeStamp());
        head.setReturnCode("AAAAAAA");

        CompanyResponse.ResponseBody body = new CompanyResponse.ResponseBody();
        body.setSignature(html);

        CompanyResponse response = new CompanyResponse();
        response.setHead(head);
        response.setBody(body);

        String retResp = XML_DECLARATION_GB2312 + cResponseParser.toXML(response);

        return retResp;

    }


    private String processIDFR(String requestString) {
        log.info("============ 浦发非当日充值对账(IDFR)开始 ============");
        Request request = (Request) requestParser.fromXML(requestString);
        HashMap<String, String> plainMap = SpdbUtil.plainToMap(request.getPlain());
        List<Transaction> transactions = transactionService.findTransatcions(AppConstants.BANK_NO_SPDB, TransTypeEnum.PAY.getType(), plainMap.get("OSttDate"));
        StringBuilder plain = new StringBuilder();
        plain.append("SettFile=");
        for (Transaction tran : transactions) {
            plain.append("IPER").append("|");
            plain.append(tran.getWorkDay()).append("|"); //清算日期
            plain.append(tran.getSendTime()).append("|"); //交易发生时间
            plain.append(tran.getBeSer()).append("|"); //订单号
            plain.append(tran.getBsSer()).append("|"); //网关流水号
            plain.append(plainMap.get("MercCode")).append("|"); //商户号
            plain.append("1a|"); //终端号
            plain.append(tran.getAmount()).append("|"); //交易金额
            plain.append("0.00|"); //手续费
            plain.append(tran.getAmount()).append("|"); //净清算金额
            plain.append(tran.getRespCode()).append("|"); //响应吗
            plain.append(tran.getSendTime()).append("|"); //交易备注1
            plain.append("|"); //交易备注2

            plain.append("\\r\\n");
        }

        Response response = new Response();
        response.setTransName("IDFR");
        response.setPlain(plain.toString());
        response.setSignature(request.getSignature());

        return XML_DECLARATION + responseParser.toXML(response);

    }

    private String processBQSR(String requestString) {
        log.info("============ 浦发当日充值对账(BQSR)开始 ============");
        Request request = (Request) requestParser.fromXML(requestString);
        HashMap<String, String> plainMap = SpdbUtil.plainToMap(request.getPlain());
        String start = plainMap.get("BTermSsn");
        String end = plainMap.get("ETermSsn");
        String OTranAbbr = plainMap.get("OTranAbbr");

        List<Transaction> transactions = null;
        if (OTranAbbr.equals("DPER")) {
            //浦发网关当日对账
            transactions = transactionService.findAllTransactions(start, end, TransTypeEnum.PAY.getType(), AppConstants.BANK_NO_SPDB2);
//        } else {
//            浦发快捷
            transactions.addAll(transactionService.findAllTransactions(start, end, TransTypeEnum.PAY.getType(), AppConstants.BANK_NO_SPDB));


        }
        StringBuilder plain = new StringBuilder();
        plain.append("OrderStateList=");
        for (Transaction tran : transactions) {
            plain.append("SettDate=").append(tran.getWorkDay()).append("|");
            plain.append("TermSsn=").append(tran.getBeSer()).append("|");
            plain.append("AcqSsn=").append(tran.getBsSer()).append("|");
            plain.append("TranAmt=").append(tran.getAmount()).append("|");
            plain.append("MercDtTm=").append(tran.getSendTime()).append("|");
            plain.append("RespCode=").append(tran.getRespCode()).append("|");
            String stat = "00";
            if (!"Y".equals(tran.getStat())) {
                stat = "01";
            }
            plain.append("CompFlag=").append(stat);
            plain.append("\\r\\n");
        }
        Response response = new Response();
        response.setTransName("BQSR");
        response.setPlain(plain.toString());
        response.setSignature(request.getSignature());

        return XML_DECLARATION + responseParser.toXML(response);

    }

    private String processIQSR(String requestString) {
        log.info("============ 浦发充值查询(IQSR)开始 ============");
        Request request = (Request) requestParser.fromXML(requestString);
        HashMap<String, String> plainMap = SpdbUtil.plainToMap(request.getPlain());
        String OTranAbbr = plainMap.get("OTranAbbr");

        Transaction transaction = transactionService.findTransactionBySerNo4Query(plainMap.get("TermSsn"));
        String retCode = "00";
        String workDay = "00000000";
        String amount = "0";
        if (transaction == null) {
            if (OTranAbbr.equals("DPER")) {
                retCode = "02";
            } else {
                retCode = "20";
            }
        } else {
            retCode = transaction.getRespCode();
            workDay = transaction.getWorkDay();
            amount = transaction.getAmount();
        }

        StringBuilder respPlain = new StringBuilder();
        respPlain.append("SettDate=").append(workDay).append("|");
        respPlain.append("AcqSsn=").append(plainMap.get("TermSsn")).append("|");
        respPlain.append("TermSsn=").append(plainMap.get("TermSsn")).append("|");
        respPlain.append("TranAmt=").append(amount).append("|");
        if (transaction != null && "Y".equals(transaction.getStat())) {
            respPlain.append("CompFlag=00|");
        } else {
            respPlain.append("CompFlag=01|");
        }
        respPlain.append("RespCode=").append(retCode);

        Response response = new Response();
        response.setPlain(respPlain.toString());
        response.setTransName(request.getTransName());
        response.setSignature(request.getSignature());

        String retResp = XML_DECLARATION + responseParser.toXML(response);

        return retResp;


    }

    private String processKPER(String requestString) {
        log.info("============ 浦发充值(KPER)开始 ============");
        String serNo = configService.getSerNo();
        Request request = (Request) requestParser.fromXML(requestString);
        HashMap<String, String> plainMap = SpdbUtil.plainToMap(request.getPlain());
        String transType = plainMap.get("TranType");

        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_SPDB);
        Config config = configService.findBankSelectConfig(bank.getCode(), "pay");


        String retCode = "00";
        if (config != null) {
            retCode = config.getK();
            log.info("强制改变申购响应码为: {}", retCode);
        } else {
            log.info("不强制改变申购响应码, 正常申购...");
        }

        Transaction tran = transactionService.findTransactionBySerNo(plainMap.get("TermSsn"));
        if (tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "94";
        } else {
            log.info("交易不重复,生成交易并入库...");
            Transaction transaction = SpdbUtil.request2Transaction(plainMap, bank, serNo, TransTypeEnum.PAY.getType(), retCode);

            transactionService.saveTransaction(transaction);
        }

        StringBuilder respPlain = new StringBuilder();
        respPlain.append("TranAbbr=").append(request.getTransName()).append("|");
        respPlain.append("AcqSsn=").append(RandomStringUtils.randomNumeric(12)).append("|");
        respPlain.append("MercDtTm=").append(plainMap.get("MercDtTm")).append("|");
        respPlain.append("TermSsn=").append(plainMap.get("TermSsn")).append("|");
        respPlain.append("RespCode=").append(retCode).append("|");
        respPlain.append("TermCode=0|");
        respPlain.append("MercCode=").append(plainMap.get("MercCode")).append("|");
        respPlain.append("TranAmt=").append(plainMap.get("TranAmt")).append("|");
        respPlain.append("SettDate=").append(plainMap.get("MercDtTm").substring(0, 8));

        Response response = new Response();
        response.setPlain(respPlain.toString());
        response.setTransName(request.getTransName());
        response.setSignature(request.getSignature());

        String retResp = XML_DECLARATION + responseParser.toXML(response);

        return retResp;

    }

    private String processKJQY(String requestString) {
        log.info("============ 浦发短信&鉴权(KJQY)开始 ============");
        Request request = (Request) requestParser.fromXML(requestString);
        HashMap<String, String> plainMap = SpdbUtil.plainToMap(request.getPlain());
        String transType = plainMap.get("TranType");

        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_SPDB);
        Config configSms = configService.findBankSelectConfig(bank.getCode(), "sms");
        Config configVerify = configService.findBankSelectConfig(bank.getCode(), "verify");

        String retCode = "00";
        if (configSms != null && ("0".equals(transType) || "1".equals(transType))) {
            retCode = configSms.getK();
            log.info("强制改变短信响应码为: {}", retCode);
        } else if (configVerify != null && "2".equals(transType)) {
            retCode = configVerify.getK();
            log.info("强制改变鉴权响应码为: {}", retCode);
        } else {
            log.info("不强制改变响应码, 正常...");
        }

        StringBuilder respPlain = new StringBuilder();
        respPlain.append("TranAbbr=").append(request.getTransName()).append("|");
        respPlain.append("Merc_id=").append(plainMap.get("Merc_id")).append("|");
        respPlain.append("MercDtTm=").append(plainMap.get("MercDtTm")).append("|");
        respPlain.append("MercCode=").append(plainMap.get("MercCode")).append("|");
        respPlain.append("RespCode=").append(retCode);

        Response response = new Response();
        response.setPlain(respPlain.toString());
        response.setTransName(request.getTransName());
        response.setSignature(request.getSignature());

        String retResp = XML_DECLARATION + responseParser.toXML(response);

        return retResp;

    }

    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }

}
