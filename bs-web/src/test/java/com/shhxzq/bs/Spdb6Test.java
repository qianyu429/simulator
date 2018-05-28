package com.shhxzq.bs;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.shhxzq.bs.util.Spdb6Util;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;

/**
 * Created by zuodongxiang on 2017/04/21.
 */
public class Spdb6Test {

    @Test
    public void test() throws Exception {
        String serverIp = "127.0.0.1";
//        String serverIp = "10.199.101.212";
        int serverPort = 5100;
        InetAddress inetAddress = InetAddress.getByName(serverIp);
        InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, serverPort);
        Socket socket = new Socket();
        socket.connect(socketAddress);

        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();

        //鉴权
        String data0 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<STPContext><Header><ActionCode>0</ActionCode><ActivityCode>AcntCheck</ActivityCode><ReqChannelNo></ReqChannelNo><ReqMerchantNo>9900000025</ReqMerchantNo><ReqDateTime>2017042500000719</ReqDateTime><ReqSysID>132434423</ReqSysID></Header><Body><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<INFO><ReqDate>20160422</ReqDate><ReqTransID>2016010700000031701</ReqTransID><IsVali></IsVali><Pawd></Pawd><AcntNo>6226620607792207</AcntNo><AcntType>1</AcntType><AcntName>刘敏</AcntName><IDCardNo>231002198903302089</IDCardNo><IDCardType>1</IDCardType><BankTelPhone></BankTelPhone><Remark1>备注1</Remark1><Remark2>备注2</Remark2><Remark3>备注3</Remark3></INFO>]]></Body></STPContext>";
        //代扣
        String data1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<STPContext><Header><ActionCode>0</ActionCode><ActivityCode>SinCut</ActivityCode><ReqChannelNo></ReqChannelNo><ReqMerchantNo>9900000025</ReqMerchantNo><ReqDateTime>2016041161304125042</ReqDateTime><ReqSysID>132434423</ReqSysID></Header><Body><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<INFO><OperationCode>noyae</OperationCode><OperationName>诺亚金通</OperationName><OperationXName>二级商户名</OperationXName><ReqDate>20160422</ReqDate><ReqTransID>2017042500000719</ReqTransID><DealChannelNo></DealChannelNo><AcntNo>6227001217450018751</AcntNo><AcntType>1</AcntType><AcntName>王五</AcntName><TransAmount>100.01</TransAmount><IDCardNo>340102198212062039</IDCardNo><IDCardType>1</IDCardType><BankNo>105100000017</BankNo><BankName>中国建设银行</BankName><BankProv></BankProv><BankCity></BankCity><BankTelPhone></BankTelPhone><LinkTelPhone></LinkTelPhone><Summary></Summary><Remark1>备注1</Remark1><Remark2>备注2</Remark2><Remark3>备注3</Remark3></INFO>]]></Body></STPContext>";


        //代扣查询
        String data2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<STPContext><Header><ActionCode>0</ActionCode><ActivityCode>SinCutQuery</ActivityCode><ReqChannelNo></ReqChannelNo><ReqMerchantNo>9900000025</ReqMerchantNo><ReqDateTime>2016041191004412002</ReqDateTime><ReqSysID>132434423</ReqSysID></Header><Body><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<INFO><OrgReqDate>20160420</OrgReqDate><OrgReqTransID>2017010700000031701</OrgReqTransID></INFO>]]></Body></STPContext>";

        //代付
        String data3 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<STPContext><Header><ActionCode>0</ActionCode><ActivityCode>SinPay</ActivityCode><ReqChannelNo></ReqChannelNo><ReqMerchantNo>9900000025</ReqMerchantNo><ReqDateTime>2016041161404344057</ReqDateTime><ReqSysID>132434423</ReqSysID></Header><Body><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<INFO><OperationCode>noyae</OperationCode><OperationName>诺亚金通</OperationName><OperationXName>二级商户名</OperationXName><ReqDate>20160422</ReqDate><ReqTransID>2016010700000031703</ReqTransID><DealChannelNo></DealChannelNo><AcntNo>6227001217450018751</AcntNo><AcntType>1</AcntType><AcntName>王五</AcntName><TransAmount>100.01</TransAmount><IDCardNo>340102198212062039</IDCardNo><IDCardType>1</IDCardType><BankNo>105100000017</BankNo><BankName>中国建设银行</BankName><BankProv></BankProv><BankCity></BankCity><BankTelPhone></BankTelPhone><LinkTelPhone></LinkTelPhone><Summary></Summary><Remark1>备注1</Remark1><Remark2>备注2</Remark2><Remark3>备注3</Remark3></INFO>]]></Body></STPContext>";
        //代付查询
         String data4 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                 "<STPContext><Header><ActionCode>0</ActionCode><ActivityCode>SinPayQuery</ActivityCode><ReqChannelNo></ReqChannelNo><ReqMerchantNo>9900000025</ReqMerchantNo><ReqDateTime>2016041391004412002</ReqDateTime><ReqSysID>132434423</ReqSysID></Header><Body><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                 "<INFO><OrgReqDate>20160420</OrgReqDate><OrgReqTransID>1461137187849</OrgReqTransID></INFO>]]></Body></STPContext>";
        //签到
        String data5 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<STPContext><Header><ActionCode>0</ActionCode><ActivityCode>DaliySignKey</ActivityCode><ReqChannelNo></ReqChannelNo><ReqMerchantNo>9900000025</ReqMerchantNo><ReqDateTime>2016051301905314039</ReqDateTime><ReqSysID>132434423</ReqSysID></Header><Body><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "]]></Body></STPContext>";

        String data6 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<STPContext>" +
                "<Header>" +
                "  <ActionCode>0</ActionCode>" +
                "  <ActivityCode>SinCut</ActivityCode>" +
                "  <ReqChannelNo></ReqChannelNo>" +
                "  <ReqMerchantNo>9900000353</ReqMerchantNo>" +
                "  <ReqDateTime>20170425114523000</ReqDateTime>" +
                "  <ReqSysID>2017042500000721</ReqSysID>" +
                "</Header>" +
                "<Body>" +
                "<![CDATA[<INFO>" +
                "  <ReqDate>20170425</ReqDate>" +
                "  <ReqTransID>2017042500000721</ReqTransID>" +
                "  <DealChannelNo>98009003</DealChannelNo>" +
                "  <AcntNo>6217001000000000018</AcntNo>" +
                "  <AcntType>1</AcntType>" +
                "  <AcntName>张三</AcntName>" +
                "  <TransAmount>0.31</TransAmount>" +
                "  <IDCardNo>340102198212062039</IDCardNo>" +
                "  <IDCardType>1</IDCardType>" +
                "  <BankNo>105100000017</BankNo>" +
                "  <BankName>中国建设银行</BankName>" +
                "  <Remark1></Remark1>" +
                "  <Remark2></Remark2>" +
                "  <Remark3></Remark3>" +
                "</INFO>]]>" +
                "</Body>" +
                "</STPContext>";

        String data7 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<STPContext>\n" +
                "<Header>\n" +
                "  <ActionCode>0</ActionCode>\n" +
                "  <ActivityCode>SinCut</ActivityCode>\n" +
                "  <ReqChannelNo></ReqChannelNo>\n" +
                "  <ReqMerchantNo>9900000353</ReqMerchantNo>\n" +
                "  <ReqDateTime>20170425134155000</ReqDateTime>\n" +
                "  <ReqSysID>2017042500000724</ReqSysID>\n" +
                "</Header>\n" +
                "<Body>\n" +
                "<![CDATA[<INFO>\n" +
                "  <ReqDate>20170425</ReqDate>\n" +
                "  <ReqTransID>2017042500000726</ReqTransID>\n" +
                "  <DealChannelNo>98009003</DealChannelNo>\n" +
                "  <AcntNo>6217001000000000018</AcntNo>\n" +
                "  <AcntType>1</AcntType>\n" +
                "  <AcntName>张三</AcntName>\n" +
                "  <TransAmount>0.31</TransAmount>\n" +
                "  <IDCardNo>340102198212062039</IDCardNo>\n" +
                "  <IDCardType>1</IDCardType>\n" +
                "  <BankNo>105100000017</BankNo>\n" +
                "  <BankName>中国建设银行</BankName>\n" +
                "  <Remark1></Remark1>\n" +
                "  <Remark2></Remark2>\n" +
                "  <Remark3></Remark3>\n" +
                "</INFO>]]>\n" +
                "</Body>\n" +
                "</STPContext>";
        String data = createPrefix(getWordCountCode(data7, "GBK")) + data7;
        outputStream.write(data.getBytes("GBK"));
        outputStream.flush();

        byte[] b = new byte[2000];
        inputStream.read(b, 0, 2000);
        System.out.println(new String(b, "GBK"));

    }


    /**
     * 根据字报文生成前缀
     * @param length
     * @return
     */
    public String createPrefix(int length) {
        int rightLength = String.valueOf(length).length();
        int extraLength = 8 - rightLength;
        String left = "";
        while (extraLength-- > 0) {
            left += "0";
        }

        return left + String.valueOf(length);
    }


    /*按特定的编码格式获取长度*/
    public int getWordCountCode(String str, String code) throws UnsupportedEncodingException {
        System.out.println("length---"+str.getBytes(code).length);
        return str.getBytes(code).length;
    }

}
