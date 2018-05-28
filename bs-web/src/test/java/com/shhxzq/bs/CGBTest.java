package com.shhxzq.bs;

import com.shhxzq.bs.util.StreamUtil;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wanglili on 17/5/4.
 */
public class CGBTest {
    @Test
    public void testCSMSReq() throws Exception {
        String sendMsg = "<SoEv>\n" +
                " <Message id=\"2017042100000684\">\n" +
                "        <CSMSReq id=\"CSMSReq\">\n" +
                "            <version>1.0.1</version>\n" +
                "            <instId>195003403000645</instId>\n" +
                "            <certId>0001</certId>\n" +
                "            <date>20170421 16:12:41</date>\n" +
                "            <bankCardNo>6225682121010295166</bankCardNo>\n" +
                "            <bankCardType>0</bankCardType>\n" +
                "            <phoneVerification>503750</phoneVerification>\n" +
                "            <mobilePhone>13800135000</mobilePhone>\n" +
                "            <extension></extension>\n" +
                "        </CSMSReq>\n" +
                "    </Message>\n" +
                "</SoEv>\n";

        HttpURLConnection http = null;
        byte[] bytes = null;
        InputStream in = null;
        PrintWriter out = null;

        URL url = new URL("http://localhost:8080/spayment/servlet/gfbank.portal.GFPortalTrade");
        http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoInput(true);
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/xml; charset=UTF-8");
              /*写入数据*/
        out = new PrintWriter(new OutputStreamWriter(http.getOutputStream(), "UTF-8"));
        out.write(sendMsg);
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

    @Test
    public void testCSVReq() throws Exception {
        //CSVReq
//        String sendMsg = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                "<SoEv>\n" +
//                " <Message id=\"2017042100000685\">\n" +
//                "        <CSVReq id=\"CSVReq\">\n" +
//                "            <version>1.0.1</version>\n" +
//                "            <instId>195003403000645</instId>\n" +
//                "            <certId>0001</certId>\n" +
//                "            <TrxId>2017042100000685</TrxId>\n" +
//                "            <TrxDtTm>2017-04-21 16:18:46</TrxDtTm>\n" +
//                "            <CstmrNm>司寇裆处</CstmrNm>\n" +
//                "            <BkAcctTp>0</BkAcctTp>\n" +
//                "            <BkAcctNo>6225682121010295166</BkAcctNo>\n" +
//                "            <CardCvn2></CardCvn2>\n" +
//                "            <CardExprDt></CardExprDt>\n" +
//                "            <IDTp>01</IDTp>\n" +
//                "            <IDNo>360312198606072026</IDNo>\n" +
//                "            <MobNo>13800135000</MobNo>\n" +
//                "            <extension></extension>\n" +
//                "        </CSVReq>\n" +
//                "    </Message>\n" +
//                "</SoEv>\n";

        //CPReq
//        String sendMsg = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                "<SoEv>\n" +
//                " <Message id=\"2017042100000687\">\n" +
//                "        <CPReq id=\"CPReq\">\n" +
//                "            <version>1.0.1</version>\n" +
//                "            <instId>195003403000645</instId>\n" +
//                "            <certId>0001</certId>\n" +
//                "            <TrxId>2017042100000687</TrxId>\n" +
//                "            <TrxDtTm>2017-04-21 16:22:49</TrxDtTm>\n" +
//                "            <TrxAmt>10000</TrxAmt>\n" +
//                "            <charge></charge>\n" +
//                "            <TrxCcyCd>156</TrxCcyCd>\n" +
//                "            <mccCode>6211</mccCode>\n" +
//                "            <TrxTp>1000</TrxTp>\n" +
//                "            <SgnNo>101E1D7DD2114076576DE4432776ED8F61</SgnNo>\n" +
//                "            <PyerNm>司寇裆处</PyerNm>\n" +
//                "            <mobile></mobile>\n" +
//                "            <PyerAcctId>6225682121010295166</PyerAcctId>\n" +
//                "            <PyerAcctTp>0</PyerAcctTp>\n" +
//                "            <PyerAcctIssrId>C1030644021075</PyerAcctIssrId>\n" +
//                "            <PyerTrxTrmNo></PyerTrxTrmNo>\n" +
//                "            <CardCvn2></CardCvn2>\n" +
//                "            <CardExprDt></CardExprDt>\n" +
//                "            <PyeeNm>test</PyeeNm>\n" +
//                "            <PyeeAcctId>123456789</PyeeAcctId>\n" +
//                "            <PyeeAcctTp>0</PyeeAcctTp>\n" +
//                "            <PyeeAcctIssrId>C1030644021075</PyeeAcctIssrId>\n" +
//                "            <PyeeCtryNo></PyeeCtryNo>\n" +
//                "            <PyeeAreaNo></PyeeAreaNo>\n" +
//                "            <AcqrrId></AcqrrId>\n" +
//                "            <PyeeTrxTrmTp></PyeeTrxTrmTp>\n" +
//                "            <PyeeTrxTrmNo></PyeeTrxTrmNo>\n" +
//                "            <MrchntNm>test</MrchntNm>\n" +
//                "            <MrchntNo>195003403000645</MrchntNo>\n" +
//                "            <MrchntTp>1</MrchntTp>\n" +
//                "            <MrchntCertTp>11</MrchntCertTp>\n" +
//                "            <MrchntCertId>75972704-5</MrchntCertId>\n" +
//                "            <MrchntCtgyCd>6211</MrchntCtgyCd>\n" +
//                "            <OrdrId>2017042100000686</OrdrId>\n" +
//                "            <OrdrDesc>支付</OrdrDesc>\n" +
//                "            <InstgId></InstgId>\n" +
//                "            <InstgAcctId></InstgAcctId>\n" +
//                "            <InstgAcctIssrId></InstgAcctIssrId>\n" +
//                "            <productType>S</productType>\n" +
//                "            <sendWay>0</sendWay>\n" +
//                "            <productName>华信现金宝货币市场基金</productName>\n" +
//                "            <MrchntPltfrmNm>test</MrchntPltfrmNm>\n" +
//                "            <netAddress>test</netAddress>\n" +
//                "            <comment></comment>\n" +
//                "            <clientIP></clientIP>\n" +
//                "            <clientMAC></clientMAC>\n" +
//                "            <PyerTrxTrmTp>01</PyerTrxTrmTp>\n" +
//                "        </CPReq>\n" +
//                "    </Message>\n" +
//                "</SoEv>\n";

        String sendMsg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<SoEv><Message id=\"1493968192496\">\n" +
                "    <STQReq id=\"STQReq\">\n" +
                "        <version>1.0.1</version>\n" +
                "        <instId>195003403000645</instId>\n" +
                "        <certId>0001</certId>\n" +
                "        <TrxId>1493968192496</TrxId>\n" +
                "        <TrxDtTm>2017-06-20 13:55:36</TrxDtTm>\n" +
                "        <OriTrxId>2017050500001205</OriTrxId>\n" +
                "        <type>1</type>\n" +
                "        <extension></extension>\n" +
                "    </STQReq>\n" +
                "</Message>\n" +
                "</SoEv>";
        String url = "http://localhost:8080/spayment/servlet/gfbank.portal.GFMerTrade";

        HttpURLConnection http = null;
        byte[] bytes = null;
        InputStream in = null;
        PrintWriter out = null;

        URL url1 = new URL(url);
        http = (HttpURLConnection) url1.openConnection();
        http.setRequestMethod("POST");
        http.setDoInput(true);
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/xml; charset=UTF-8");
        /*写入数据*/
        out = new PrintWriter(new OutputStreamWriter(http.getOutputStream(), "UTF-8"));
        out.write(sendMsg);
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
