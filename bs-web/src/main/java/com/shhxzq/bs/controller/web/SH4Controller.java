package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.service.SH4Service;
import com.shhxzq.bs.util.HttpUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 上海直联
 * Created by zhangzhenzhen on 17/7/13.
 */


@Controller
@Log4j2
public class SH4Controller {

    @Autowired
    private SH4Service sh4Service;

    @Value("${spw.server}")
    private String spwServerIP;

    @Value("${spw.port}")
    private String spwServerPort;


    /**
     * 签约
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "boscartoon/pagesign.do")
    public String sign(HttpServletRequest request, ModelMap model) {

        String cell = request.getParameter("cell");//获取请求中的流水号
        String notifyUrl = request.getParameter("notifyUrl");//后台通知地址
        String showUrl = request.getParameter("showUrl");//页面显示地址

        model.put("cell", cell);
        model.put("notifyUrl", notifyUrl);
        model.put("showUrl", showUrl);

        return "web/sh4/pagesign";

    }


    @RequestMapping(value = "sh4/notify", method = RequestMethod.POST)
    public String notify(HttpServletRequest request, Model model) {
        String url1 = request.getParameter("notifyUrl");
        String url2 = request.getParameter("showUrl");
        String cell = request.getParameter("cell");
        String resultCode = request.getParameter("resultCode");
        String signNo = RandomStringUtils.randomAlphanumeric(8);//随机生成签约协议号
        HttpUtil.sendPost(url1, String.format("cell=%s&signNo=%s&errorCode=%s", cell, signNo, resultCode));
        model.addAttribute("url", url2);
        return "web/sh4/url";



    }


    /**
     * 解约/支付/明细查询
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "boscartoon/directpay.do", method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String respStr = sh4Service.handleZL(request);
        response.setContentType("application/xml");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(respStr);

    }


    @RequestMapping(value = "boscartoon/test")
    public String test(HttpServletRequest request, ModelMap model) {
        return "web/sh4/test";

    }

}
