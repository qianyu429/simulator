package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.service.BSService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by houjiagang on 16/11/21.
 */
@Controller
@Log4j2
public class BSController {

    private static final String ENCODING = "UTF-8";

    @Autowired
    private BSService bsServiceImpl;

    @RequestMapping(value = "kd_webtradeCombine/openacctIndividual_boshi")
    public void openacctIndividual(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("=========== 博时openacctIndividual开始 ============");
        String returnResponse = bsServiceImpl.handle(request, "openacctIndividual");
        response.setContentType("text/xml");
        response.setCharacterEncoding(ENCODING);
        PrintWriter out = response.getWriter();
        out.print(returnResponse);

    }

    @RequestMapping(value = "kd_webtradeCombine/buyfundimmediate_boshi")
    public void buyfundimmediate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("=========== 博时buyfundimmediate开始 ============");
        String returnResponse = bsServiceImpl.handle(request, "buyfundimmediate");
        response.setContentType("text/xml");
        response.setCharacterEncoding(ENCODING);
        PrintWriter out = response.getWriter();
        out.print(returnResponse);

    }

    @RequestMapping(value = "kd_webtradeCombine/redeemimmediate_boshi")
    public void redeemimmediate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("=========== 博时redeemimmediate开始 ============");
        String returnResponse = bsServiceImpl.handle(request, "redeemimmediate");
        response.setContentType("text/xml");
        response.setCharacterEncoding(ENCODING);
        PrintWriter out = response.getWriter();
        out.print(returnResponse);

    }

    @RequestMapping(value = "kd_webtradeCombine/redeemResultQuery_boshi")
    public void redeemResultQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("=========== 博时redeemResultQuery开始 ============");
        String returnResponse = bsServiceImpl.handle(request, "redeemResultQuery");
        response.setContentType("text/xml");
        response.setCharacterEncoding(ENCODING);
        PrintWriter out = response.getWriter();
        out.print(returnResponse);

    }

    @RequestMapping(value = "kd_webtradeCombine/openacctEnterprise_boshi")
    public void openacctEnterprise(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("=========== 博时openacctEnterprise开始 ============");
        String returnResponse = bsServiceImpl.handle(request, "openacctEnterprise");
        response.setContentType("text/xml");
        response.setCharacterEncoding(ENCODING);
        PrintWriter out = response.getWriter();
        out.print(returnResponse);

    }


    @RequestMapping(value = "kd_webtradeCombine/redeem98_boshi")
    public void redeem98(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("=========== 博时redeem98开始 ============");
        String returnResponse = bsServiceImpl.handle(request, "redeem98");
        response.setContentType("text/xml");
        response.setCharacterEncoding(ENCODING);
        PrintWriter out = response.getWriter();
        out.print(returnResponse);

    }


    @RequestMapping(value = "kd_webtradeCombine/confirmCancle_boshi")
    public void confirmCancle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("=========== 博时confirmCancle开始 ============");
        String returnResponse = bsServiceImpl.handle(request, "confirmCancle");
        response.setContentType("text/xml");
        response.setCharacterEncoding(ENCODING);
        PrintWriter out = response.getWriter();
        out.print(returnResponse);

    }
















}
