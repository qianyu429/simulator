package com.shhxzq.bs.util;

import com.shhxzq.bs.mapping.sh.SHYQRequest;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Transaction;

import java.util.Date;


public class SHYQUtil {

    public static Transaction buildTransaction(SHYQRequest request, Bank bank, String transType, String serNo, String code) {
        Transaction transaction = new Transaction();
        transaction.setBeSer(request.getOpReq().getSerialNo());// 基金公司流水号
        transaction.setMerId(bank.getMerId());// 商户号
        transaction.setBsSer(serNo);// 银联方流水号
        transaction.setBankNo(bank.getBankNo());// 银行编号
        transaction.setAccoNo(request.getOpReq().getReqParam().getACNO());// 交易账户(卡号)
        transaction.setTransType(transType);// 交易类型
        transaction.setAmount(request.getOpReq().getReqParam().getTRAM());// 交易金额
        transaction.setRespCode(code);// 响应码: e.g. "0"
        transaction.setTransStat("");// 状态码

        String stat = "F";
        if ("0".equals(code)) {
            stat = "Y";
        }

        transaction.setStat(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(DateUtil.format8Date(new Date()));// 工作日
        transaction.setSendTime(DateUtil.format14Date(new Date()));// 基金公司交易时间

        return transaction;
    }




}
