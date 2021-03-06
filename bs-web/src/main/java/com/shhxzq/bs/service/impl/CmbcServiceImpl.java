package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.CmbcService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.TransactionService;
import com.shhxzq.bs.util.CmbcUtil;
import com.shhxzq.bs.util.EctUtil;
import com.shhxzq.bs.util.StreamUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by houjiagang on 16/7/19.
 */
@Component
@Log4j2
public class CmbcServiceImpl implements CmbcService {

    private static final String ENCODING = "GBK";


    @Autowired
    private BankService bankService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private TransactionService transactionService;

    @Override
    public String handle(HttpServletRequest request) throws Exception {
        byte[] requsetBody = StreamUtil.readBytes(request.getInputStream());
        String reqStr = new String(requsetBody, ENCODING);
        log.info("Receive the request: {}", reqStr);
        String respStr = coneverResponse(reqStr);
        log.info("Response message is: {}", respStr);
        return respStr;
    }


    private String coneverResponse(String reqStr) {
        Map<String, String> params = EctUtil.parseXml(reqStr);

        if (params.get("CSVReq") != null) {
            if ("".equals(params.get("msgValidationCode"))) {
                return processCSVReqSms(params);
            } else {
                return processCSVReqVerify(params);
            }
        } else if (params.get("COSReq") != null) {
            return processCOSReq(params);
        } else if (params.get("CPReq") != null) {
            return processCPReq(params);
        } else if (params.get("STQReq") != null) {
            return processSTQReq(params);
        }


        return null;
    }


    private String processSTQReq(Map<String, String> params) {
        log.info("============ 民生快捷单笔查询开始 ============");

        Transaction transaction = transactionService.findTransactionBySerNo4Query(params.get("serialNo"));
        String retCode = "0001";
        if (transaction == null) {
            retCode = "0003";
        } else {
            retCode = transaction.getRespCode();
        }

        StringBuilder response = new StringBuilder();
        response.append("<Cartoon>");
        response.append("<Message id=\"").append(params.get("Message")).append("\">");
        response.append("<STQRes id=\"STQRes\">");
        response.append("<version>2.0.0</version>");
        response.append("<instId>").append(params.get("instId")).append("</instId>");
        response.append("<certId>").append(params.get("certId")).append("</certId>");
        response.append("<serialNo>").append(params.get("serialNo")).append("</serialNo>");
        response.append("<date>").append(params.get("date")).append("</date>");
        if ("0003".equals(retCode)) {
            response.append("<amount>").append("0").append("</amount>");
        } else {
            response.append("<amount>").append(transaction.getAmount()).append("</amount>");
        }
        response.append("<charge>").append("0").append("</charge>");
        response.append("<currency>").append("156").append("</currency>");
        response.append("<originalSerialNo>").append("</originalSerialNo>");
        response.append("<originalDate>").append("</originalDate>");
        response.append("<status>").append(retCode).append("</status>");
        response.append("</STQRes></Message></Cartoon>");

        return response.toString();
    }


    /**
     * 申购
     *
     * @param params
     * @return
     */
    private String processCPReq(Map<String, String> params) {
        log.info("============ 民生快捷申购开始 ============");

        String serNo = configService.getSerNo();

        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CMBC);
        Config config = configService.findBankSelectConfig(bank.getCode(), "pay");

        /**
         *  0000：失败（代表银行账务处理失败）
         *  0001：成功（代表银行账务处理成功）
         *  0002：银行处理中（代表银行处理中间状态）
         *  0003：银行查无此订单。
         *
         *
         */
        String retCode = "0001";
        if (config != null) {
            retCode = config.getK();
            log.info("强制改变申购响应码为: {}", retCode);
        } else {
            log.info("不强制改变申购响应码, 正常申购...");
        }
        Transaction tran = transactionService.findTransactionBySerNo(params.get("serialNo"));
        if (tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "0000";
        } else {
            log.info("交易不重复,生成交易并入库...");
            Transaction transaction = CmbcUtil.payRequest2Transaction(params, bank, TransTypeEnum.PAY.getType(), serNo, retCode);
            transactionService.saveTransaction(transaction);
        }

        StringBuilder response = new StringBuilder();
        response.append("<Cartoon>");
        response.append("<Message id=\"").append(params.get("Message")).append("\">");
        response.append("<CPRes id=\"CPRes\">");
        response.append("<version>2.0.0</version>");
        response.append("<instId>").append(params.get("instId")).append("</instId>");
        response.append("<certId>").append(params.get("certId")).append("</certId>");
        response.append("<serialNo>").append(params.get("serialNo")).append("</serialNo>");
        response.append("<signNo>").append(params.get("signNo")).append("</signNo>");
        response.append("<overdraft>").append("N").append("</overdraft>");
        response.append("</CPRes></Message></Cartoon>");

        return response.toString();
    }

    /**
     * 解约
     *
     * @param params
     * @return
     */
    private String processCOSReq(Map<String, String> params) {
        String retCode = "00";
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CMBC);
        Config config = configService.findBankSelectConfig(bank.getCode(), TransTypeEnum.CANCEL.getType());
        if (config != null) {
            retCode = config.getK();
            log.info("强制改变解约响应码为: " + retCode);
        } else {
            log.info("不强制改变解约响应码, 正常解约...");
        }
        StringBuilder response = new StringBuilder();
        response.append("<Cartoon>");
        response.append("<Message id=\"").append(params.get("Message")).append("\">");
        response.append("<COSRes id=\"COSRes\">");
        response.append("<version>2.0.0</version>");
        response.append("<instId>").append(params.get("instId")).append("</instId>");
        response.append("<certId>").append(params.get("certId")).append("</certId>");
        response.append("<date>").append(params.get("date")).append("</date>");
        response.append("<signNo>").append(params.get("bankCardNo")).append("</signNo>");
        response.append("<linkAcctNo>").append(params.get("linkAcctNo")).append("</linkAcctNo>");
        response.append("<status>").append(retCode).append("</status>");
        response.append("</COSRes></Message></Cartoon>");

        return response.toString();
    }

    /**
     * sms
     *
     * @param params
     * @return
     */
    private String processCSVReqSms(Map<String, String> params) {
        log.info("============ 民生快捷发短信开始 ============");

        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CMBC);
        Config config = configService.findBankSelectConfig(bank.getCode(), "sms");
        String code = "8888";
        String msg = "成功";
        if (config != null) {
            code = config.getK();
            msg = config.getVal();
            log.info("强制改变短信响应码为: {} - {}", code, msg);
        } else {
            log.info("不强制改变短信响应码, 正常发短信...");
        }

        StringBuilder response = new StringBuilder();
        response.append("<Cartoon>");
        response.append("<Message id=\"").append(params.get("Message")).append("\">");
        if ("8888".equals(code)) {
            response.append("<CSVRes id=\"CSVRes\">");
            response.append("<version>2.0.0</version>");
            response.append("<instId>").append(params.get("instId")).append("</instId>");
            response.append("<certId>").append(params.get("certId")).append("</certId>");
            response.append("<date>").append(params.get("date")).append("</date>");
            response.append("<signNo>").append(params.get("bankCardNo")).append("</signNo>");
            response.append("<linkAcctNo>").append(params.get("bankCardNo")).append("</linkAcctNo>");
            response.append("<channelSeqNo>").append(RandomStringUtils.randomNumeric(32)).append("</channelSeqNo>");
            response.append("</CSVRes>");
        } else {
            response.append("<Error id=\"Error\">");
            response.append("<version>2.0.0</version>");
            response.append("<instId>").append(params.get("instId")).append("</instId>");
            response.append("<certId>").append(params.get("certId")).append("</certId>");
            response.append("<errorCode>").append(code).append("</errorCode>");
            response.append("<errorMessage>").append(msg).append("</errorMessage>");
            response.append("</Error>");
        }

        response.append("</Message></Cartoon>");

        return response.toString();
    }

    /**
     * verify
     *
     * @param params
     * @return
     */
    private String processCSVReqVerify(Map<String, String> params) {

        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CMBC);
        Config config = configService.findBankSelectConfig(bank.getCode(), "verify");
        String code = "8888";
        String msg = "成功";
        if (config != null) {
            code = config.getK();
            msg = config.getVal();
            log.info("强制改鉴权响应码为: {} - {}", code, msg);
        } else {
            log.info("不强制改变鉴权响应码, 正常鉴权...");
        }

        StringBuilder response = new StringBuilder();
        response.append("<Cartoon>");
        response.append("<Message id=\"").append(params.get("Message")).append("\">");
        if ("8888".equals(code)) {
            response.append("<CSVRes id=\"CSVRes\">");
            response.append("<version>2.0.0</version>");
            response.append("<instId>").append(params.get("instId")).append("</instId>");
            response.append("<certId>").append(params.get("certId")).append("</certId>");
            response.append("<date>").append(params.get("date")).append("</date>");
            response.append("<signNo>").append(params.get("bankCardNo")).append("</signNo>");
            response.append("<linkAcctNo>").append(params.get("bankCardNo")).append("</linkAcctNo>");
            response.append("<channelSeqNo>").append(params.get("channelSeqNo")).append("</channelSeqNo>");
            response.append("</CSVRes>");
        } else {
            response.append("<Error id=\"Error\">");
            response.append("<version>2.0.0</version>");
            response.append("<instId>").append(params.get("instId")).append("</instId>");
            response.append("<certId>").append(params.get("certId")).append("</certId>");
            response.append("<errorCode>").append(code).append("</errorCode>");
            response.append("<errorMessage>").append(msg).append("</errorMessage>");
            response.append("</Error>");
        }

        response.append("</Message></Cartoon>");

        return response.toString();
    }
}
