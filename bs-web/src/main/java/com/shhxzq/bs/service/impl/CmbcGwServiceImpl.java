package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.CmbcGwService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zuodongxiang on 17/6/5.
 */
@Component
@Log4j2
public class CmbcGwServiceImpl implements CmbcGwService {
    @Autowired
    ConfigService configService;
    @Autowired
    BankService bankService;
    @Autowired
    private TransactionService transactionService;

    @Override
    public String verify(String param) {
        log.info("============ 民生网关鉴权开始 ============");
        String[] params = param.split("\\|");
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CMBCGW);
        Config config = configService.findBankSelectConfig(bank.getCode(), TransTypeEnum.VERIFY.getType());
        String retCode = "0000";
        String retMsg = "交易成功";
        if (config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
        }
        String ret = params[0] + "|" + params[1] + "|" + params[2] +
                "|" + params[3] + "|" + params[4] + "|" +
                params[5] + "|" + retCode + "|" + retMsg + "|" + params[7];
        log.info("============民生网关鉴权响应数据============");
        log.info(ret);
        return ret;

    }

    @Override
    public String pay(String param) {
        log.info("============ 民生网关充值开始 ============");
        String[] params = param.split("\\|");
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CMBCGW);
        Config config = configService.findBankSelectConfig(bank.getCode(), TransTypeEnum.PAY.getType());
        String retCode = "0000";
        String retMsg = "交易成功";
        if (config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
        }
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sf1 = new SimpleDateFormat("HHmmss");
        String nowDate = sf.format(date);
        String nowTime = sf1.format(date);
        //落库
        Transaction transaction = new Transaction();
        transaction.setAmount(params[5]);//交易金额
        transaction.setBeSer(params[2]);//订单号
        transaction.setRespCode(retCode);
        transaction.setBankNo(AppConstants.BANK_NO_CMBCGW);
        transaction.setTransType(TransTypeEnum.PAY.getType());
        transaction.setMemo1(retMsg);//向预留字段中添加错误码描述,用于查询
        transaction.setMemo2(params[7]);//向预留字段中添加商品基金描述,用于查询
        transactionService.saveTransaction(transaction);
        String ret = params[0] + "|" + params[1] + "|" + params[2] + "|"
                + params[4] + "|" + params[5] + "|" + nowDate + "|"
                + nowDate + "|" + nowTime + "|" + params[7] + "|" +
                "0" + "|" + retCode + "|" + retMsg;
        log.info("============ 民生网关充值响应数据 ============");
        log.info(ret);
        return ret;

    }

    @Override
    public String query(String param) {
        log.info("============ 民生网关单笔查询开始 ============");
        String[] params = param.split("\\|");
        String reqTransID = params[2];//获取订单号
        Transaction transaction = transactionService.findTransactionBySerNo4Query(reqTransID);
        String retCode = "0000";
        String retMsg = "交易成功";
        String transAmount = "";
        String orderDate = "";
        String productId = "";
        String orderStatus = "";
        if (transaction == null) {
            retCode = "1001";
            retMsg = "交易状态未知";
            orderStatus = "99";
        } else {
            orderStatus = "01";
            transAmount = transaction.getAmount();
            retCode = transaction.getRespCode();
            retMsg = transaction.getMemo1();
            productId = transaction.getMemo2();
            orderDate = transaction.getCreatedDate();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        }
        String ret = params[0] + "|" + params[1] + "|" + params[2]
                + "|" + "01" + "|" + transAmount + "|" +
                orderDate + "|" + productId + "|" + "0" +
                "|" + orderStatus + "|" + retCode + "|" + retMsg;
        log.info("============ 民生网关单笔查询响应数据 ============");
        log.info(ret);
        return ret;
    }
}
