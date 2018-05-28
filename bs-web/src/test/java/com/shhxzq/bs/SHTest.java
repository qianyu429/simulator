package com.shhxzq.bs;

import com.shhxzq.bs.common.HttpClientSend;
import com.shhxzq.bs.util.StreamUtil;
import com.thoughtworks.xstream.XStream;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by houjiagang on 16/9/5.
 */
public class SHTest {

    private static final String ENCODING = "utf-8";

    private XStream messageParser;

    @Test
    public void testSH() throws Exception {

        String sendMsg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<root><transaction_id>675201611170000000001677</transaction_id><partner_fund_no>EA000002000103</partner_fund_no><buyer_cert_no>131124199806072431</buyer_cert_no><input_charset>UTF-8</input_charset><sign>89EBDEC3E243BE08D1C5379AA283180A</sign><buyer_name>邵霸</buyer_name><service_version>1.0</service_version><acc_time>20161117170051</acc_time><partner>BOSERAFUND</partner><ecpay_code>HXZQDS</ecpay_code><buyer_cert_type>0</buyer_cert_type><mobile_number>19999999999</mobile_number><sign_type>MD5</sign_type></root>";
        String url = "http://10.199.101.212:7080/kd_webtradeCombine/openacctIndividual_boshi";
        HttpClientSend http = new HttpClientSend();
        String resultxml = http.post(sendMsg, url);

        System.out.println(resultxml);
    }


    @Test
    public void testSHYQ() throws Exception {

        String sendMsg = "dse_pap=&reqData="+
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<BOSEBankData><opReq><serialNo>2016111500110282</serialNo><reqTime>20161115</reqTime><ReqParam><ACNO>31600700009000356</ACNO><OPAC>300113085110013</OPAC><TRAM>0.03</TRAM><NAME>测试客户3001130851</NAME><USAG>证券结算资金</USAG><REMK>对私转账</REMK><COSE></COSE></ReqParam></opReq></BOSEBankData>";
        String url = "http://localhost:8080/CM/APIReqServlet";
        HttpClientSend http = new HttpClientSend();
        String resultxml = http.post(sendMsg, url);

        System.out.println(resultxml);
    }




    @Test
    public void test() throws Exception {

        String sendMsg = "dse_pap=&reqData="+
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<BOSEBankData><opReq><serialNo>2016111500110282</serialNo><reqTime>20161115</reqTime><ReqParam><ACNO>31600700009000356</ACNO><OPAC>300113085110013</OPAC><TRAM>0.03</TRAM><NAME>测试客户3001130851</NAME><USAG>证券结算资金</USAG><REMK>对私转账</REMK><COSE></COSE></ReqParam></opReq></BOSEBankData>";

        HttpURLConnection http = null;
        byte[] bytes = null;
        InputStream in = null;
        OutputStream out = null;

        URL url = new URL("http://localhost:8080/CM/APIReqServlet");
        http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoInput(true);
        http.setDoOutput(true);
        http.setRequestProperty("Content-Length", Integer.toString(500));
        http.setRequestProperty("Content-Type", "application/xml; charset=GBK");
        http.connect();
              /*写入数据*/
        out = http.getOutputStream();
        out.write(sendMsg.getBytes("GBK"));
        out.flush();



        int retCode = http.getResponseCode();

        if (retCode != 200) {
            throw new Exception("http发送返回码未知retCode[" + retCode + "]");
        }

        in = http.getInputStream();
        bytes = StreamUtil.readBytes(in);
        String s = new String(bytes, "GBK");
        System.out.print(s);


    }

    @Test
    public void tet(){

        String s = "dse_pap=&reqData=<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<BOSEBankData><opReq><serialNo>2016111500110282</serialNo><reqTime>20161115</reqTime><ReqParam><ACNO>31600700009000356</ACNO><OPAC>300113085110013</OPAC><TRAM>0.03</TRAM><NAME>���Կͻ�3001130851</NAME><USAG>֤ȯ�����ʽ�</USAG><REMK>��˽ת��</REMK><COSE></COSE></ReqParam></opReq></BOSEBankData>";

    }


    public static void main(String[] args){
        System.out.println(System.getProperty("user.home") );

    }

}
