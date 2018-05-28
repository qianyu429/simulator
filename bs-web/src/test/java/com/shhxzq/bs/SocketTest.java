package com.shhxzq.bs;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by houjiagang on 16/7/9.
 */
public class SocketTest {
    public static void main(String[] args) throws IOException {
//        String serverIp = "127.0.0.1";
        String serverIp = "10.199.101.212";
        int serverPort = 7081;

        InetAddress inetAddress = InetAddress.getByName(serverIp);
        InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, serverPort);
        Socket socket = new Socket();
        socket.connect(socketAddress);

        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();

        //鉴权
//        String datas = "CMBA0255BKQD201610271128521381610270000515                                                          CMBCHINA  7B66ABCC613E32172016102700109975    ************1595    0574                                                                                                                                                                           D                                       ";



        //申购
//        String datas ="00000553<?xml version=\"1.0\" encoding=\"GBK\" ?><NYYH><HEAD><SEQ_NO>2016071200101203</SEQ_NO><TRANS_TIME>20160525195233</TRANS_TIME><TRANS_CODE>consume</TRANS_CODE><CHANNEL>00008</CHANNEL><BANK_SEQ_NO></BANK_SEQ_NO><ORDER_NO></ORDER_NO></HEAD><BODY><SCENE_CODE>00</SCENE_CODE><CERT_TYPE>110</CERT_TYPE><CERT_NO>440802195509200840</CERT_NO><CARD_TYPE>D</CARD_TYPE><CARD_NO>6224480004488178</CARD_NO><CCY>CNY</CCY><TX_AMT>99.01</TX_AMT><CUST_NAME></CUST_NAME><TELEPHONE></TELEPHONE><EXP_DATE/><CVV2/><PRODUCT_NUM/><RESERVE1/><RESERVE2/></BODY></NYYH>F97A32A41DEB028A";

//        赎回
        String datas = "00000484<?xml version=\"1.0\" encoding=\"GBK\" ?><NYYH><HEAD><SEQ_NO>2016052500101201</SEQ_NO><TRANS_TIME>20160525201901</TRANS_TIME><TRANS_CODE>spayment</TRANS_CODE><CHANNEL>00008</CHANNEL><BANK_SEQ_NO></BANK_SEQ_NO><ORDER_NO></ORDER_NO></HEAD><BODY><SCENE_CODE>02</SCENE_CODE><BANK_FLAG>0</BANK_FLAG><BANK_NO></BANK_NO><ACCT_NO>6224480004488178</ACCT_NO><ACCT_NAME>TESTA0000336519</ACCT_NAME><CCY>CNY</CCY><TX_AMT>1.00</TX_AMT><REMARK/><MSG/><RESERVE1/><RESERVE2/></BODY></NYYH>0ABF3581A1C4E5CB";
        //query
//        String datas = "00000426<?xml version=\"1.0\" encoding=\"GBK\" ?><NYYH><HEAD><SEQ_NO>2016071200101203</SEQ_NO><TRANS_TIME>20160525204812</TRANS_TIME><TRANS_CODE>orderquery</TRANS_CODE><CHANNEL>00008</CHANNEL><BANK_SEQ_NO></BANK_SEQ_NO><ORDER_NO></ORDER_NO></HEAD><BODY><TRANS_TYPE>1</TRANS_TYPE><OLD_SEQ_NO>2016052500101196</OLD_SEQ_NO><OLD_BANK_SEQ_NO></OLD_BANK_SEQ_NO><ORDER_DATE>20160525</ORDER_DATE><RESERVE1/><RESERVE2/></BODY></NYYH>11CA7256E5EE571B";

        outputStream.write(datas.getBytes("UTF-8"));
        outputStream.flush();

//        receive
        StringBuffer receiveData = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream), "GBK"));
        while (true) {
            String readed = reader.readLine();
            if (readed != null) {
                receiveData.append(new String(readed.getBytes(), "UTF-8"));
            } else {
                break;
            }
        }
        System.out.println(receiveData.toString().trim());


//        byte[] b = new byte[119+126];
//        inputStream.read(b, 0, 119+126);
//        System.out.println(new String(b, "UTF-8"));




    }

}