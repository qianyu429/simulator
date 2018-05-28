package com.shhxzq.bs.processer;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.mapping.ccb.Body;
import com.shhxzq.bs.mapping.ccb.Tx;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.TransactionService;
import com.shhxzq.bs.utils.CCBUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by houjiagang on 16/8/9.
 */
@Component
@Log4j2
public class CCBProcesser{

    private static final String ENCODING = "GB2312";

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"GB2312\"?>\n";

    private static final Pattern PATTERN_REPLACEMENT;

    private XStream txParser;


    @Autowired
    private BankService bankService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private TransactionService transactionService;


    static {
        PATTERN_REPLACEMENT = Pattern.compile("(?<=<TX_CODE>)(.*?)(?=</TX_CODE>)");
    }


    public String handle(String reqString) throws Exception {
        //init xstream
        txParser = getXStreamInstance();
        txParser.processAnnotations(Tx.class);
        //receive request
        log.info("Receive the request is {}", reqString);
        //convert response by txcode
        String respStr = convertResponse(reqString);
        log.info("Response is {}", respStr);
        return respStr;
    }

    private String convertResponse(String reqString){

        Matcher matcher = PATTERN_REPLACEMENT.matcher(reqString);
        if(! matcher.find()) {
            throw new UnsupportedOperationException("The txcode cannot be found in request xml!");
        }
        String txCode = matcher.group(0);
        switch (txCode) {
            case "6W1303":
                return  process6W1303(reqString);
            case "6W1503":
                return  process6W1503(reqString);

        }
        return null;

    }

    private String process6W1503(String reqString){
        log.info("============ 建行银企业交易查询(6W1503)开始 ============");
        Tx request = (Tx) txParser.fromXML(reqString);
        Transaction transaction = transactionService.findTransactionBySerNo4Query(request.getBody().getRequestSn1());
        String retCode = "000000";
        if (transaction == null) {
            retCode = "0130Z110BB22";
        } else {
            retCode = transaction.getRespCode();
        }
        Config config = configService.findConfigByGrpAndK("ccb-code-redeem", retCode);
        if (config == null) {
            config = new Config();
            config.setVal("");
        }

        //拼装response
        Body body = new Body();
        body.setCreditNo(RandomStringUtils.randomNumeric(12));
        if("000000".equals(retCode)) {
            body.setDealResult("4");
        } else {
            body.setDealResult("3");
        }
        body.setMessage("");

        Tx response = new Tx();
        response.setRequestSn(request.getRequestSn());
        response.setCustId(request.getCustId());
        response.setTxCode(request.getTxCode());
        if("0130Z110BB22".equals(retCode)) {
            response.setReturnCode(retCode);
            response.setReturnMsg("查询不到交易");
        } else {
            response.setReturnCode("000000");
            response.setReturnMsg("");
        }

        response.setLanguage("CN");
        response.setBody(body);

        String respStr =  XML_DECLARATION + txParser.toXML(response);
        log.info("============ 建行银企业交易查询(6W1503)结束 ============");
        return respStr;

    }

    private String process6W1303(String reqString){
        log.info("============ 建行银企业单笔代发(6W1303)开始 ============");
        String serNo = configService.getSerNo();
        Tx request = (Tx) txParser.fromXML(reqString);
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CCB);
        Config config = configService.findBankSelectConfig(bank.getCode(), "redeem");
        String retCode = "000000";
        String retMsg = "SUCCESS";
        if(config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
            log.info("强制改变取现响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常取现...");
        }
        Transaction tran = transactionService.findTransactionBySerNo(request.getRequestSn());
        if(tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "0130Z110B144";
            retMsg = "记录重复，请与当地银行联系";
        } else {
            log.info("交易不重复,生成交易并入库...");
            Transaction transaction = CCBUtil.buildTransaction(request, bank, TransTypeEnum.REDEEM.getType(), serNo, retCode);
            transactionService.saveTransaction(transaction);
        }

        //拼装response
        Body body = new Body();
        body.setCreditNo(RandomStringUtils.randomNumeric(12));
        body.setIndividualName1("");
        body.setIndividual1("");
        body.setIndividualName2("");
        body.setIndividual2("");
        body.setRem1("");
        body.setRem2("");

        Tx response = new Tx();
        response.setRequestSn(request.getRequestSn());
        response.setCustId(request.getCustId());
        response.setTxCode(request.getTxCode());
        response.setReturnCode(retCode);
        response.setReturnMsg(retMsg);
        response.setLanguage("CN");
        response.setBody(body);

        String respStr = XML_DECLARATION + txParser.toXML(response);
        log.info("============ 建行银企业单笔代发(6W1303)结束 ============");
        return respStr;

    }



    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }
}
