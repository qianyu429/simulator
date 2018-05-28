package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.service.Spdb2Service;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 浦发网关支付
 * Created by wanglili on 17/5/2.
 */

@Controller
@Log4j2
public class Spdb2Controller {

    private static final String ENCODING = "GBK";


    @Autowired
    private Spdb2Service spdbServiceImpl;
    /**
     * payment/main 浦发网关协议签约绑卡
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "payment/main")
    public String spdb(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        log.info("=========== 浦发网关绑卡接口开始 ===========");
        String transName = request.getParameter("transName");
        String plain = request.getParameter("Plain");
        String signature = request.getParameter("Signature");

        Map map = parsePlain(plain);
        String merCUrl = map.get("MercUrl").toString();
        String merc_id = map.get("Merc_id").toString();
        String mercDtTm = map.get("MercDtTm").toString();
        String mercCode = map.get("MercCode").toString();

        StringBuilder sb = new StringBuilder();
        sb.append("TranAbbr=").append(transName).append("|");
        sb.append("Merc_id=").append(merc_id).append("|");
        sb.append("MercDtTm=").append(mercDtTm).append("|");
        sb.append("MercCode=").append(mercCode).append("|");
        //永远返回签约成功
        sb.append("RespCode=00");

        log.info("回调地址是: {}", merCUrl);
        model.put("merCUrl", merCUrl);

        log.info("Plain是: {}", sb.toString());
        model.put("Plain", sb.toString());

        model.put("transName", transName);
        model.put("Signature", signature);

        return "web/spdb2/doPost";

    }

    @RequestMapping(value = "spdb2/test")
    public String test(HttpServletRequest request, HttpServletResponse response) {
        return "web/spdb2/test";

    }

    /**
     * payment/main 浦发快捷
     *
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "payment/mainPaygate" , consumes = {"application/xml", "text/xml", "text/plain"})
    public void spdbPaygate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("=========== 浦发网关历史对账开始 ============");
        String returnResponse = spdbServiceImpl.handle(request);

        response.setContentType("text/xml");
        response.setCharacterEncoding(ENCODING);
        PrintWriter out = response.getWriter();
        out.print(returnResponse);

    }

    private Map parsePlain(String plain) {

        Map map = new HashMap<>();
        if (StringUtils.isEmpty(plain)) {
            log.error("请求报文Plain为空");
            return null;
        }

        String[] values = StringUtils.split(plain, "|");
        for (String value : values) {
            String[] tmp = value.split("=");
            String key = tmp[0];
            String val = tmp[1];
            map.put(key, val);
        }

        return map;
    }

}
