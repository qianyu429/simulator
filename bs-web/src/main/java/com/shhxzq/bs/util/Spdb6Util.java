package com.shhxzq.bs.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zuodongxiang on 17/4/21.
 */
public class Spdb6Util {

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
        if (element.attributeCount() > 0) {
            map.put(element.getName(), element.attribute(0).getValue());
        } else {
            map.put(element.getName(), element.getTextTrim());
        }

        for (Element e : list) {
            buildMap(e, map);
        }
    }

    /**
     * 整数转字节
     *
     * @param tmp
     * @param bytes
     * @return
     */
    public static byte[] int2byte(int tmp, int bytes) {
        StringBuffer tmpStr = new StringBuffer(tmp + "");
        int length = tmpStr.length();
        for (int i = 0; i < bytes - length; i++) {
            tmpStr.insert(0, '0');
        }

        return tmpStr.toString().getBytes();
    }

}
