package com.shhxzq.bs.util;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.mapping.spdb.CompanyRequest;
import com.shhxzq.bs.mapping.spdb.Request;
import com.shhxzq.bs.mapping.spdb.Response;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.TransactionService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by houjiagang on 16/7/12.
 */
@Log4j2
public class SpdbUtil {

    public static Transaction request2Transaction(HashMap<String, String> map, Bank bank, String serNo, String transType, String retCode){
        Transaction transaction = new Transaction();
        transaction.setBeSer(map.get("TermSsn"));
        transaction.setMerId(bank.getMerId());
        transaction.setBsSer(serNo);
        transaction.setBankNo(bank.getBankNo());
        transaction.setAccoNo(map.get("IdNo"));
        transaction.setTransType(transType);
        transaction.setAmount(map.get("TranAmt"));
        transaction.setRespCode(retCode);
        transaction.setTransStat("");

        String stat = "F";
        if ("00".equals(retCode)) {
            stat = "Y";
        }
        transaction.setStat(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(map.get("MercDtTm").substring(0,8));// 工作日
        transaction.setSendTime(DateUtil.format14Date(new Date()));// 基金公司交易时间

        return transaction;

    }

    public static Transaction wRequest2Transaction(HashMap<String, String> map, Bank bank, String serNo, String transType, String retCode){
        Transaction transaction = new Transaction();
        transaction.setBeSer(map.get("TermSsn"));
        transaction.setMerId(bank.getMerId());
        transaction.setMemo1(map.get("MercCode"));
        transaction.setBsSer(serNo);
        transaction.setBankNo(bank.getBankNo());
        transaction.setTransType(transType);
        transaction.setAmount(map.get("TranAmt"));
        transaction.setRespCode(retCode);
        transaction.setTransStat("");

        String stat = "F";
        if ("00".equals(retCode)) {
            stat = "Y";
        }
        transaction.setStat(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(map.get("MercDtTm").substring(0,8));// 工作日
        transaction.setSendTime(DateUtil.format14Date(new Date()));// 基金公司交易时间

        return transaction;

    }

    public static Transaction cRequest2Transaction(CompanyRequest request, Bank bank, String serNo, String transType, String retCode){
        SimpleDateFormat formate = new SimpleDateFormat("yyyyMMdd");

        Transaction transaction = new Transaction();
        if("redeem".equals(transType)){
            transaction.setBeSer(request.getBody().getSignature().getBody().getElecChequeNo());
        } else {
            transaction.setBeSer(request.getBody().getSignature().getBody().getElectronNumber());
        }

        transaction.setMerId(bank.getMerId());
        transaction.setBsSer(serNo);
        transaction.setBankNo(bank.getBankNo());
        transaction.setAccoNo(request.getBody().getSignature().getBody().getAcctNo());
        transaction.setTransType(transType);
        transaction.setAmount(request.getBody().getSignature().getBody().getAmount());
        transaction.setRespCode(retCode);
        transaction.setTransStat("");

        String stat = "F";
        if ("1".equals(retCode)){
            stat = "Y";
        }
        transaction.setStat(stat);
        transaction.setWorkDay(formate.format(new Date()));
        transaction.setSendTime(DateUtil.format14Date(new Date()));

        return transaction;
    }

    public static HashMap<String, String> plainToMap(String plain) {
        String key = "";
        String value = "";
        HashMap<String, String> plainMap = new HashMap<String, String>();

        String[] plains = plain.split("\\|");
        for (String attr : plains) {
            String[] attrs = attr.split("=");
            key = attrs[0];
            value = attrs.length < 2 ? "" : attrs[1];
            plainMap.put(key, value);
        }
        return plainMap;
    }


}
