package com.shhxzq.bs;

import com.shhxzq.bs.util.Spdb6Util;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;

/**
 * 测试同行受托支付交易
 * Created by zuodongxiang on 2017/5/8.
 */
public class Spdb6THTest {

    @Test
    public void test() throws Exception {
        String serverIp = "127.0.0.1";
//        String serverIp = "10.199.101.212";
        int serverPort = 4000;
        InetAddress inetAddress = InetAddress.getByName(serverIp);
        InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, serverPort);
        Socket socket = new Socket();
        socket.connect(socketAddress);

        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();

       String data0 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
               "<STP>\n" +
               "<HEAD>\n" +
               "  <TRNCODE>GATHER</TRNCODE>\n" +
               "  <VERSION>1.0</VERSION>\n" +
               "  <DSFSN>2017050300001085</DSFSN>\n" +
               "  <DSFDATE>20170503</DSFDATE>\n" +
               "  <DSFTIME>150825</DSFTIME>\n" +
               "  <DSFNO>9900000351</DSFNO>\n" +
               "  <DSFNAME>上海华信证券</DSFNAME>\n" +
               "</HEAD>\n" +
               "<BODY>\n" +
               "  <ACCCODE>6217934100005780</ACCCODE>\n" +
               "  <ACCNAME>浦发1285803937</ACCNAME>\n" +
               "  <AMOUNT>31</AMOUNT>\n" +
               "  <CCYID>CNY</CCYID>\n" +
               "  <ACCTYPE>0</ACCTYPE>\n" +
               "  <IDTYPE>7</IDTYPE>\n" +
               "  <IDCARD>1285803937</IDCARD>\n" +
               "  <BIZTYPE>pay</BIZTYPE>\n" +
               "  <MERNO>9900000351</MERNO>\n" +
               "  <MERNAME>上海华信证券</MERNAME>\n" +
               "  <REMARK>华信现金宝</REMARK>\n" +
               "  <RESERVED1></RESERVED1>\n" +
               "  <RESERVED2></RESERVED2>\n" +
               "</BODY>\n" +
               "</STP>";

        String data00 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<STP>\n" +
                "<HEAD>\n" +
                "  <TRNCODE>QRYRST</TRNCODE>\n" +
                "  <VERSION>1.0</VERSION>\n" +
                "  <DSFSN>2017050300001085</DSFSN>\n" +
                "  <DSFDATE>20170503</DSFDATE>\n" +
                "  <DSFTIME>151138</DSFTIME>\n" +
                "  <DSFNO>9900000351</DSFNO>\n" +
                "  <DSFNAME>上海华信证券</DSFNAME>\n" +
                "</HEAD>\n" +
                "<BODY>\n" +
                "  <DSFSN>2017050300001085</DSFSN>\n" +
                "</BODY>\n" +
                "</STP>";
        String data1 = createPrefix(getWordCountCode(data00, "GBK")) + data00;
        String data2 = "1.014942410301494241030000"+data1;
        String data3 = createPrefix(getWordCountCode(data2, "GBK")) + data2;
        outputStream.write(data3.getBytes("GBK"));
        outputStream.flush();

        byte[] b = new byte[2000];
        inputStream.read(b, 0, 2000);
        System.out.println(new String(b, "GBK"));

    }


    /**
     * 根据字报文生成前缀
     *
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
        System.out.println("length---" + str.getBytes(code).length);
        return str.getBytes(code).length;
    }

}
