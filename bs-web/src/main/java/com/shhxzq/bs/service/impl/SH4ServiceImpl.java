package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.mapping.sh4.Banksh;
import com.shhxzq.bs.mapping.sh4.Message;
import com.shhxzq.bs.mapping.sh4.TradeInfo;
import com.shhxzq.bs.mapping.sh4.ZLResponse;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.SH4Service;
import com.shhxzq.bs.service.TransactionService;
import com.shhxzq.bs.util.StreamUtil;
import com.shhxzq.bs.util.SH4Util;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 上海直联支付
 * Created by zhangzhenzhen on 17/7/13.
 */

@Component
@Log4j2
public class SH4ServiceImpl implements SH4Service{


    private static final String ENCODING = "UTF-8";

    private XStream parser;

    private XStream respParser;

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    @Autowired
    private BankService bankService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private TransactionService transactionService;

    private static final Pattern PATTERN_REPLACEMENT;

    static {
        PATTERN_REPLACEMENT = Pattern.compile("(?<=<OSNO>)(.*?)(?=</OSNO>)");
    }




    @Override
    public String handleZL(HttpServletRequest request) throws IOException {
        parser = getXStreamInstance();
        parser.processAnnotations(Banksh.class);
        byte[] requestBody = StreamUtil.readBytes(request.getInputStream());
        String reqStr = new String(requestBody, ENCODING);
        log.info("Receive the request: {}", reqStr);
        Banksh req = (Banksh) parser.fromXML(reqStr);
        String respStr = converResponseZL(req, req.getMessage().getZLRequest().getId());
        log.info("Response is {}", reqStr);
        return respStr;

    }

    private String converResponseZL(Banksh request, String transCode){
        switch (transCode) {
            case "CSCReq":
                return processCSCReqZL(request);//直联解约
            case "CPReq":
                return processCPReqZL(request);//直联支付
            case "TQReq":
                return processSQReqZL(request);//直联交易明细查询
        }
        return null;
    }


    private String processCSCReqZL(Banksh request) {
        log.info("============ 上海直联解约开始 ============");

        ZLResponse zlResponse = new ZLResponse();
        zlResponse.setId("CSCRes");
        zlResponse.setVersion("1.0.1");
        zlResponse.setInstId(request.getMessage().getZLRequest().getInstId());
        zlResponse.setSignNo(request.getMessage().getZLRequest().getSignNo());
        zlResponse.setSerialNo("40201705101308306713");
        zlResponse.setDate("20170510 17:26:51");
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_SHZL);
        Config config = configService.findBankSelectConfig(bank.getCode(), "calsignZL");
        String errorCode = "succ";
        if (config!=null) {
            errorCode = config.getK();
        }
        zlResponse.setErrorCode(errorCode);

        Message zLMessage = new Message();
        zLMessage.setId(request.getMessage().getId());
        zLMessage.setZLResponse(zlResponse);
        zLMessage.setSignature("aE7wBSEcLRGemt76dhi0DPSVRbJdS2yHGaaOnIuFFVO3DoDToOg8z7TzM5knaUQUd83HIgFDSSrlxaPtxy+WxW36M7ZSLRp0NxYloRDV2i7+76KlKk/q4olmLfeBusPSEcwWGRePu4P9zNmRCo2KZ/NH5SereDLzynZeKI1CAwo=");

        Banksh res = new Banksh();
        res.setMessage(zLMessage);

        String reqStr = XML_DECLARATION + parser.toXML(res);

        return reqStr;
    }



    private String processCPReqZL(Banksh request) {
        log.info("============ 上海直联支付开始 ============");
        String serNo = configService.getSerNo();
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_SHZL);
        Config config = configService.findBankSelectConfig(bank.getCode(), "payZL");
        String errorCode = "succ";
        if (config!=null) {
            errorCode = config.getK();
            log.info("强制改变支付响应码为: {}", errorCode);
        }else {
            log.info("不强制改变响应码, 支付成功...");
        }

        Transaction tran = transactionService.findTransactionBySerNo(request.getMessage().getId());
        if(tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            errorCode = "2001";
        } else {
            log.info("交易不重复,生成交易并入库...");
            Transaction transaction = SH4Util.buildTransaction(request, bank, TransTypeEnum.PAY.getType(), serNo, errorCode);
            transactionService.saveTransaction(transaction);
        }


        ZLResponse zlResponse = new ZLResponse();
        zlResponse.setId("CPRes");
        zlResponse.setVersion("1.0.1");
        zlResponse.setInstId(request.getMessage().getZLRequest().getInstId());
        zlResponse.setOrderNum(request.getMessage().getZLRequest().getOrderNum());
        zlResponse.setSignNo(request.getMessage().getZLRequest().getSignNo());
        zlResponse.setSerialNo("40201705101308306721");
        zlResponse.setDate("20170510 17:40:45");
        zlResponse.setErrorCode(errorCode);

        Message message = new Message();
        message.setId(request.getMessage().getId());
        message.setZLResponse(zlResponse);
        message.setSignature("jVwc+GaIkwegjEx4YKWZljZ5QxBmBaaan1iUd/DJwLXk+YhLe/ccTXndMeke8ud6g7php52mjj8cP77ScnzSOdbg+Y7C1WcSWV8oREENESFyBcYRa9wztL6p4MuSpyI5T9wyZS+Z4+Gqn7hFNgjm1nTrStf2KzRvewC69EQ9198=");

        Banksh res = new Banksh();
        res.setMessage(message);

        String reqStr = XML_DECLARATION + parser.toXML(res);

        return reqStr;
    }


    private String processSQReqZL(Banksh request) {
        log.info("============ 上海直联明细查询开始 ============");
        List<TradeInfo> tradeInfoList = new ArrayList<>();
        String[] checkSerialNoList = request.getMessage().getZLRequest().getCheckSerialNoList().split("\\|");
        for (int i=0; i<checkSerialNoList.length; i++) {
            Transaction transaction = transactionService.findTransactionBySerNo4Query(checkSerialNoList[i]);
            String retCode = "0000";
            String retMsg = "";
            if(transaction == null){
                retCode = "0003";
                retMsg = "银行查无此订单";
            } else {
                if(!"succ".equals(transaction.getRespCode())){
                    if (transaction.getRespCode() == "3015"){//交易处理中
                        retCode = "0002";
                    }else {
                        retCode = "0000";//交易失败
                    }
                } else {
                    retCode = "0001";//交易成功
                }

            }

            TradeInfo tradeInfo = new TradeInfo();
            tradeInfo.setSerialNo(transaction.getBeSer());
            tradeInfo.setDate("20170510 17:40:36");
            tradeInfo.setSignNo("100036302000BBE672A71AF68544F81E45077598876E0");
            tradeInfo.setAmount(transaction.getAmount());
            tradeInfo.setCharge("");
            tradeInfo.setCurrency("156");
            tradeInfo.setCardType("1");
            tradeInfo.setOriginalSerialNo("");
            tradeInfo.setOriginalDate("");
            tradeInfo.setStatus(retCode);
            tradeInfoList.add(tradeInfo);
        }


        ZLResponse zlResponse = new ZLResponse();
        zlResponse.setId("TQRes");
        zlResponse.setVersion("1.0.1");
        zlResponse.setInstId(request.getMessage().getZLRequest().getInstId());
        zlResponse.setSerialNo(request.getMessage().getZLRequest().getSerialNo());
        zlResponse.setDate(request.getMessage().getZLRequest().getDate());
        zlResponse.setType("1");
        zlResponse.setBeginDate(request.getMessage().getZLRequest().getBeginDate());
        zlResponse.setEndDate(request.getMessage().getZLRequest().getEndDate());
        zlResponse.setCheckSerialNoList(request.getMessage().getZLRequest().getCheckSerialNoList());
        zlResponse.setTradeInfoList(tradeInfoList);
        zlResponse.setErrorCode("succ");


          Message  message = new Message();
          message.setId(request.getMessage().getId());
          message.setZLResponse(zlResponse);

        Banksh res = new Banksh();
        res.setMessage(message);

        String respStr = XML_DECLARATION + parser.toXML(res);
        return respStr;


    }






    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }
}
