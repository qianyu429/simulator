package com.shhxzq.bs.utils;

import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.util.DateUtil;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by houjiagang on 16/7/12.
 */
public class GDNYUtil {

    public static Transaction request2Transaction(HashMap<String, String> map, Bank bank, String serNo, String transType, String retCode){
        Transaction transaction = new Transaction();
        transaction.setBeSer(map.get("SEQ_NO"));
        transaction.setMerId(bank.getMerId());
        transaction.setBsSer(serNo);
        transaction.setBankNo(bank.getBankNo());
        transaction.setAccoNo(map.get("CARD_NO"));
        if(TransTypeEnum.REDEEM.getType().equals(transType)){
            transaction.setAccoNo(map.get("ACCT_NO"));
        }
        transaction.setTransType(transType);
        transaction.setAmount(map.get("TX_AMT"));
        transaction.setRespCode(retCode);
        transaction.setTransStat("");

        String stat = "F";
        if ("00".equals(retCode)) {
            stat = "Y";
        }
        transaction.setStat(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(map.get("TRANS_TIME").substring(0,8));// 工作日
        transaction.setSendTime(DateUtil.format14Date(new Date()));// 基金公司交易时间

        return transaction;

    }


}
