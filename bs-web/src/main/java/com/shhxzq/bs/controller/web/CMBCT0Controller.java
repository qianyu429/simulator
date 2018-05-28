package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.service.CmbcT0Service;
import com.shhxzq.bs.util.SocketThread;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wanglili on 16/12/30.
 */
@Controller
@Log4j2
public class CMBCT0Controller {

    private static final int PORT = 9108;

    @Autowired
    private CmbcT0Service cmbcT0Service;

    public CMBCT0Controller() throws Exception {

        ServerSocket serverSocket = new ServerSocket(PORT);

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        log.info("民生T+0垫资服务在" + PORT + "端口监听...");
                        Socket socket = serverSocket.accept();
                        new SocketThread(socket, cmbcT0Service).start();

                    } catch (Exception e) {
                        log.error("民生T+0服务启动异常", e);
                    }
                }
            }
        }.start();

    }


}
