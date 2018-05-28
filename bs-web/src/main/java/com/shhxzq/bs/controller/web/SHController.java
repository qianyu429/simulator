package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.service.SHService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 招行银企
 * Created by houjiagang on 16/11/16.
 */
@Controller
@Log4j2
public class SHController {

    @Autowired
    private SHService shService;

    @Value("${spw.server}")
    private String spwServerIP;

    @Value("${spw.port}")
    private String spwServerPort;

    /**
     * 上海快捷
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "boscartoon/shortcutPayStdGateway.do")
    public void redeem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String respStr = shService.handle(request);
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(respStr);

    }

    /**
     * 上海银企
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "CM/APIReqServlet")
    public void redeemSHYQ(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String respStr = shService.handleYQ(request);
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(respStr);

    }




}
