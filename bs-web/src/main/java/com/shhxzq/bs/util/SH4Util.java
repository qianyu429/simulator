package com.shhxzq.bs.util;

import com.shhxzq.bs.mapping.sh4.Banksh;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Transaction;

import java.util.Date;

/**
 * Created by zhangzhenzhen on 17/7/14.
 */
public class SH4Util {
    public static Transaction buildTransaction(Banksh request, Bank bank, String transType, String serNo, String code) {
        Transaction transaction = new Transaction();
        transaction.setBeSer(request.getMessage().getId());// 基金公司流水号
        transaction.setMerId(bank.getMerId());// 商户号
        transaction.setBsSer(serNo);// 银联方流水号
        transaction.setBankNo(bank.getBankNo());// 银行编号
        transaction.setTransType(transType);// 交易类型
        transaction.setAmount(request.getMessage().getZLRequest().getAmount());// 交易金额
        transaction.setRespCode(code);// 响应码: e.g. "0"
        transaction.setTransStat("");// 状态码

        String stat = "F";
        if ("succ".equals(code)) {
            stat = "Y";
        }

        transaction.setStat(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(DateUtil.format8Date(new Date()));// 工作日
        transaction.setSendTime(DateUtil.format14Date(new Date()));// 基金公司交易时间

        return transaction;
    }

}
