package com.shhxzq.bs;

import com.shhxzq.bs.util.EctUtil;
import com.shhxzq.bs.util.StreamUtil;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by houjiagang on 16/7/19.
 */
public class CmbcTest {


    @Test
    public void test() throws Exception {

        String msg = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<Cartoon>\n" +
                "<Message id=\"2016071200103382\">\n" +
                "<CSVReq id=\"CSVReq\">\n" +
                "<version>2.0.0</version>\n" +
                "<instId>23015</instId>\n" +
                "<certId>23015</certId>\n" +
                "<date>20160712 09:48:55</date>\n" +
                "<accountName>夏果</accountName>\n" +
                "<bankCardNo>6226193300215413</bankCardNo>\n" +
                "<bankCardType>D</bankCardType>\n" +
                "<certificateType>1</certificateType>\n" +
                "<certificateNo>35072519720818699X</certificateNo>\n" +
                "<mobilePhone>18611372221</mobilePhone>\n" +
                "<mobileCheck>1</mobileCheck>\n" +
                "<linkAcctNo>6226193300215413</linkAcctNo>\n" +
                "<msgValidationCode></msgValidationCode>\n" +
                "<channelSeqNo></channelSeqNo>\n" +
                "</CSVReq>\n" +
                "</Message>\n" +
                "</Cartoon>\n";


        HttpURLConnection http = null;
        byte[] bytes = null;
        InputStream in = null;
        PrintWriter out = null;


        URL url = new URL("http://127.0.0.1:8080/epay/connect.do");
        http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoInput(true);
        http.setDoOutput(true);
        http.setRequestProperty("Content-Length", Integer.toString(500));
        http.setRequestProperty("Content-Type", "application/xml; charset=GBK");
              /*写入数据*/
        out = new PrintWriter(new OutputStreamWriter(http.getOutputStream(), "GBK"));
        out.write(msg);
        out.flush();
        out.close();
        int retCode = http.getResponseCode();

        if (retCode != 200) {
            throw new Exception("http发送返回码未知retCode[" + retCode + "]");
        }

        in = http.getInputStream();
        bytes = StreamUtil.readBytes(in);
        String s = new String(bytes, "UTF-8");
        System.out.print(s);


    }
}
