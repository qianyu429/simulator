package com.shhxzq.bs.util;

import com.shhxzq.bs.mapping.ccb.Body;
import com.shhxzq.bs.mapping.ccb.Tran;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Transaction;

import java.util.Date;


public class CcbUtil {

    public static Transaction buildTransaction(Tran tran, Bank bank, String transType, String serNo, String code) {
        Transaction transaction = new Transaction();
        transaction.setBeSer(tran.getHeader().getTxseq());// 基金公司流水号
        transaction.setMerId(bank.getMerId());// 商户号
        transaction.setBsSer(serNo);// 银联方流水号
        transaction.setBankNo(bank.getBankNo());// 银行编号
        transaction.setAccoNo(tran.getBody().getAcctNo());// 交易账户(卡号)
        transaction.setTransType(transType);// 交易类型
        transaction.setAmount(tran.getBody().getAmount());// 交易金额
        transaction.setRespCode(code);// 响应码: e.g. "0"
        transaction.setTransStat("");// 状态码

        String stat = "F";
        if ("".equals(code)) {
            stat = "Y";
        }

        transaction.setStat(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(tran.getHeader().getTxdate());// 工作日
        transaction.setSendTime(DateUtil.format14Date(new Date()));// 基金公司交易时间

        return transaction;
    }




}
