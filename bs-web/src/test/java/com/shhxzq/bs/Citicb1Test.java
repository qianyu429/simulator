package com.shhxzq.bs;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Test;

import java.io.BufferedInputStream;

/**
 * Created by houjiagang on 2016/10/27.
 */
public class Citicb1Test {

    @Test
    public void test() throws Exception {
//        String xmlData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
//                "<MESSAGE>" +
//                "<APPLY_NAME>OmUZJvZesP8=</APPLY_NAME>" +
//                "<APPLY_SEX>0</APPLY_SEX>" +
//                "<PARTNER_USER_ID>123456789</PARTNER_USER_ID>" +
//                "<APPLY_ID_NBR>f/E2yp/DUgo12yVGtSitK2S8rPvETgw3</APPLY_ID_NBR>" +
//                "<APP_CARD_NBR>11111</APP_CARD_NBR>" +
//                "<NET_ID>XXTEST</NET_ID>" +
//                "<CARD_PRODUCT_ID>213132</CARD_PRODUCT_ID>" +
//                "<APPLY_PINYIN_NAME>Hu9CsTn1FyM=</APPLY_PINYIN_NAME>" +
//                "<SUBMIT_DATE>20170222113908</SUBMIT_DATE>" +
//                "<SIGNATURE_VAL>1afe7cd9ee09c79ae277d156b0cd1faf</SIGNATURE_VAL>" +
//                "<APPLY_ID_TYPE>1</APPLY_ID_TYPE>" +
//                "<PARTNER_APPLY_ID>222222222</PARTNER_APPLY_ID>" +
//                "<PARTNER_ID>JD00</PARTNER_ID>" +
//                "<MOBILE>8KEdNBZIPBC1PxCJCUz2eg==</MOBILE>" +
//                "</MESSAGE>";
        String xmlData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<MESSAGE>" +
                "<CARD_RMB/>" +
                "<CORP_PROVINCE_NM>上海</CORP_PROVINCE_NM>" +
                "<APPLY_ID_NBR>f/E2yp/DUgo12yVGtSitK2S8rPvETgw3</APPLY_ID_NBR>" +
                "<CORP_NAME>s7tmYaBtmQCLvvUyjxuJuJBzJiUwaO2j</CORP_NAME>" +
                "<SEND_CARD_TYPE>01</SEND_CARD_TYPE>" +
                "<CARD_WB/>" +
                "<IP_ADDR/>" +
                "<EMAIL>Ugj4tZWPo4W6isEgnzLLVVJgQCkUrqdP</EMAIL>" +
                "<NET_ID>XXTEST</NET_ID>" +
                "<CARD_RMB_TYPE/>" +
                "<FLAG_ID/>" +
                "<HOUSE_ADDR>浦东新区女人大街2号</HOUSE_ADDR>" +
                "<APPLY_ID_TYPE>1</APPLY_ID_TYPE>" +
                "<CORP_TEL>+wJZOBQM8C9ClcooyysYfA==</CORP_TEL>" +
                "<CONTACT_ZONE>d8zDwrhGReU=</CONTACT_ZONE>" +
                "<PARTNER_APPLY_ID>222222222</PARTNER_APPLY_ID>" +
                "<PARTNER_ID>JD00</PARTNER_ID>" +
                "<APPLY_NAME>OmUZJvZesP8=</APPLY_NAME>" +
                "<LIMIT_DATE_END/>" +
                "<EDUCATION_LEVEL/>" +
                "<CONTACT_MOBILE>8KEdNBZIPBC1PxCJCUz2eg==</CONTACT_MOBILE>" +
                "<INS_CONTACT_MOBILE>8KEdNBZIPBC1PxCJCUz2eg==</INS_CONTACT_MOBILE>" +
                "<APPLY_SEX>0</APPLY_SEX>" +
                "<INS_CONTACT_REL>1</INS_CONTACT_REL>" +
                "<CORP_ZONE>UxOi0Lg0VKY=</CORP_ZONE>" +
                "<CARD_PRODUCT_ID>213132</CARD_PRODUCT_ID>" +
                "<APPLY_PINYIN_NAME>Hu9CsTn1FyM=</APPLY_PINYIN_NAME>" +
                "<SUBMIT_DATE>20170223143412</SUBMIT_DATE>" +
                "<IS_AUTO_GHHK>0</IS_AUTO_GHHK>" +
                "<HOUSE_CITY_ID>021</HOUSE_CITY_ID>" +
                "<INS_CONTACT_NAME>龙节</INS_CONTACT_NAME>" +
                "<RECOMMEND_NO/>" +
                "<ISSUE_DEPART/>" +
                "<APP_CARD_NBR>11111</APP_CARD_NBR>" +
                "<CORP_POSTAL_CODE>20000</CORP_POSTAL_CODE>" +
                "<JOB_LEVEL/>" +
                "<SIGNATURE_VAL>24427ce78acab23f8d3d97561ceca199</SIGNATURE_VAL>" +
                "<HOUSE_PROVINCE_ID>021</HOUSE_PROVINCE_ID>" +
                "<LIMIT_DATE_START/>" +
                "<CORP_CITY_NM>上海</CORP_CITY_NM>" +
                "<HOUSE_ZONE>UxOi0Lg0VKY=</HOUSE_ZONE>" +
                "<HOUSE_CITY_NM>上海</HOUSE_CITY_NM>" +
                "<HOUSE_POSTAL_CODE>2000</HOUSE_POSTAL_CODE>" +
                "<CONTACT_NAME>龙节</CONTACT_NAME>" +
                "<APP_ZD_ADDR>2</APP_ZD_ADDR>" +
                "<CORP_EXTENSION>d8zDwrhGReU=</CORP_EXTENSION>" +
                "<PARTNER_USER_ID>123456789</PARTNER_USER_ID>" +
                "<CORP_ADDR>浦东新区女人大街2号</CORP_ADDR>" +
                "<CORP_PROVINCE_ID>021</CORP_PROVINCE_ID>" +
                "<CONTACT_TEL>d8zDwrhGReU=</CONTACT_TEL>" +
                "<CARD_WB_TYPE/>" +
                "<RECOMMEND_NO_MGM/>" +
                "<CONTACT_REL>1</CONTACT_REL>" +
                "<HOUSE_PROVINCE_NM>上海</HOUSE_PROVINCE_NM>" +
                "<HOUSE_TEL>1AEIM7hQISaiqFRsZ9Zw/g==</HOUSE_TEL>" +
                "<IS_AUTO_REPAY>0</IS_AUTO_REPAY>" +
                "<CORP_CITY_ID>021</CORP_CITY_ID>" +
                "<NET_POINT_ID/>" +
                "<MOBILE>8KEdNBZIPBC1PxCJCUz2eg==</MOBILE>" +
        "</MESSAGE>";
        String url = "http://localhost:8080/citiccard/cardshopapi/basic.do?func=applyNewCard";
        HttpClient client = new HttpClient();
        PostMethod myPost = new PostMethod(url);
        try {

            //设置请求超时时间
            String responseString = null;

            myPost.setRequestHeader("Content-Type","text/xml");
            myPost.setRequestHeader("charset","utf-8");

            myPost.setRequestBody(xmlData);
            int statusCode = client.executeMethod(myPost);
            if(statusCode == HttpStatus.SC_OK){
                BufferedInputStream bis = new BufferedInputStream(myPost.getResponseBodyAsStream());
                byte[] bytes = new byte[1024];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int count = 0;
                while((count = bis.read(bytes))!= -1){
                    bos.write(bytes, 0, count);
                }
                byte[] strByte = bos.toByteArray();
                responseString = new String(strByte,0,strByte.length,"utf-8");
                bos.close();
                bis.close();
                System.out.println("response string: " + responseString);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }finally {
            myPost.releaseConnection();
        }

    }
}
