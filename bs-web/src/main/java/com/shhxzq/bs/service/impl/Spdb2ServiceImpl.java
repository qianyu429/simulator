package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.mapping.spdb.ErrorResponse;
import com.shhxzq.bs.mapping.spdb.Request;
import com.shhxzq.bs.mapping.spdb.Response;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.Spdb2Service;
import com.shhxzq.bs.service.TransactionService;
import com.shhxzq.bs.util.SpdbUtil;
import com.shhxzq.bs.util.StreamUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wanglili on 17/5/8.
 */
@Component
@Log4j2
public class Spdb2ServiceImpl implements Spdb2Service {

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n";
    private static final String XML_DECLARATION_GB2312 = "<?xml version=\"1.0\" encoding=\"GB2312\"?>\n";
    private static final Pattern PATTERN_REPLACEMENT;
    private static final Pattern PATTERN_REPLACEMENT1;
    private static final String ENCODING = "GBK";

    private XStream requestParser;
    private XStream responseParser;
    private XStream errResponseParser;

    private XStream cRequestParser;
    private XStream cResponseParser;

    private XStream queryRequestParser;


    @Autowired
    private BankService bankService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private TransactionService transactionService;

    static {
        PATTERN_REPLACEMENT = Pattern.compile("(?<=<transName>)(.*?)(?=</transName>)");
        PATTERN_REPLACEMENT1 = Pattern.compile("(?<=<transCode>)(.*?)(?=</transCode>)");
    }

    @Override
    public String handle(HttpServletRequest request) throws Exception {
        //init xstream
        requestParser = getXStreamInstance();
        responseParser = getXStreamInstance();
        errResponseParser = getXStreamInstance();

        requestParser.processAnnotations(Request.class);
        responseParser.processAnnotations(Response.class);
        errResponseParser.processAnnotations(ErrorResponse.class);

        //receive request
        byte[] requsetBody = StreamUtil.readBytes(request.getInputStream());
        String requestString = new String(requsetBody, ENCODING);
        log.info("Receive the request: {}", requestString);

        //convert response by transName
        String responseStr = convertResponse(requestString);
        log.info("response is: {}", responseStr);

        return responseStr;
    }

    private String convertResponse(String requestString) {
        Matcher matcher = PATTERN_REPLACEMENT.matcher(requestString);
        if (!matcher.find()) {
            throw new UnsupportedOperationException("The transName cannot be found in request xml!");
        }
        String transName = matcher.group(0);
        switch (transName) {
            case "IDFR":
                return processIDFR(requestString);
        }

        return null;
    }

    private String processIDFR(String requestString) {
        log.info("============ 浦发非当日充值对账(IDFR)开始 ============");
        Request request = (Request) requestParser.fromXML(requestString);
        HashMap<String, String> plainMap = SpdbUtil.plainToMap(request.getPlain());
        List<Transaction> transactions = transactionService.findTransatcions(AppConstants.BANK_NO_SPDB2, TransTypeEnum.PAY.getType(), plainMap.get("OSttDate"));
        StringBuilder plain = new StringBuilder();
        plain.append("SettFile=");
        for (Transaction tran : transactions) {
            plain.append("IPER").append("|");
            plain.append(tran.getWorkDay()).append("|"); //清算日期
            plain.append(tran.getSendTime()).append("|"); //交易发生时间
            plain.append(tran.getBeSer()).append("|"); //订单号
            plain.append(tran.getBsSer()).append("|"); //网关流水号
            plain.append(plainMap.get("MercCode")).append("|"); //商户号
            plain.append("1a|"); //终端号
            plain.append(tran.getAmount()).append("|"); //交易金额
            plain.append("0.00|"); //手续费
            plain.append(tran.getAmount()).append("|"); //净清算金额
            plain.append(tran.getRespCode()).append("|"); //响应吗
            plain.append(tran.getSendTime()).append("|"); //交易备注1
            plain.append("|"); //交易备注2

            plain.append("\r\n");
        }

        Response response = new Response();
        response.setTransName("IDFR");
        response.setPlain(plain.toString());
        response.setSignature(request.getSignature());

        return XML_DECLARATION + responseParser.toXML(response);

    }

    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }

}
