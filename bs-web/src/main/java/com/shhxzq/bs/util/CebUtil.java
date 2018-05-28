package com.shhxzq.bs.util;

import com.shhxzq.bs.mapping.ccb.Tran;
import com.shhxzq.bs.mapping.ceb.MessageSuit;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Transaction;

import java.util.Date;


public class CebUtil {

    public static Transaction buildTransaction(MessageSuit messageSuit, Bank bank, String transType, String serNo, String code) {
        Transaction transaction = new Transaction();
        transaction.setBeSer(messageSuit.getMessage().getPlain().getSerialNo());// 基金公司流水号
        transaction.setMerId(bank.getMerId());// 商户号
        transaction.setBsSer(serNo);// 银联方流水号
        transaction.setBankNo(bank.getBankNo());// 银行编号
        transaction.setAccoNo(messageSuit.getMessage().getPlain().getMerId());// 交易账户(卡号)
        transaction.setTransType(transType);// 交易类型
        transaction.setAmount(messageSuit.getMessage().getPlain().getAmount());// 交易金额
        transaction.setRespCode(code);// 响应码: e.g. "0"
        transaction.setTransStat("");// 状态码

        String stat = "F";
        if ("".equals(code)) {
            stat = "Y";
        }

        transaction.setStat(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(messageSuit.getMessage().getPlain().getDate().substring(0, 7));// 工作日
        transaction.setSendTime(DateUtil.format14Date(new Date()));// 基金公司交易时间

        return transaction;
    }


    public static Transaction buildredeemTransaction(com.shhxzq.bs.mapping.ceb.Transaction cebTransaction, Bank bank, String transType, String serNo, String code) {
        Transaction transaction = new Transaction();
        transaction.setBeSer(cebTransaction.getTransHead().getBatchID());// 基金公司流水号
        transaction.setMerId(bank.getMerId());// 商户号
        transaction.setBsSer(serNo);// 银联方流水号
        transaction.setBankNo(bank.getBankNo());// 银行编号
        transaction.setAccoNo(cebTransaction.getTransContent().getReqData().getAccountNo());// 交易账户(卡号)
        transaction.setTransType(transType);// 交易类型
        transaction.setAmount(cebTransaction.getTransContent().getReqData().getAmount());// 交易金额
        transaction.setRespCode(code);// 响应码: e.g. "0"
        transaction.setTransStat("");// 状态码

        String stat = "F";
        if ("0000".equals(code)) {
            stat = "Y";
        }

        transaction.setStat(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(cebTransaction.getTransHead().getJnlDate());// 工作日
        transaction.setSendTime(DateUtil.format14Date(new Date()));// 基金公司交易时间

        return transaction;
    }



}
