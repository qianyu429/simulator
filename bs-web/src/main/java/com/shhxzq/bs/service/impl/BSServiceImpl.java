package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.mapping.bs.Request;
import com.shhxzq.bs.mapping.bs.Response;
import com.shhxzq.bs.service.BSService;
import com.shhxzq.bs.util.DateUtil;
import com.shhxzq.bs.util.StreamUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by houjiagang on 2016/11/21.
 */
@Component
@Log4j2
public class BSServiceImpl implements BSService {

    private static final String ENCODING = "UTF-8";

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

    private XStream requestParser;

    private XStream responseParser;

    @Override
    public String handle(HttpServletRequest request, String transCode) throws Exception {
        //init xstream
        requestParser = getXStreamInstance();
        requestParser.processAnnotations(Request.class);
        responseParser = getXStreamInstance();
        responseParser.processAnnotations(Response.class);
        byte[] requestBody = StreamUtil.readBytes(request.getInputStream());
        String requestString = new String(requestBody, ENCODING);
        log.info("Receive the request: {}", requestString);
        String respStr = convertResponse(requestString, transCode);
        log.info("Response is {}", respStr);
        return respStr;
    }

    private String convertResponse(String reqStr, String transCode){
        switch (transCode) {
            case "openacctIndividual":
                return processOpenAcctIndividual(reqStr);
            case "buyfundimmediate":
                return processBuyfundimmediate(reqStr);
            case "redeemimmediate":
                return processRedeemimmediate(reqStr);
            case "redeemResultQuery":
                return processRedeemResultQuery(reqStr);
            case "openacctEnterprise":
                return processOpenacctEnterprise(reqStr);
            case "redeem98":
                return processRedeem98(reqStr);
            case "confirmCancle":
                return processConfirmCancle(reqStr);

        }

        return null;
    }

    private String processConfirmCancle(String reqStr){
        Request request = (Request) requestParser.fromXML(reqStr);
        Response response = new Response();
        response.setAttach("");
        response.setEcpayCode("HXZQDS");
        response.setFundDate(DateUtil.format8Date(new Date()));
        response.setInputCharset("UTF-8");
        response.setOriconfirmAmount(request.getApplyUnits());
        response.setOriconfirmUnits(request.getApplyUnits());
        response.setOutTradeNo("205201611090000000420461");
        response.setPartner("BOSERAFUND");
        response.setReferserialId(request.getReferserialId());
        response.setRetcode("0000");
        response.setRetmsg("成功");
        response.setServiceVersion("1.0");
        response.setSignKeyIndex("1");
        response.setSignType("MD5");
        response.setSpTransId(request.getSpTransId());
        response.setSpUser(request.getSpUser());
        response.setTimeEnd(DateUtil.format14Date(new Date()));
        response.setTotalFundUnits(request.getApplyAmount());
        response.setSign("E583FD1F432E908D59778B8C610B9F12");

        String respStr = XML_DECLARATION + responseParser.toXML(response);
        return respStr;
    }
    private String processRedeem98(String reqStr){
        Request request = (Request) requestParser.fromXML(reqStr);
        Response response = new Response();
        response.setFundDate(DateUtil.format8Date(new Date()));
        response.setAttach("");
        response.setEcpayCode("HXZQDS");
        response.setInputCharset("UTF-8");
        response.setModifyTime(DateUtil.format14Date(new Date()));
        response.setOutTradeNo(request.getTransactionId());
        response.setPartner("BOSERAFUND");
        response.setPartnerFundNo(request.getPartnerFundNo());
        response.setRetcode("0000");
        response.setRetmsg("成功");
        response.setServiceVersion("1.0");
        response.setSignKeyIndex("1");
        response.setSignType("MD5");
        response.setSpTransId(request.getPartnerFundNo());
        response.setSpUser("056100138749");
        response.setTradeStatus("0");
        response.setTimeEnd(DateUtil.format14Date(new Date()));
        response.setSign("D6C879D320769BE0223D9A9882F3124A");

        String respStr = XML_DECLARATION + responseParser.toXML(response);
        return respStr;
    }

    private String processOpenacctEnterprise(String reqStr){
        Request request = (Request) requestParser.fromXML(reqStr);
        Response response = new Response();
        response.setAttach("");
        response.setEcpayCode("HXZQDS");
        response.setInputCharset("UTF-8");
        response.setModifyTime(DateUtil.format14Date(new Date()));
        response.setPartner("BOSERAFUND");
        response.setPartnerFundNo(request.getPartnerFundNo());
        response.setRetcode("0000");
        response.setRetmsg("成功");
        response.setServiceVersion("1.0");
        response.setSignKeyIndex("1");
        response.setSignType("MD5");
        response.setSpTransId(request.getPartnerFundNo());
        response.setSpUser("056100138749");
        response.setTradeStatus("0");
        response.setSign("D6C879D320769BE0223D9A9882F3124A");

        String respStr = XML_DECLARATION + responseParser.toXML(response);
        return respStr;

    }


    private String processRedeemResultQuery(String reqStr){
        Request request = (Request) requestParser.fromXML(reqStr);
        Response response = new Response();
        response.setAllotDate("");
        response.setAttach("backquery");
        response.setEcpayCode("HXZQ");
        response.setFundDate(DateUtil.format8Date(new Date()));
        response.setFundshareType("1");
        response.setInputCharset("UTF-8");
        response.setOutTradeNo("currentTime");
        response.setPartner("BOSERAFUND");
        response.setRetcode("0000");
        response.setRetmsg("成功");
        response.setServiceVersion("1.0");
        response.setSignKeyIndex("1");
        response.setSignType("MD5");
        response.setSpTransId("afadezhanghao");
        response.setSpUser("051107622427");
        response.setTimeEnd(DateUtil.format14Date(new Date()));
        response.setTradeStatus("0");
        response.setTransactionId("currentTime");
        response.setSign("948A4F91C7A3E2A8855B663B72EACBCA");

        String respStr = XML_DECLARATION + responseParser.toXML(response);
        return respStr;
    }

    private String processRedeemimmediate(String reqStr){
        Request request = (Request) requestParser.fromXML(reqStr);
        Response response = new Response();
        response.setAllotDate(DateUtil.format8Date(new Date()));
        response.setAttach("");
        response.setEcpayCode("HXZQDS");
        response.setFundDate(DateUtil.format8Date(new Date()));
        response.setFundshareType("0");
        response.setInputCharset("UTF-8");
        response.setOutTradeNo(request.getTransactionId());
        response.setPartner("BOSERAFUND");
        response.setRetcode("0000");
        response.setRetmsg("成功");
        response.setServiceVersion("1.0");
        response.setSignKeyIndex("1");
        response.setSignType("MD5");
        response.setSpTransId(request.getPartnerFundNo());
        response.setSpUser(request.getSpUser());
        response.setTimeEnd(DateUtil.format14Date(new Date()));
        response.setTotalFundUnits(request.getApplyUnits());
        response.setTransactionId(request.getTransactionId());
        response.setSign("31777792BFCA7BEC81A3FF0A22876E31");

        String respStr = XML_DECLARATION + responseParser.toXML(response);
        return respStr;
    }

    private String processBuyfundimmediate(String reqStr){
        Request request = (Request) requestParser.fromXML(reqStr);
        Response response = new Response();
        response.setAttach("sub");
        response.setConfirmUnits(request.getApplyAmount());
        response.setEcpayCode("HXZQDS");
        response.setFeeType("1");
        response.setFundDate(DateUtil.format8Date(new Date()));
        response.setIncArriveDate(DateUtil.format8Date(new Date()));
        response.setIncBeginDate(DateUtil.format8Date(new Date()));
        response.setInputCharset(request.getInputCharset());
        response.setOutTradeNo(request.getTransactionId());
        response.setPartner("BOSERAFUND");
        response.setRetcode("0000");
        response.setRetmsg("成功");
        response.setServiceVersion("1.0");
        response.setSignKeyIndex("1");
        response.setSignType("MD5");
        response.setSpTransId(request.getPartnerFundNo());
        response.setSpUser(request.getSpUser());
        response.setTimeEnd(DateUtil.format14Date(new Date()));
        response.setTotalFundUnits(request.getApplyAmount());
        response.setTransactionId(request.getTransactionId());
        response.setSign("8ACD32969875C517678E1D89315D26EB");

        String respStr = XML_DECLARATION + responseParser.toXML(response);
        return respStr;

    }

    private String processOpenAcctIndividual(String reqStr){
        Request request = (Request) requestParser.fromXML(reqStr);
        Response response = new Response();
        response.setAttach("");
        response.setEcpayCode("HXZQDS");
        response.setInputCharset("utf-8");
        response.setModifyTime(request.getAccTime());
        response.setPartner("BOSERAFUND");
        response.setPartnerFundNo(request.getPartnerFundNo());
        response.setRetcode("0000");
        response.setRetmsg("成功");
        response.setServiceVersion("1.0");
        response.setSignKeyIndex("1");
        response.setSignType("MD5");
        response.setSpTransId(request.getPartnerFundNo());
        response.setSpUser(RandomStringUtils.randomNumeric(12));
        response.setTradeStatus("0");
        response.setSign("982E4206A18A6AB30DCC5C4CADA4213A");

        String respStr = XML_DECLARATION + responseParser.toXML(response);
        return respStr;

    }


    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }
}
