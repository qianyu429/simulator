package com.shhxzq.bs.util;

import com.shhxzq.bs.mapping.boc.Request;
import com.shhxzq.bs.mapping.icbc.CMS;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Transaction;

import java.util.Date;


public class BocUtil {

    public static Transaction buildTransaction(Request request, Bank bank, String transType, String serNo, String code) {
        Transaction transaction = new Transaction();
        transaction.setBeSer(request.getBody().getTraceNo());// 基金公司流水号
        transaction.setMerId(bank.getMerId());// 商户号
        transaction.setBsSer(serNo);// 银联方流水号
        transaction.setBankNo(bank.getBankNo());// 银行编号
        transaction.setAccoNo(request.getBody().getCardNo());// 交易账户(卡号)
        transaction.setTransType(transType);// 交易类型
        transaction.setAmount(request.getBody().getAmount());// 交易金额
        transaction.setRespCode(code);// 响应码: e.g. "0"
        transaction.setTransStat("");// 状态码

        String stat = "F";
        if ("OK".equals(code)) {
            stat = "Y";
        }

        transaction.setStat(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(request.getHead().getRequestTime().substring(0, 8));// 工作日
        transaction.setSendTime(DateUtil.format14Date(new Date()));// 基金公司交易时间

        return transaction;
    }




}
