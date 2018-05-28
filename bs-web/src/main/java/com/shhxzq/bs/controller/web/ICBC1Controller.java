package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.service.ICBC1Service;
import com.shhxzq.bs.util.Base64Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by zuodongxiang on 17/6/19.
 */
@Controller
@Log4j2
public class ICBC1Controller {
    @Autowired
    private ICBC1Service icbc1Service;

    @RequestMapping(value = "/servlet/EBConsignPayServlet", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String icbc1(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        log.info("=========== 进入工行网银签约页面 ============");
        String respStr = icbc1Service.sign(request);
        model.put("merVAR", request.getParameter("merVAR"));
        String respStrEncode = Base64Util.base64Encode(respStr);
        log.info("Send the Base64 encode notifyData is: ");
        log.info(respStrEncode);
        model.put("notifyData", respStrEncode);
        model.put("merURL", request.getParameter("merURL"));
        model.put("signMsg", "");
        log.info("=========== 工行网银签约跳转页面开始 ============");
        return "web/icbc1/doPost";
    }

    @RequestMapping(value = "icbc1/test")
    public String test(HttpServletRequest request, HttpServletResponse response) {
        return "web/icbc1/test";

    }
}
