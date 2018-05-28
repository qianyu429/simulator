package com.shhxzq.bs.processer;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.mapping.pab.Result;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.TransactionService;
import com.shhxzq.bs.util.DateUtil;
import com.shhxzq.bs.utils.PABUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by houjiagang on 2016/10/31.
 */
@Component
@Log4j2
public class PABProcesser {

    private static final String ENCODING = "GBK";

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"GBK\"?>";

    private XStream parser;

    @Autowired
    private BankService bankService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private TransactionService transactionService;


    public String handle(String reqString, String transCode){
        //init xstream
        parser = getXStreamInstance();
        parser.processAnnotations(Result.class);
        //receive request
        reqString = reqString.substring(222);
        log.info("Receive the request is {}", reqString);
        String respStr = convertResponse(reqString, transCode);
        log.info("Response is {}", respStr);
        return respStr;


    }



    private String convertResponse(String reqString, String transCode){
        switch (transCode){
            case "400101":
                return process400101(reqString);
            case "4047":
                return process4047(reqString);
            case "4048":
                return process4048(reqString);
            case "4004":
                return process4004(reqString);
            case "4005":
                return process4005(reqString);
        }

        return null;

    }

    private String process400101(String reqString) {
        log.info("============ 平安快捷签约(400101)开始 ============");
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_PAB);
        Config config = configService.findBankSelectConfig(bank.getCode(), "verify");
        String retCode = "0";
        if(config != null){
            retCode = config.getK();
            log.info("强制改变鉴权响应码为: {}", retCode);
        } else {
            log.info("不强制改变响应码,正常鉴权...");
        }
        Result response = new Result();
        response.setFlag(retCode);
        response.setDesc("");



        String length = String.valueOf(36 + parser.toXML(response).length());
        String returnString = "A0010101010090107980000003600000000000"+ length + "400101     02201610251622032016102500050024    000000:交易受理成功                                                                                       000000            00000000000"
                + XML_DECLARATION + parser.toXML(response);


        try {
            byte[] temp = returnString.getBytes("GBK");
            returnString = new String(temp, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return returnString;

    }

    private String process4047(String reqString) {
        log.info("============ 平安快捷充值(4047)开始 ============");
        String serNo = configService.getSerNo();
        Result request = (Result) parser.fromXML(reqString);
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_PAB);
        Config config = configService.findBankSelectConfig(bank.getCode(), "pay");
        String retCode = "000000";
        if (config != null) {
            retCode = config.getK();
            log.info("强制改变充值响应码为: {}", retCode);
        } else {
            log.info("不强制改变响应码,正常充值...");
        }

        Transaction tran = transactionService.findTransactionBySerNo(request.getHoResultSet4047R().getSThirdVoucher());
        if (tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "EBLN00";
        } else {
            log.info("交易不重复,生成交易并入库...");
            Transaction transaction = PABUtil.buildTransaction(request, bank, TransTypeEnum.PAY.getType(), serNo, retCode);
            transactionService.saveTransaction(transaction);

        }
        Result.ResultList list = new Result.ResultList();
        list.setSThirdVoucher(request.getHoResultSet4047R().getSThirdVoucher());
        list.setOppAccNo(request.getHoResultSet4047R().getOppAccNo());
        list.setOppAccName(request.getHoResultSet4047R().getOppAccName());
        list.setAmount(request.getHoResultSet4047R().getAmount());
        list.setFee("");
        list.setStt(retCode);
        list.setPostScript("");
        list.setRemarkFCR("");
        list.setTraderCode("");

        Result response = new Result();
        response.setThirdVoucher(request.getThirdVoucher());
        response.setPayType("0");
        response.setSrcAccNo(request.getSrcAccNo());
        response.setTotalNum(request.getTotalNum());
        response.setTotalAmount(request.getTotalAmount());
        response.setBussFlowNo(RandomStringUtils.randomNumeric(22));
        response.setList(list);

        String length = String.valueOf(36 + parser.toXML(response).length());

        String respStr = "A001010101009010798000000360000000000" + length + "4047       02201610251338102016102500049940    " + retCode + ":交易受理成功                                                                                       000000            00000000000" +
                XML_DECLARATION + parser.toXML(response);
        try {
            byte[] temp = respStr.getBytes("GBK");
            respStr = new String(temp, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return respStr;

    }

    private String process4048(String reqString) {
        log.info("============ 平安快捷充值查询(4048)开始 ============");
        Result request = (Result) parser.fromXML(reqString);
        Transaction transaction = transactionService.findTransactionBySerNo4Query(request.getThirdVoucher());
        String retCode = "0000";
        if (transaction != null ){
            retCode = transaction.getRespCode();
        } else {
            retCode = "YQ9996";
            transaction = new Transaction();
            transaction.setAccoNo("");
            transaction.setAmount("");
        }

        Result.ResultList list = new Result.ResultList();
        list.setSThirdVoucher(request.getThirdVoucher());
        list.setCstInnerFlowNo("");
        list.setIdType("1");
        list.setIdNo("123426197905180526");
        list.setOppAccNo(transaction.getAccoNo());
        list.setOppAccName(transaction.getAccoNo());
        list.setAmount(transaction.getAmount());
        list.setStt(retCode);
        list.setSttInfo("");
        list.setPostScript("");
        list.setRemarkFCR("");
        list.setTraderCode("");

        Result response = new Result();
        response.setThirdVoucher(request.getThirdVoucher());
        response.setBussFlowNo(RandomStringUtils.randomNumeric(22));
        if("0000".equals(retCode)){
            response.setBstt("4");
        } else {
            response.setBstt("3");
        }
        response.setBusiType("M8YQD");
        response.setPayType("0");
        response.setCurrency("RMB");
        response.setOthBankFlag("");
        response.setSrcAccNo("11014501996009");
        response.setTotalNum("1");
        response.setTotalAmount(transaction.getAmount());
        response.setSettleType("0");
        response.setList(list);

        String length = String.valueOf(36 + parser.toXML(response).length());

        String returnString = "A001010101009010798000000360000000000" + length + "4048       02201610251338102016102500049940    000000:交易受理成功                                                                                       000000            00000000000"
                + XML_DECLARATION + parser.toXML(response);

        try {
            byte[] temp = returnString.getBytes("GBK");
            returnString = new String(temp, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return returnString;
    }


    private String process4004(String reqString) {
        log.info("============ 平安银企赎回(4004)开始 ============");
        String serNo = configService.getSerNo();
        Result request = (Result) parser.fromXML(reqString);
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_PAB);
        Config config = configService.findBankSelectConfig(bank.getCode(), "redeem");
        String retCode = "000000";
        if (config != null) {
            retCode = config.getK();
            log.info("强制改变赎回响应码为: {}", retCode);
        } else {
            log.info("不强制改变响应码,正常赎回...");
        }
        Transaction tran = transactionService.findTransactionBySerNo(request.getThirdVoucher());
        if(tran != null) {
            log.info("重复交易,流水号为: {}", tran);
        } else {
            log.info("交易不重复,生成交易并入库...");
            Transaction transaction = PABUtil.buildTransaction(request, bank, TransTypeEnum.REDEEM.getType(), serNo, retCode);
            transactionService.saveTransaction(transaction);
        }

        Result response = new Result();
        response.setThirdVoucher(request.getThirdVoucher());
        response.setFrontLogNo(RandomStringUtils.randomNumeric(22));
        response.setCcyCode("RMB");
        response.setOutAcctName(request.getOutAcctNo());
        response.setOutAcctNo(request.getOutAcctNo());
        response.setInAcctBankName(request.getInAcctNo());
        response.setInAcctNo(request.getInAcctNo());
        response.setInAcctName(request.getInAcctName());
        response.setTranAmount(request.getTranAmount());
        response.setUnionFlag(request.getUnionFlag());
        response.setFee1("0.00");
        response.setFee2("0.00");
        response.setSOA_VOUCHER("");
        response.setHostFlowNo(RandomStringUtils.randomNumeric(22));
        response.setHostTxDate(DateUtil.format8Date(new Date()));
        response.setMobile("");
        response.setCstInnerFlowNo("");


        String length = String.valueOf(36 + parser.toXML(response).length());
        String respStr = "A001010101009010798000000360000000000" + length + "4004       02201610251339482016102500049942    000000:交易受理成功                                                                                       000000            00000000000" +
                XML_DECLARATION + parser.toXML(response);

        try {
            byte[] temp = respStr.getBytes("GBK");
            respStr = new String(temp, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return respStr;


    }


    private String process4005(String reqString) {
        log.info("============ 平安银企赎回查询(4005)开始 ============");
        Result request = (Result) parser.fromXML(reqString);
        Transaction transaction = transactionService.findTransactionBySerNo4Query(request.getOrigThirdVoucher());
        String retCode = "000000";
        if(transaction != null) {
            retCode = transaction.getRespCode();
        } else {
            retCode = "MA9112";
        }
        Result response = new Result();
        response.setOrigThirdVoucher(request.getOrigThirdVoucher());
        response.setFrontLogNo(RandomStringUtils.randomNumeric(22));
        response.setCcyCode("RMB");
        response.setOutAcctBankName("");
        response.setOutAcctNo(transaction.getAccoNo());
        response.setInAcctBankName("mockserver");
        response.setInAcctNo("6029070100044038");
        response.setInAcctName("6029070100044038");
        response.setTranAmount(transaction.getAmount());
        response.setUnionFlag("1");
        response.setYhcljg(retCode);
        response.setFee("0.00");
        response.setCstInnerFlowNo("");
        if("000000".equals(retCode)){
            response.setStt("20");
        } else {
            response.setStt("30");
        }
        response.setIsBack("0");
        response.setBackRem("");
        response.setSysFlag("N");
        response.setTransBsn("4004");
        response.setSubmitTime(DateUtil.format14Date(new Date()));
        response.setAccountDate(DateUtil.format8Date(new Date()));
        response.setInAcctBankName("");
        response.setHostFlowNo(RandomStringUtils.randomNumeric(22));
        response.setHostErrorCode("000000");
        response.setKType("0");
        response.setDType("0");
        response.setSubBatchNo("");
        response.setProxyPayName("");
        response.setProxyPayAcc("");
        response.setProxyPayBankName("");

        String length = String.valueOf(36 + parser.toXML(response).length());

        String respStr = "A001010101009010798000000360000000000" + length + "4005       02201610251402112016102500049956    000000:交易受理成功                                                                                       000000            00000000000" +
                XML_DECLARATION + parser.toXML(response);

        try {
            byte[] temp = respStr.getBytes("GBK");
            respStr = new String(temp, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return respStr;
    }






    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }
}
