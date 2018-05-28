package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.service.CmbcGwService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zuodongxiang on 17/6/5.
 * 民生网关
 */
@Controller
@Log4j2
public class CmbcGwController {
    private static final String ENCODING = "UTF-8";
    @Autowired
    private CmbcGwService cmbcGwServiceImpl;

//    @RequestMapping(value = "epay/fundAccess.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
//    public void doHandle(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
//
//        log.info("=========== 民生网关支付处理开始 ============");
//        String param = request.getParameter("cryptograph");
//        String[] params = param.split("\\|");
//        String returnResponse = null;
//        switch (params[0]) {
//            case "0002":
//                returnResponse = cmbcGwServiceImpl.verify(param);
//                model.put("merCUrl", params[6]);
//                model.put("cryptograph", returnResponse);
//                request.getRequestDispatcher("/web/cmbcgw/doPost").forward(request, response);
//                return;
//            case "0004":
//                returnResponse = cmbcGwServiceImpl.pay(param);
//                break;
//            case "0007":
//                returnResponse = cmbcGwServiceImpl.query(param);
//                break;
//        }
//        response.setContentType("text/html;charset=UTF-8");
//        response.setCharacterEncoding(ENCODING);
//        PrintWriter out = response.getWriter();
//        out.print(returnResponse);
//        out.flush();
//        out.close();
//
//    }

    @RequestMapping(value = "epay/fundAccess.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String doHandle(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        log.info("=========== 民生网关支付处理开始 ============");
        String param = request.getParameter("cryptograph");
        String[] params = param.split("\\|");
        String returnResponse = null;
        switch (params[0]) {
            case "0002":
                returnResponse = cmbcGwServiceImpl.verify(param);
                model.put("merCUrl", params[6]);
                model.put("cryptograph", returnResponse);
                log.info("=========== 民生网关鉴权跳转页面开始 ============");
                return "web/cmbcgw/doPost";
            case "0004":
                returnResponse = cmbcGwServiceImpl.pay(param);
                break;
            case "0007":
                returnResponse = cmbcGwServiceImpl.query(param);
                break;
        }
        request.setAttribute("returnResponse", returnResponse);
        return "forward:/cmbcgw/dohandleOther";
    }

    @RequestMapping(value = "cmbcgw/dohandleOther")
    public void dohandleOther(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String returnResponse = (String) request.getAttribute("returnResponse");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding(ENCODING);
        PrintWriter out = response.getWriter();
        out.print(returnResponse);
        out.flush();
        out.close();
        log.info("=========== 离开民生网关模拟器 ============");

    }

    @RequestMapping(value = "cmbcgw/test")
    public String test(HttpServletRequest request, HttpServletResponse response) {
        return "web/cmbcgw/test";

    }
}