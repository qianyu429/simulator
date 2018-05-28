package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.service.CmbcT0Service;
import com.shhxzq.bs.service.Spdb6Service;
import com.shhxzq.bs.util.Spdb6Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * Created by zuodongxiang on 17/4/20.
 */

@Controller
@Log4j2
public class Spdb6Controller {
    private static final int PORT = 5100;
    /**
     * 获取对称密钥值
     */
    private static final String DELIY = "DaliySignKey";

    /**
     * 身份核实
     */
    private static final String VERIFY = "AcntCheck";

    /**
     * 代扣
     */
    private static final String PAY = "SinCut";

    /**
     * 代扣查询
     */
    private static final String QUERY_PAY = "SinCutQuery";

    /**
     * 代付
     */
    private static final String REDEEM = "SinPay";

    /**
     * 代付查询
     */
    private static final String QUERY_REDEEM = "SinPayQuery";

    @Autowired
    private Spdb6Service spdb6Service;

    public Spdb6Controller() throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);

        new Thread() {
            @Override
            public void run() {

                while (true) {
                    InputStream in = null;
                    OutputStream out = null;
                    try {
                        log.info("受托支付在" + PORT + "端口监听...");
                        Socket socket = serverSocket.accept();
                        log.info("========================== 进入受托支付系统 ===========================");
                        log.info("受托支付系统收到一个请求,正在处理...");
                        in = socket.getInputStream();

                        // 读请求
                        byte head[] = new byte[8];
                        in.read(head);
                        int totalLen = Integer.parseInt(new String(head));
                        log.info("接收的xml总长度为：{}", new String(head));

                        byte b[] = new byte[totalLen];
                        in.read(b);
                        String xml = new String(b, "GBK");
                        log.info("接收的xml为：{}", xml);

                        String activityCode = xml.substring(xml.indexOf("<ActivityCode>") + 14, xml.indexOf("</ActivityCode>"));
                        log.info("交易类型为:{}", activityCode);
                        // 处理不同的请求
                        String respXml = null;
                        if (VERIFY.equals(activityCode)) {
                            //身份验证
                            respXml = spdb6Service.verify();
                        } else if (PAY.equals(activityCode)) {
                            // 代扣
                            Map<String, String> requestXml = Spdb6Util.parseXml(xml);
                            Map<String, String> body = Spdb6Util.parseXml(requestXml.get("Body"));
                            respXml = spdb6Service.pay(body);
                        } else if (QUERY_PAY.equals(activityCode)) {
                            // 代扣查询
                            Map<String, String> requestXml = Spdb6Util.parseXml(xml);
                            Map<String, String> body = Spdb6Util.parseXml(requestXml.get("Body"));
                            respXml = spdb6Service.queryPay(body);

                        } else if (REDEEM.equals(activityCode)) {
                            // 代付
                            Map<String, String> requestXml = Spdb6Util.parseXml(xml);
                            Map<String, String> body = Spdb6Util.parseXml(requestXml.get("Body"));
                            respXml = spdb6Service.redeem(body);
                        } else if (QUERY_REDEEM.equals(activityCode)) {
                            // 代付查询
                            Map<String, String> requestXml = Spdb6Util.parseXml(xml);
                            Map<String, String> body = Spdb6Util.parseXml(requestXml.get("Body"));
                            respXml = spdb6Service.queryRedeem(body);
                        } else if (DELIY.equals(activityCode)) {
                            // 获取对称密钥值
                            respXml = spdb6Service.deliy();
                        } else {
                            // 不支持的交易类型
                            respXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><STPContext><Header><RespCode>90</RespCode><RespDesc>报文格式错误</RespDesc></Header></STPContext>";
                        }

                        // 写响应
                        int len = respXml.getBytes("GBK").length;
                        log.info("响应内容:{}", respXml);
                        log.info("响应报文长度:{}", new String(Spdb6Util.int2byte(len, 8)));

                        out = socket.getOutputStream();
                        out.write(Spdb6Util.int2byte(len, 8));
                        out.write(respXml.getBytes("GBK"));
                        out.flush();
                        log.info("响应回写完毕");
                        log.info("========================== 离开受托支付系统 ===========================");

                    } catch (Exception e) {
                        log.error("受托支付处理请求时异常", e);
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                                log.error("受托支付关闭输入流时异常", e);
                            }
                        }
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e) {
                                log.error("受托支付关闭输出流时异常", e);
                            }
                        }
                    }
                }


            }
        }

                .

                        start();
    }


}