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
public class EctUtils {

    /**
     * 解析xml报文
     *
     * @param xml
     * @return
     */
    public static Map<String, String> parseXml(String xml) {
        Map<String, String> map = new HashMap();
        try {
            Document doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
            buildMap(root, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap();
        }
        return map;
    }

    private static void buildMap(Element element, Map<String, String> map) {
        List<Element> list = element.elements();
        if(element.attributeCount() > 0){
            map.put(element.getName(), element.attribute(0).getValue());
        } else {
            map.put(element.getName(), element.getTextTrim());
        }

        for (Element e : list) {
            buildMap(e, map);
        }
    }

    public static Transaction buildTransaction(Map<String, String> params, Bank bank, String transType, String serNo, String code, String val) {
        Transaction transaction = new Transaction();
        transaction.setBeSer(params.get("transSeq"));// 基金公司流水号
        transaction.setMerId(bank.getMerId());// 商户号
        transaction.setBsSer(serNo);// 银联方流水号
        transaction.setBankNo(bank.getBankNo());// 银行编号
        transaction.setAccoNo(params.get("transAcct"));// 交易账户(卡号)
        transaction.setTransType(transType);// 交易类型
        transaction.setAmount(params.get("transAmt"));// 交易金额
        transaction.setRespCode(code);// 响应码: e.g. "50050000"
        transaction.setTransStat("");// 状态码

        String stat = "Y";
        if (!"50050000".equals(code)) {
            stat = "I";
        }

        transaction.setStat(stat);// 内部码: 根据响应码和状态码枚举, e.g. "Y"
        transaction.setWorkDay(params.get("transDate"));// 工作日
        transaction.setSendTime(DateUtil.format14Date(new Date()));// 基金公司交易时间

        return transaction;
    }
}
