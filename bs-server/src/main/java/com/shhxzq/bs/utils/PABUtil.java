package com.shhxzq.bs.utils;

import com.shhxzq.bs.mapping.pab.Result;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.util.DateUtil;

import java.util.Date;


public class PABUtil {

    public static Transaction buildTransaction(Result request, Bank bank, String transType, String serNo, String code) {
        Transaction transaction = new Transaction();
        transaction.setBeSer(request.getThirdVoucher());// 基金公司流水号
        transaction.setMerId(bank.getMerId());// 商户号
        transaction.setBsSer(serNo);// 银联方流水号
        transaction.setBankNo(bank.getBankNo());// 银行编号
        if("pay".equals(transType)){
            transaction.setAccoNo(request.getHoResultSet4047R().getOppAccNo());// 交易账户(卡号)
            transaction.setAmount(String.valueOf(request.getHoResultSet4047R().getAmount().toString()));// 交易金额
            transaction.setRespCode(code);// 响应码: e.g. "0"
            String stat = "F";
            if ("000000".equals(code)) {
                stat = "Y";
                transaction.setRespCode("0000");
            }
            transaction.setStat(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        } else {
            transaction.setAccoNo(request.getOutAcctNo());
            transaction.setAmount(String.valueOf(request.getTranAmount()));
            transaction.setRespCode(code);
            String stat = "F";
            if("000000".equals(code)) {
                stat = "Y";
            }
            transaction.setStat(stat);
        }
        transaction.setTransType(transType);// 交易类型
        transaction.setTransStat("");// 状态码
        transaction.setWorkDay(DateUtil.format8Date(new Date()));// 工作日
        transaction.setSendTime(DateUtil.format14Date(new Date()));// 基金公司交易时间

        return transaction;
    }




}
