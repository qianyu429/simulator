package com.shhxzq.bs;

import com.shhxzq.bs.common.HttpClientSend;
import com.shhxzq.bs.mapping.icbc.CMS;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by houjiagang on 16/7/21.
 */
public class ICBCTest {

    private static final String ENCODING = "GBK";

    private XStream requestParser;
    private XStream responseParser;

    private static HttpClient httpClient;

    static {
        PoolingClientConnectionManager manager = new PoolingClientConnectionManager();
        manager.setDefaultMaxPerRoute(2);
        httpClient = new DefaultHttpClient(manager, new BasicHttpParams());
    }

    /**
     * 测试解约
     * @throws Exception
     */
    @Test
    public void testCancel() throws Exception {

        String sendMsg = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "\n" +
                "<CMS> \n" +
                "    <eb> \n" +
                "        <pub> \n" +
                "            <TransCode>SZFH_DELPERAMT</TransCode>  \n" +
                "            <CIS>400090001271671</CIS>  \n" +
                "            <BankCode>102</BankCode>  \n" +
                "            <ID>bjh.d.4000</ID>  \n" +
                "            <TranDate>20080507</TranDate>  \n" +
                "            <TranTime>151211</TranTime>  \n" +
                "            <fSeqno>20120307162656209</fSeqno> \n" +
                "        </pub>  \n" +
                "        <in> \n" +
                "            <CorpAccNo>4000023029200124946</CorpAccNo>  \n" +
                "            <AccNo>6222024000015697457</AccNo>  \n" +
                "            <AccName>漫发混</AccName>  \n" +
                "            <MobilePhone>13725741125</MobilePhone>  \n" +
                "            <CorpNo>EN100001</CorpNo> \n" +
                "        </in> \n" +
                "    </eb> \n" +
                "</CMS>";
        org.apache.http.client.methods.HttpPost request = new org.apache.http.client.methods.HttpPost("http://localhost:8080/servlet/ICBCCMPAPIReqServlet?userID=" + "400004059363");

        request.setHeader("Accept-Encoding", "identity");
        List<BasicNameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("Version", "0.0.0.1"));
        param.add(new BasicNameValuePair("BankCode", "102"));
        param.add(new BasicNameValuePair("GroupCIS", "400090001271671"));
        param.add(new BasicNameValuePair("ID", "ncrp.y.4000"));
        param.add(new BasicNameValuePair("Cert", ""));
//        param.add(new BasicNameValuePair("reqData", msg));
        param.add(new BasicNameValuePair("reqData", getrevFromBASE64(sendMsg.getBytes())));
        // Post请求
        // 设置参数
        request.setEntity(new UrlEncodedFormEntity(param, "GB2312"));
        HttpResponse response = httpClient.execute(request);
        String resData = readFromStream2(response.getEntity().getContent(), response.getEntity().getContentLength());
        System.out.println(resData);
        resData = resData.substring(resData.indexOf("reqData=") + 8);

        System.out.println(resData);
        Encrypt enc = new Encrypt();
        resData = enc.base64DecodeByInCode(resData.trim(), "gbk");
        System.out.println(resData);
    }


    @Test
    public void testParserXml_request_SZFH_SMSAPPLY() {

        String sendMsg = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "\n" +
                "<CMS> \n" +
                "    <eb> \n" +
                "        <pub> \n" +
                "            <TransCode>SZFH_DELPERAMT</TransCode>  \n" +
                "            <CIS>400090001271671</CIS>  \n" +
                "            <BankCode>102</BankCode>  \n" +
                "            <ID>bjh.d.4000</ID>  \n" +
                "            <TranDate>20080507</TranDate>  \n" +
                "            <TranTime>151211</TranTime>  \n" +
                "            <fSeqno>20120307162656209</fSeqno> \n" +
                "        </pub>  \n" +
                "        <in> \n" +
                "            <CorpAccNo>4000023029200124946</CorpAccNo>  \n" +
                "            <AccNo>6222024000015697457</AccNo>  \n" +
                "            <AccName>漫发混</AccName>  \n" +
                "            <MobilePhone>13725741125</MobilePhone>  \n" +
                "            <CorpNo>EN100001</CorpNo> \n" +
                "        </in> \n" +
                "    </eb> \n" +
                "</CMS>";

        String msg = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n" +
                "<CMS>\n" +
                "    <eb>\n" +
                "        <pub>\n" +
                "            <TransCode>SZFH_SMSAPPLY</TransCode>\n" +
                "            <CIS>400090001604411</CIS>\n" +
                "            <BankCode>102</BankCode>\n" +
                "            <ID>ncrp.y.4000</ID>\n" +
                "            <TranDate>20160722</TranDate>\n" +
                "            <TranTime>165430</TranTime>\n" +
                "            <fSeqno>20160722165430</fSeqno>\n" +
                "        </pub>\n" +
                "        <in>\n" +
                "            <CorpAccNo>4000023029200124946</CorpAccNo>\n" +
                "            <AccNo>6222024000000000105</AccNo>\n" +
                "            <SupType>0</SupType>\n" +
                "            <AccName>瑰拥立</AccName>\n" +
                "            <IdType>0</IdType>\n" +
                "            <IdCode>456529197004241268</IdCode>\n" +
                "            <MobilePhone>13622463581</MobilePhone>\n" +
                "            <CorpNo>BDP800008</CorpNo>\n" +
                "            <PersonNo>CM13622463581</PersonNo>\n" +
                "            <DeadLine>20170101</DeadLine>\n" +
                "            <ReqReserved1></ReqReserved1>\n" +
                "            <ReqReserved2></ReqReserved2>\n" +
                "            <ReqReserved3></ReqReserved3>\n" +
                "            <ReqReserved4></ReqReserved4>\n" +
                "        </in>\n" +
                "    </eb>\n" +
                "</CMS>\n";

        requestParser = getXStreamInstance();
        requestParser.processAnnotations(CMS.class);

        CMS request = (CMS) requestParser.fromXML(sendMsg);

        System.out.println(request.getEBody().getIn().getAccNo());


    }


    @Test
    public void testParserXml_response_SZFH_SMSAPPLY() {
        String msg = "<?xml  version=\"1.0\" encoding=\"GBK\" ?>\n" +
                "<CMS>\n" +
                "    <eb>\n" +
                "        <pub>\n" +
                "            <TransCode>SZFH_SMSAPPLY</TransCode>\n" +
                "            <CIS>400090001604411</CIS>\n" +
                "            <BankCode>102</BankCode>\n" +
                "            <ID>ncrp.y.4000</ID>\n" +
                "            <TranDate>20160722</TranDate>\n" +
                "            <TranTime>170051</TranTime>\n" +
                "            <fSeqno>20160722170051</fSeqno>\n" +
                "            <RetCode>00000</RetCode>\n" +
                "            <RetMsg>交易成功.</RetMsg>\n" +
                "        </pub>\n" +
                "        <out>\n" +
                "            <CorpAccNo>4000023029200124946</CorpAccNo>\n" +
                "            <AccNo>6222024000000000105</AccNo>\n" +
                "            <SupType>0</SupType>\n" +
                "            <AccName>瑰拥立</AccName>\n" +
                "            <IdType>0</IdType>\n" +
                "            <IdCode>456529197004241268</IdCode>\n" +
                "            <MobilePhone>13622463581</MobilePhone>\n" +
                "            <CorpNo>BDP800008</CorpNo>\n" +
                "            <PersonNo>CM13622463581</PersonNo>\n" +
                "            <DeadLine>20170101</DeadLine>\n" +
                "            <ReqReserved1></ReqReserved1>\n" +
                "            <ReqReserved2></ReqReserved2>\n" +
                "            <ReqReserved3></ReqReserved3>\n" +
                "            <ReqReserved4></ReqReserved4>\n" +
                "            <BankSeq>FHRZ2016080310010825</BankSeq>\n" +
                "            <AccType>3</AccType>\n" +
                "            <AreaCode>4000</AreaCode>\n" +
                "            <SMSSendFlag></SMSSendFlag>\n" +
                "            <SMSSendNo>10010825</SMSSendNo>\n" +
                "            <RepReserved1></RepReserved1>\n" +
                "            <RepReserved2>1</RepReserved2>\n" +
                "            <RepReserved3>20150723</RepReserved3>\n" +
                "            <RepReserved4></RepReserved4>\n" +
                "        </out>\n" +
                "    </eb>\n" +
                "</CMS>\n";

        responseParser = getXStreamInstance();
        responseParser.processAnnotations(CMS.class);

        CMS request = (CMS) responseParser.fromXML(msg);

        System.out.println(request.getEBody().getOut().getAreaCode());


    }


    @Test
    public void testPostXml() throws Exception {
        String msg = "<?xml version=\"1.0\" encoding=\"GB2312\" ?>\n" +
                "<CMS>\n" +
                "    <eb>\n" +
                "        <pub>\n" +
                "            <TransCode>QPAYPERTM</TransCode>\n" +
                "            <CIS>400090001604411</CIS>\n" +
                "            <BankCode>102</BankCode>\n" +
                "            <ID>ncrp.y.4000</ID>\n" +
                "            <TranDate>20160830</TranDate>\n" +
                "            <TranTime>181458</TranTime>\n" +
                "            <fSeqno>null</fSeqno>\n" +
                "        </pub>\n" +
                "        <in>\n" +
                "            <BeginTime>20160722000000</BeginTime>\n" +
                "            <EndTime>20160801235959</EndTime>\n" +
                "            <ResultType></ResultType>\n" +
                "            <NextTag>11</NextTag>\n" +
                "            <ReqReserved1></ReqReserved1>\n" +
                "            <ReqReserved2></ReqReserved2>\n" +
                "        </in>\n" +
                "    </eb>\n" +
                "</CMS>\n";


        org.apache.http.client.methods.HttpPost request = new org.apache.http.client.methods.HttpPost("http://10.199.101.212:7080/servlet/ICBCCMPAPIReqServlet?userID=" + "400004059363");

        request.setHeader("Accept-Encoding", "identity");
        List<BasicNameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("Version", "0.0.0.1"));
        param.add(new BasicNameValuePair("BankCode", "102"));
        param.add(new BasicNameValuePair("GroupCIS", "400090001271671"));
        param.add(new BasicNameValuePair("ID", "ncrp.y.4000"));
        param.add(new BasicNameValuePair("Cert", ""));
//        param.add(new BasicNameValuePair("reqData", msg));
        param.add(new BasicNameValuePair("reqData", getrevFromBASE64(msg.getBytes())));


        // Post请求
        // 设置参数
        request.setEntity(new UrlEncodedFormEntity(param, "GB2312"));

        HttpResponse response = httpClient.execute(request);
        String resData = readFromStream2(response.getEntity().getContent(), response.getEntity().getContentLength());
        System.out.println(resData);
        resData = resData.substring(resData.indexOf("reqData=") + 8);

        System.out.println(resData);
        Encrypt enc = new Encrypt();
        resData = enc.base64DecodeByInCode(resData.trim(), "gbk");


        System.out.println(resData);
    }


    private String readFromStream2(InputStream is, long l) throws IOException {
        int i = -1;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[(int) l];
        while ((i = is.read(b)) != -1) {
            baos.write(b, 0, i);
        }
        baos.flush();
        String result = baos.toString("GB2312");
        baos.close();
        return result;
    }

    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }

    public String getrevFromBASE64(byte[] s) {
        if (s == null)
            return null;
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            return encoder.encode(s);
        } catch (Exception e) {
            return null;
        }
    }
}
