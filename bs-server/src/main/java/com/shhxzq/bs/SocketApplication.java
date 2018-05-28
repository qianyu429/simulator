package com.shhxzq.bs;

import com.shhxzq.bs.processer.CCBProcesser;
import com.shhxzq.bs.processer.CMBProcesser;
import com.shhxzq.bs.processer.GDNYProcesser;
import com.shhxzq.bs.processer.PABProcesser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by houjiagang on 16/7/11.
 */
@Log4j2
@SpringBootApplication
public class SocketApplication implements CommandLineRunner{


    private final static String GDNY_KEY = "<TRANS_CODE>";
    private final static String CCB_KEY = "<TX_CODE>";
    private final static String CMB_KEY = "CMBA";

    private final static String OPEN_FUND = "400101";
    private final static String REDEEM = "4004";
    private final static String REDEEM_QUERY = "4005";
    private final static String PAYMENT = "4047";
    private final static String PAY_QUERY = "4048";


    @Autowired
    private GDNYProcesser gdnyProcesser;

    @Autowired
    private CCBProcesser ccbProcesser;

    @Autowired
    private CMBProcesser cmbProcesser;

    @Autowired
    private PABProcesser pabProcesser;

    public static void main(String args[]) throws IOException {
        SpringApplication.run(SocketApplication.class, args);

    }

    @Override
    public void run(String... strings) throws Exception {
        log.info("服务启动...");
        this.start();
    }

    public void start(){
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(7081);

            log.info("7081正在监听...");
            while (true){
                socket = serverSocket.accept();
                log.info(socket.hashCode() + " connected...");
                connectManager(socket);
            }
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }finally{
            try {
                serverSocket.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void connectManager(final Socket socket){
        new Thread(new Runnable() {
            private BufferedWriter writer;
            public void run() {
                try {
                    String receiveMsg;
                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "GBK"));
                    OutputStream os = socket.getOutputStream();
                    InputStream is = socket.getInputStream();
                    int i = -1;
                    byte[] buf = new byte[4096];
                    i = is.read(buf);
                    receiveMsg = new String(buf, 0, i);
                    log.info("receiveMsg is {}", receiveMsg);

                    Pattern GDNYKey = Pattern.compile(GDNY_KEY);
                    Matcher GDNYMatcher = GDNYKey.matcher(receiveMsg);

                    Pattern CCBKey = Pattern.compile(CCB_KEY);
                    Matcher CCBMatcher = CCBKey.matcher(receiveMsg);

                    Pattern CMBKey = Pattern.compile(CMB_KEY);
                    Matcher CMBMatcher = CMBKey.matcher(receiveMsg);

                    Pattern PABKEY_OPEN_FUND = Pattern.compile(OPEN_FUND);
                    Matcher PABmatcher_open_fund = PABKEY_OPEN_FUND.matcher(receiveMsg);

                    Pattern PABKEY_REDEEM = Pattern.compile(REDEEM);
                    Matcher PABmatcher_redeem = PABKEY_REDEEM.matcher(receiveMsg);

                    Pattern PABKEY_REDEEM_QUERY = Pattern.compile(REDEEM_QUERY);
                    Matcher PABmatcher_redeem_query = PABKEY_REDEEM_QUERY.matcher(receiveMsg);

                    Pattern PABKEY_PAYMENT = Pattern.compile(PAYMENT);
                    Matcher PABmatcher_payment = PABKEY_PAYMENT.matcher(receiveMsg);

                    Pattern PABKEY_PAY_QUERY = Pattern.compile(PAY_QUERY);
                    Matcher PABmatcher_pay_query = PABKEY_PAY_QUERY.matcher(receiveMsg);

                    String returnMsg = "";

                    if(GDNYMatcher.find()) {
                        returnMsg = gdnyProcesser.handleMessage(receiveMsg);
                        writer.write(returnMsg);
                        writer.flush();
                    } else if (CCBMatcher.find()) {
                        returnMsg = ccbProcesser.handle(receiveMsg);
                        writer.write(returnMsg);
                        writer.flush();
                    } else if (CMBMatcher.find()) {
                        returnMsg = cmbProcesser.handle(receiveMsg);
                        os.write(returnMsg.getBytes());
                        os.flush();
                    } else if (PABmatcher_open_fund.find()){
                        returnMsg = pabProcesser.handle(receiveMsg, OPEN_FUND);
                        os.write(returnMsg.getBytes("GBK"));
                        os.flush();
                    } else if (PABmatcher_payment.find()){
                        returnMsg = pabProcesser.handle(receiveMsg, PAYMENT);
                        os.write(returnMsg.getBytes("GBK"));
                        os.flush();
                    } else if (PABmatcher_pay_query.find()){
                        returnMsg = pabProcesser.handle(receiveMsg, PAY_QUERY);
                        os.write(returnMsg.getBytes("GBK"));
                        os.flush();
                    } else if (PABmatcher_redeem.find()){
                        returnMsg = pabProcesser.handle(receiveMsg, REDEEM);
                        os.write(returnMsg.getBytes("GBK"));
                        os.flush();
                    } else if (PABmatcher_redeem_query.find()){
                        returnMsg = pabProcesser.handle(receiveMsg, REDEEM_QUERY);
                        os.write(returnMsg.getBytes("GBK"));
                        os.flush();
                    }

                } catch (IOException e) {
                    log.error(e);
                } catch (Exception e) {
                    log.error(e);
                } finally {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        log.error(e);
                    }
                }
            }
        }).start();

    }

}


