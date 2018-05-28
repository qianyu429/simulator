package com.shhxzq.bs.util;

import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Transaction;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 16/6/2
 */
public class CmbcUtil {

    public static Transaction buildTransaction(Map<String, String> params, Bank bank, String transType, String serNo, String code, String val) {
        Transaction transaction = new Transaction();
        transaction.setBeSer(params.get("trnId"));// 基金公司流水号
        transaction.setMerId(bank.getMerId());// 商户号
        transaction.setBsSer(serNo);// 银联方流水号
        transaction.setBankNo(bank.getBankNo());// 银行编号
        transaction.setAccoNo(params.get("acntNo"));// 交易账户(卡号)
        transaction.setTransType(transType);// 交易类型
        transaction.setAmount(params.get("amount"));// 交易金额
        transaction.setRespCode(code);// 响应码: e.g. "0"
        transaction.setTransStat("");// 状态码

        String stat = "F";
        if ("0".equals(code)) {
            stat = "Y";
        } else if ("WEC02".equals(code)) {
            stat = "I";
        }

        transaction.setStat(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(params.get("dtClient").substring(0, 10).replaceAll("-", ""));// 工作日
        transaction.setSendTime(DateUtil.format14Date(new Date()));// 基金公司交易时间

        return transaction;
    }


    public static Transaction payRequest2Transaction(Map<String, String> params, Bank bank, String transType, String serNo, String code){
        Transaction transaction = new Transaction();
        transaction.setBeSer(params.get("serialNo"));
        transaction.setMerId(bank.getMerId());
        transaction.setBsSer(serNo);
        transaction.setBankNo(bank.getBankNo());
        transaction.setAccoNo(params.get("signNo"));
        transaction.setTransType(transType);
        transaction.setAmount(params.get("amount"));
        transaction.setRespCode(code);
        transaction.setTransStat("");

        String stat = "F";
        if("0001".equals(code)){
            stat = "Y";
        }

        transaction.setStat(stat);
        transaction.setWorkDay(params.get("date").substring(0,8));
        transaction.setSendTime(DateUtil.format14Date(new Date()));

        return transaction;
    }
}
