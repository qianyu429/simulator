package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.Spdb6Service;
import com.shhxzq.bs.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zuodongxiang on 17/4/20.
 */
@Component
@Log4j2
public class Spdb6ServiceImpl implements Spdb6Service {

    @Autowired
    private ConfigService configService;
    @Autowired
    private BankService bankService;
    @Autowired
    private TransactionService transactionService;

    private static final String RESP_XML_HEADER_TEMPLATE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><STPContext><Header><RespCode>00</RespCode><RespDesc>交易成功</RespDesc></Header>";


    @Override
    public String verify() {
        log.info("============ 受托支付身份核实开始 ============");
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_SPDB6);
        Config configVerify = configService.findBankSelectConfig(bank.getCode(), "verify");
        String retCode = "AC00";
        String retMsg = "认证成功";
        if (configVerify != null) {
            retCode = configVerify.getK();
            retMsg = configVerify.getVal();
        }
        String responseXml = RESP_XML_HEADER_TEMPLATE + "<Body><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<INFO><RespCode>" + retCode +
                "</RespCode><RespDesc>" + retMsg +
                "</RespDesc></INFO>]]></Body></STPContext>";
        return responseXml;
    }

    @Override
    public String deliy() {
        log.info("============ 受托支付获取对称秘钥值开始 ============");
        String responseXml = RESP_XML_HEADER_TEMPLATE + "<Body><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<INFO><KeyValue>g7gGlSS0HlQ8vgLaRFoatQ==</KeyValue></INFO>]]></Body></STPContext>";
        return responseXml;

    }

    @Override
    public String pay(Map<String, String> params) {
        log.info("============ 受托支付代扣开始 ============");
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_SPDB6);
        Config config = configService.findBankSelectConfig(bank.getCode(), "pay");//查询代扣响应码
        String retCode = "00";
        String retMsg = "成功";
        if (config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
        }
        String reqTransID = params.get("ReqTransID");//交易请求流水
        //如果交易流水已存在,则不插入代扣数据
        Transaction resultTransaction = transactionService.findTransactionBySerNo(reqTransID);
        String responseXml;
        if (resultTransaction != null) {
            retCode = "SYS02";
            retMsg = "重复交易";
            responseXml = RESP_XML_HEADER_TEMPLATE + "<Body><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<INFO><RespCode>" + retCode +
                    "</RespCode><RespDesc>" + retMsg +
                    "</RespDesc></INFO>]]></Body></STPContext>";
        } else {
            String transAmount = params.get("TransAmount");//交易金额
            Transaction transaction = new Transaction();
            transaction.setAmount(transAmount);
            transaction.setBeSer(reqTransID);
            transaction.setRespCode(retCode);
            transaction.setBankNo(AppConstants.BANK_NO_SPDB6);
            transaction.setTransType("pay");
            transaction.setMemo1(retMsg);//向预留字段中添加错误码描述,用于代扣查询
            transactionService.saveTransaction(transaction);

            responseXml = RESP_XML_HEADER_TEMPLATE + "<Body><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<INFO><RespCode>" + retCode +
                    "</RespCode><RespDesc>" + retMsg +
                    "</RespDesc><RealTransAmount>" + transAmount +
                    "</RealTransAmount></INFO>]]></Body></STPContext>";
        }
        return responseXml;
    }

    @Override
    public String queryPay(Map<String, String> params) {
        log.info("============ 受托支付代扣查询开始 ============");
        String reqTransID = params.get("OrgReqTransID");//交易请求流水
        Transaction transaction = transactionService.findTransactionBySerNo4Query(reqTransID);
        String retCode;
        String retMsg;
        String transAmount = null;
        if (transaction == null) {
            retCode = "TF04";
            retMsg = "交易不存在";
            transAmount = "";
        } else {
            transAmount = transaction.getAmount();
            retCode = transaction.getRespCode();
            retMsg = transaction.getMemo1();
            if (transAmount == null) {
                transAmount = "";
            }
        }
        String responseXml = RESP_XML_HEADER_TEMPLATE + "<Body><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<INFO><RespCode>" + retCode +
                "</RespCode><RespDesc>" + retMsg +
                "</RespDesc><RealTransAmount>" + transAmount +
                "</RealTransAmount></INFO>]]></Body></STPContext>";
        return responseXml;
    }

    @Override
    public String redeem(Map<String, String> params) {
        log.info("============ 受托支付代付开始 ============");
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_SPDB6);
        Config config = configService.findBankSelectConfig(bank.getCode(), "redeem");//查询代扣响应码
        String retCode = "00";
        String retMsg = "成功";
        if (config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
        }
        String reqTransID = params.get("ReqTransID");//交易请求流水
        String responseXml;
        //如果交易流水已存在,则不插入代付数据
        Transaction resultTransaction = transactionService.findTransactionBySerNo(reqTransID);
        if (resultTransaction != null) {
            retCode = "SYS02";
            retMsg = "重复交易";
            responseXml = RESP_XML_HEADER_TEMPLATE + "<Body><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<INFO><RespCode>" + retCode +
                    "</RespCode><RespDesc>" + retMsg +
                    "</RespDesc></INFO>]]></Body></STPContext>";
        } else {
            String transAmount = params.get("TransAmount");//交易金额
            Transaction transaction = new Transaction();
            transaction.setAmount(transAmount);
            transaction.setBeSer(reqTransID);
            transaction.setBankNo(AppConstants.BANK_NO_SPDB6);
            transaction.setRespCode(retCode);
            transaction.setTransType("redeem");
            transaction.setMemo1(retMsg);//向预留字段中添加错误码描述,用于代付查询
            transactionService.saveTransaction(transaction);
            responseXml = RESP_XML_HEADER_TEMPLATE + "<Body><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<INFO><RespCode>" + retCode +
                    "</RespCode><RespDesc>" + retMsg +
                    "</RespDesc><RealTransAmount>" + transAmount +
                    "</RealTransAmount></INFO>]]></Body></STPContext>";
        }
        return responseXml;
    }

    @Override
    public String queryRedeem(Map<String, String> params) {
        log.info("============ 受托支付代付查询开始 ============");
        String reqTransID = params.get("OrgReqTransID");//交易请求流水
        Transaction transaction = transactionService.findTransactionBySerNo4Query(reqTransID);
        String retCode;
        String retMsg;
        String transAmount = null;
        if (transaction == null) {
            retCode = "TF04";
            retMsg = "交易不存在";
            transAmount = "";
        } else {
            transAmount = transaction.getAmount();
            retCode = transaction.getRespCode();
            retMsg = transaction.getMemo1();
            if (transAmount == null) {
                transAmount = "";
            }
        }
        String responseXml = RESP_XML_HEADER_TEMPLATE + "<Body><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<INFO><RespCode>" + retCode +
                "</RespCode><RespDesc>" + retMsg +
                "</RespDesc><RealTransAmount>" + transAmount +
                "</RealTransAmount></INFO>]]></Body></STPContext>";
        return responseXml;
    }

}
