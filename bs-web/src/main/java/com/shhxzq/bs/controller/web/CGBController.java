package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.service.CGBService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 广发快捷
 * Created by wanglili on 17/5/3.
 */
@Controller
@Log4j2
public class CGBController {
    private static final String ENCODING = "UTF-8";

    @Autowired
    private CGBService cgbService;

    /**
     * 签约,支付和查询
     *
     * @param request
     */
    @RequestMapping(value = "spayment/servlet/gfbank.portal.GFMerTrade")
    public void doHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String respBody = cgbService.doHandle(request);
        response.setContentType("text/xml");
        response.setCharacterEncoding(ENCODING);
        PrintWriter out = response.getWriter();
        out.print(respBody);

    }

    /**
     * 短信发送
     *
     * @param request
     */
    @RequestMapping(value = "spayment/servlet/gfbank.portal.GFPortalTrade")
    public void doSMS(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String respBody = cgbService.doHandle(request);
        response.setContentType("text/xml");
        response.setCharacterEncoding(ENCODING);
        PrintWriter out = response.getWriter();
        out.print(respBody);
    }

}
