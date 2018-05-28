package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.service.Spdb6THService;
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
 * Created by zuodongxiang on 17/5/8.
 * 同行受托支付
 */

@Controller
@Log4j2
public class Spdb6THController {
    private static final int PORT = 4000;

    /**
     * 单笔扣款标识
     */
    private static final String PAY_TONG_HANG = "GATHER";
    /**
     * 单笔付款标识
     */
    private static final String REDEEM_TONG_HANG = "PAY";
    /**
     * 查询标识
     */
    private static final String QRYRST = "QRYRST";


    @Autowired
    private Spdb6THService spdb6THService;

    public Spdb6THController() throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);

        new Thread() {
            @Override
            public void run() {

                while (true) {
                    InputStream in = null;
                    OutputStream out = null;
                    try {
                        log.info("受托支付同行系统在" + PORT + "端口监听...");
                        Socket socket = serverSocket.accept();
                        log.info("========================== 进入受托支付同行系统 ===========================");
                        log.info("受托支付同行系统收到一个请求,正在处理...");
                        in = socket.getInputStream();

                        // 读请求
                        byte head1[] = new byte[34];//读取报文总长度,版本号,商户号,时间戳
                        in.read(head1);
                        System.out.println("head2---" + new String(head1));
                        byte head2[] = new byte[8];//读取交易报文长度
                        in.read(head2);
                        System.out.println("head2---" + new String(head2));
                        int totalLen = Integer.parseInt(new String(head2));
                        log.info("交易报文长度为：{}", new String(head2));
                        byte b[] = new byte[totalLen];
                        in.read(b);
                        String xml = new String(b, "GBK");//通过使用指定的GBK解码byte数组b，构造一个新的String。
                        log.info("接收的xml为：{}", xml);
                        Map<String, String> parameters = Spdb6Util.parseXml(xml);
                        String trncode = parameters.get("TRNCODE");
                        log.info("交易类型为:{}", trncode);
                        // 处理不同的请求
                        String respXml = null;
                        if (PAY_TONG_HANG.equals(trncode)) {
                            //单笔扣款
                            respXml = spdb6THService.payTongHang(parameters);
                        } else if (REDEEM_TONG_HANG.equals(trncode)) {
                            // 单笔付款
                            respXml = spdb6THService.redeemTongHang(parameters);
                        } else if (QRYRST.equals(trncode)) {
                            //查询交易
                            respXml = spdb6THService.queryTrade(parameters);
                        }
                        // 写响应
                        int len = respXml.getBytes("GBK").length;
                        log.info("响应内容:{}", respXml);
                        log.info("响应报文长度:{}", new String(Spdb6Util.int2byte(len + 2, 8)));//含有返回码00,字节数加2
                        out = socket.getOutputStream();
                        out.write(Spdb6Util.int2byte(len + 2, 8));//报文长度
                        out.write("00".getBytes());//返回码
                        out.write(respXml.getBytes("GBK"));//报文
                        out.flush();
                        log.info("响应回写完毕");
                        log.info("========================== 离开受托支付同行系统 ===========================");

                    } catch (Exception e) {
                        log.error("受托支付同行系统处理请求时异常", e);
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                                log.error("受托支付同行系统关闭输入流时异常", e);
                            }
                        }
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e) {
                                log.error("受托支付同行系统关闭输出流时异常", e);
                            }
                        }
                    }
                }


            }
        }.start();
    }


}