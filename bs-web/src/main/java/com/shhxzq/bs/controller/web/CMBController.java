package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.mapping.cmb.PkgBodyBKQDReq;
import com.shhxzq.bs.mapping.cmb.PkgHeader;
import com.shhxzq.bs.service.CMBService;
import com.shhxzq.bs.util.DateUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

/**
 * 招行银企
 * Created by houjiagang on 16/10/27.
 */
@Controller
@Log4j2
public class CMBController {


    @Value("${be.server}")
    private String beServerIP;

    @Autowired
    private CMBService cmbService;

    /**
     * redeem
     *
     * @param request
     * @param response
     *
     * @throws Exception
     */
    @RequestMapping(value = "")
    public void redeem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String respStr = cmbService.handle(request);
        response.setContentType("text/xml");
        response.setCharacterEncoding("GBK");
        PrintWriter out = response.getWriter();
        out.print(respStr);

    }


    @RequestMapping(value = "cmb/BindPayFeeFdmApply")
    public String signPre(HttpServletRequest request, ModelMap model){
        String accno = request.getParameter("McAccountNo");
        String custCode = request.getParameter("uin");
        String callbackUrl = request.getParameter("url");

        model.put("accno", accno);
        model.put("custCode", custCode);
        model.put("callbackUrl", callbackUrl);

        return "web/cmb/signPre";


    }

    @RequestMapping(value = "cmb/doPost")
    public String doPost(HttpServletRequest request, ModelMap model) throws Exception {
        String accno = request.getParameter("accno");
        String custCode = request.getParameter("custCode");
        String callbackUrl = request.getParameter("callbackUrl");

        int serverPort = 6002;

        InetAddress inetAddress = InetAddress.getByName(beServerIP);
        InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, serverPort);
        Socket socket = new Socket();
        socket.connect(socketAddress);

        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();


        PkgBodyBKQDReq requestBody = new PkgBodyBKQDReq();
        requestBody.setCustCode(custCode);
        requestBody.setAcctno(accno);
        requestBody.setAreaCode("0574");
        requestBody.setMemo("D");

        String bodyString = requestBody.getPackage();

        PkgHeader header = new PkgHeader();
        header.setPkgFlag("CMBA");
        header.setPkgLen(String.valueOf(bodyString.getBytes().length));
        header.setTransCode("BKQD");
        header.setBnkDate(DateUtil.format8Date(new Date()));
        header.setBnkTime("120000");
        header.setBnkFlowNo(RandomStringUtils.randomNumeric(16));
        header.setCorpDate(DateUtil.format8Date(new Date()));
        header.setCorpTime("120000");
        header.setCorpFlowNo(RandomStringUtils.randomNumeric(16));
        header.setReqCorpNo("CMBCHINA");
        header.setAckCode(RandomStringUtils.randomNumeric(16));

        String headerString = header.getPackage();
        String callbackString = headerString + bodyString;


        outputStream.write(callbackString.getBytes("UTF-8"));
        outputStream.flush();

        StringBuffer receiveData = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream)));
        while(true) {
            String readed = reader.readLine();
            if(readed !=null){
                receiveData.append(new String(readed.getBytes(), "UTF-8"));

            } else {
                break;
            }
        }

        String datas = receiveData.toString();
        log.info("The receive data is {}", datas);
        String returnUrl = "web/cmb/doPost";
        if(!datas.contains("CMBMB99")){
            returnUrl = "web/cmb/doPostFail";
        }

        model.put("callbackUrl", callbackUrl);

        return returnUrl;


    }

    @RequestMapping(value = "cmb/doPostFail")
    public String doPostFail(HttpServletRequest request, ModelMap model){

        return "web/cmb/doPostFail";

    }

    @RequestMapping(value = "cmb/test")
    public String test(HttpServletRequest request, HttpServletResponse response){
        return "web/cmb/test";

    }



}
