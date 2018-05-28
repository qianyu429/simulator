package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.service.FaceIdentifyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 人脸识别
 * Created by zhangzhenzhen on 17/7/11.
 */
@Controller
@Log4j2
public class FaceIdentifyController {
    @Autowired
    private FaceIdentifyService faceIdentifyService;


    /**
     * 人脸识别
     * @param request
     * @param response
     * @throws IOException
     */

    @RequestMapping(value = "faceidentify", method = RequestMethod.POST)
    public void faceIdentify (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String responsestr = faceIdentifyService.handle();
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(responsestr);

    }
}
