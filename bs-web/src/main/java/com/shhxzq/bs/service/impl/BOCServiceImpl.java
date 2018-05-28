package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.mapping.boc.Body;
import com.shhxzq.bs.mapping.boc.Head;
import com.shhxzq.bs.mapping.boc.Request;
import com.shhxzq.bs.mapping.boc.Response;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BOCService;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.TransactionService;
import com.shhxzq.bs.util.Base64Util;
import com.shhxzq.bs.util.BocUtil;
import com.shhxzq.bs.util.DateUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by houjiagang on 16/8/3.
 */
@Component
@Log4j2
public class BOCServiceImpl implements BOCService {

    private static final String ENCODING = "UTF-8";

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

    private XStream requestParser;

    private XStream responseParser;

    @Autowired
    private BankService bankService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private TransactionService transactionService;

    @Override
    public String handle(HttpServletRequest request) throws Exception {
        //init xstream
        requestParser = getXStreamInstance();
        requestParser.processAnnotations(Request.class);
        responseParser = getXStreamInstance();
        responseParser.processAnnotations(Response.class);
        //receive request&messageId
        String messageId = request.getParameter("messageId");
        messageId = Base64Util.base64Decode(messageId, "UTF-8");
        String reqStr = request.getParameter("signature");
        log.info("The messageId is {}", messageId);
        log.info("Receive the request is {}", reqStr);
        //convert response by messageId
        String respStr = convertResponse(messageId, reqStr);
        log.info("Response is {}", respStr);
        return respStr;
    }

    private String convertResponse(String messageID, String reqString){

        switch (messageID) {
            case "215101":
                return processPreSign(reqString);
            case "215102":
                return processSign(reqString);
            case "215105":
                return processPay(reqString);
            case "215108":
                return processPayQuery(reqString);
        }
        return null;

    }

    private String processPayQuery(String reqString){
        log.info("============ 中行快捷订单支付查询(215108)开始 ============");
        Request request = (Request) requestParser.fromXML(reqString);
        Transaction transaction = transactionService.findTransactionBySerNo4Query(request.getBody().getTraceNo());
        String retCode = "OK";
        if (transaction == null) {
            retCode = "E15000047";
            transaction = new Transaction();
            transaction.setAmount("0");
            transaction.setStat("F");
        } else {
            retCode = transaction.getRespCode();
        }
        Config config = configService.findConfigByGrpAndK("boc-code-pay", retCode);
        if (config == null) {
            config = new Config();
            config.setVal("");
        }

        Head head = new Head();
        if ("E15000047".equals(retCode)) {
            head.setResponseCode(retCode);
            head.setResponseInfo(config.getVal());
        } else {
            head.setResponseCode("OK");
            head.setResponseInfo("");
        }

        Body body = new Body();
        body.setMerchantNo("80000210001010");
        body.setTraceNo(request.getBody().getTraceNo());
        body.setCardNo(request.getBody().getCardNo());
        body.setTranCode(request.getBody().getTranCode());
        body.setCurrency("156");
        body.setAmount(transaction.getAmount());
        body.setTranStatus("1");
        if(!"OK".equals(retCode)){
            body.setTranStatus("2");
        }
        body.setRecvTime(DateUtil.format14Date(new Date()));
        body.setTranTime(DateUtil.format14Date(new Date()));

        Response response = new Response();
        response.setHead(head);
        if(!"E15000047".equals(retCode)) {
            response.setBody(body);
        }
        String respStr =  XML_DECLARATION + responseParser.toXML(response);
        log.info("============ 中行快捷订单支付查询(215108)结束 ============");
        return respStr;

    }

    private String processPay(String reqString){
        log.info("============ 中行快捷订单支付(215105)开始 ============");
        String serNo = configService.getSerNo();
        Request request = (Request) requestParser.fromXML(reqString);
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_BOC);
        Config config = configService.findBankSelectConfig(bank.getCode(), "pay");
        String retCode = "OK";
        String retMsg = "成功";
        if(config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
            log.info("强制改变申购响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常申购...");
        }
//        Config configStat = configService.findBankSelectConfig(bank.getCode(), "payStat");
//        String tranStat = "1";
//        if(configStat != null) {
//            tranStat = configStat.getK();
//            log.info("强制改变申购状态码为: {}", tranStat);
//        } else {
//            log.info("不强制改变申购状态码, 正常申购...");
//        }

        Transaction tran = transactionService.findTransactionBySerNo(request.getBody().getTraceNo());
        if(tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "E15000002";
            retMsg = "商户交易流水号重复";
        } else {
            log.info("交易不重复,生成交易并入库...");
            Transaction transaction = BocUtil.buildTransaction(request, bank, TransTypeEnum.PAY.getType(), serNo, retCode);
            transactionService.saveTransaction(transaction);
        }

        //拼装response
        Head head = new Head();
        head.setResponseCode(retCode);
        head.setResponseInfo(retMsg);
        head.setRequestSeq(request.getHead().getRequestSeq());

        Body body = new Body();
        body.setMerchantNo("80000210001010");
        body.setTraceNo(request.getBody().getTraceNo());
        body.setCardNo(request.getBody().getCardNo());
        body.setCurrency("156");
        body.setAmount(request.getBody().getAmount());
        body.setRecvTime(DateUtil.format14Date(new Date()));
        body.setTranTime(DateUtil.format14Date(new Date()));

        Response response = new Response();
        response.setHead(head);
        if ("OK".equals(retCode)) {
            response.setBody(body);
        }

        String respStr = XML_DECLARATION + responseParser.toXML(response);
        log.info("============ 中行快捷订单支付(215105)结束 ============");
        return respStr;

    }

    private String processSign(String reqString){
        log.info("============ 中行快捷支付签约(215102)开始 ============");
        Request request = (Request) requestParser.fromXML(reqString);
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_BOC);
        Config config = configService.findBankSelectConfig(bank.getCode(), "verify");
        String retCode = "OK";
        String retMsg = "成功";
        if(config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
            log.info("强制改变鉴权响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常鉴权...");
        }
        //拼装response
        Head head = new Head();
        head.setResponseCode(retCode);
        head.setResponseInfo(retMsg);
        head.setRequestSeq(request.getHead().getRequestSeq());

        Response response = new Response();
        response.setHead(head);

        String respStr = XML_DECLARATION + responseParser.toXML(response);
        log.info("============ 中行快捷支付签约(215102)结束 ============");
        return respStr;
    }

    private String processPreSign(String reqString){
        log.info("============ 中行快捷预签约(215101)开始 ============");
        Request request = (Request) requestParser.fromXML(reqString);
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_BOC);
        Config config = configService.findBankSelectConfig(bank.getCode(), "sms");
        String retCode = "OK";
        String retMsg = "成功";
        if(config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
            log.info("强制改变短信响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常发短信...");
        }
        //拼装response
        Head head = new Head();
        head.setResponseCode(retCode);
        head.setResponseInfo(retMsg);
        head.setRequestSeq(request.getHead().getRequestSeq());

        Body body = new Body();
        body.setUniqueCode("888");

        Response response = new Response();
        response.setHead(head);
        if ("OK".equals(retCode)) {
            response.setBody(body);
        }

        String respStr = XML_DECLARATION + responseParser.toXML(response);
        log.info("============ 中行快捷预签约(215101)结束 ============");
        return respStr;
    }




    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }
}
