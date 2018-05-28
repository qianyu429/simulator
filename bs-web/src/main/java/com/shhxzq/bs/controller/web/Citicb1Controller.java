package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.CiticService;
import com.shhxzq.bs.service.TransactionService;
import com.shhxzq.bs.util.DateUtil;
import com.shhxzq.bs.util.SignatureUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;

/**
 * 中信联名卡
 * Created by wanglili on 17/03/03.
 */
@Controller
@Log4j2
public class Citicb1Controller {

    @Value("${spw.server}")
    private String spwServerIP;

    @Value("${spw.port}")
    private String spwServerPort;

    private static final String ENCODING = "UTF-8";

    @Autowired
    private CiticService citicServiceImpl;

    @Autowired
    private TransactionService transactionService;

    private String key1 = "wz3z0tiuduaid0bf2mh614tj62bokzml";
    private String key2 = "x2r991zdq2nblz3na58mspt248kdq90i";

    /**
     * 中信联名卡业务请求处理.
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "citiccard/cardshopapi/basic.do", method = RequestMethod.POST, consumes = "text/xml;charset=UTF-8")
    @ResponseBody
    public void doHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("=========== 中信联名卡基础校验开始 ============");
        String param = request.getParameter("func");
        String returnResponse = null;
        switch (param) {
            case "basicCheck":
                returnResponse = citicServiceImpl.handleBasicCheck(request);
                break;
            case "applyNewCard":
                returnResponse = citicServiceImpl.handleApplyNewCard(request);
                break;
        }
        response.setContentType("text/xml");
        response.setCharacterEncoding(ENCODING);
        PrintWriter out = response.getWriter();
        out.print(returnResponse);
        out.flush();
        out.close();
    }

    /**
     * 中信联名卡二卡申请跳转.
     *
     * @param request
     * @param model
     * @throws Exception
     */
    @RequestMapping(value = "citiccard/cardishop/secondcard/index_wap.html")
    public String doSecondCardNavigate(HttpServletRequest request, ModelMap model) {
        log.info("=========== 中信联名卡二卡申请跳转开始 ============");
        String partner_id = request.getParameter("partner_id");
        String partner_apply_id = request.getParameter("partner_apply_id");
        String partner_user_id = request.getParameter("partner_user_id");
        String submit_date = request.getParameter("submit_date");
        String sign = request.getParameter("sign");

        Transaction trans = transactionService.findTransactionBySerNo4Query(partner_apply_id);
        String apply_name = trans.getMemo2();
        String idNum = trans.getIdNum();

        model.put("partner_id", partner_id);
        model.put("partner_apply_id", partner_apply_id);
        model.put("partner_user_id", partner_user_id);
        model.put("submit_date", submit_date);
        model.put("sign", sign);
        model.put("apply_name", apply_name);
        model.put("idNum", idNum);

        return "web/citicb1/secondCardInfo";
    }


    @RequestMapping(value = "citicb1/test")
    public String test(HttpServletRequest request, HttpServletResponse response) {
        return "web/citicb1/test";
    }


    /**
     * 中信联名卡二卡申请结果返回.
     *
     * @param request
     * @param model
     * @throws Exception
     */
    @RequestMapping(value = "citicb1/doSecondCardApply")
    public String doSecondCardApplyResult(HttpServletRequest request, ModelMap model) throws Exception {
        log.info("=========== 中信联名卡二卡申请结果 ============");
        String partner_id = request.getParameter("partner_id");
        String partner_apply_id = request.getParameter("partner_apply_id");
        String partner_user_id = request.getParameter("partner_user_id");
        String submit_date = request.getParameter("submit_date");

        String applyDate = DateUtil.format14Date(new Date());

        HashMap result = citicServiceImpl.handleApplySecondCard(partner_apply_id);
        model.put("partner_id", partner_id);
        model.put("partner_apply_id", partner_apply_id);
        model.put("partner_user_id", partner_user_id);
        model.put("apply_date", applyDate);
        model.put("result_code", result.get("errCode"));

        SignatureUtil signatureUtil = new SignatureUtil();
        signatureUtil.setKey(key1, key2);
        String sign = signatureUtil.getSignature(model);

        //中信二卡申请回调地址http://10.199.101.213:8088/spw/citicb/sencondCardsCallBack.service?partner_id=HX00&partner_apply_id=2017041800000044&partner_user_id=0002000009&apply_date=20170418164415&result_code=9993&result_msg=提交成功&sign=c2d42a9d58e07d7d0febd5d9555c4dce
        String postUrl = "http://" + spwServerIP + ":" + spwServerPort + "/spw/citicb/sencondCardsCallBack.service?" +
                "partner_id=" + partner_id + "&partner_apply_id=" + partner_apply_id +
                "&partner_user_id=" + partner_user_id + "&submit_date=" + submit_date +
                "&result_code=" + result.get("errCode") +
                "&result_msg=" + result.get("errMsg") + "&apply_date=" + applyDate +
                "&sign=" + sign;

        log.info("回调地址是: {}", postUrl);

        model.put("postUrl", postUrl);

        return "web/citicb1/doPost";
    }

}
