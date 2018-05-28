package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.mapping.icbc.*;
import com.shhxzq.bs.mapping.icbc2.Eb;
import com.shhxzq.bs.mapping.icbc2.Rd;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.ICBCService;
import com.shhxzq.bs.service.TransactionService;
import com.shhxzq.bs.util.Base64Util;
import com.shhxzq.bs.util.DateUtil;
import com.shhxzq.bs.util.IcbcUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by houjiagang on 16/7/26.
 */
@Component
@Log4j2
public class ICBCServiceImpl implements ICBCService {

    private static final String ENCODING = "GBK";

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"GB2312\"?>\n";

    private static final Pattern PATTERN_REPLACEMENT;

    private XStream parser;


    @Autowired
    private BankService bankService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private TransactionService transactionService;

    static {
        PATTERN_REPLACEMENT = Pattern.compile("(?<=<TransCode>)(.*?)(?=</TransCode>)");
    }

    @Override
    public String handle(HttpServletRequest request) throws Exception {
        //init xstream
        parser = getXStreamInstance();
        parser.processAnnotations(CMS.class);
        //receive request
        String reqStr = request.getParameter("reqData");
        if (!"<?".equals(reqStr.substring(0, 2))) {
            reqStr = Base64Util.base64Decode(reqStr, ENCODING);
        }
        log.info("Receive the request is {}", reqStr);
        //convert response by transCode
        String respStr = convertResponse(reqStr);
        respStr = respStr.replace("<querys>", "").replace("</querys>", "").replace("<querys/>", "");
        respStr = respStr.replace("<rds>", "").replace("</rds>", "").replace("<rds/>", "");
        log.info("Response is {}", respStr);
        return respStr;
    }

    private String convertResponse(String reqString) {
        Matcher matcher = PATTERN_REPLACEMENT.matcher(reqString);
        if (!matcher.find()) {
            throw new UnsupportedOperationException("Thre transCode cannot be found in request xml!");
        }
        String transCode = matcher.group(0);
        switch (transCode) {
            case "SZFH_SMSAPPLY":
                return processSMSAPPLY(reqString);
            case "SZFH_SMSCONFIRM":
                return processSMSCONFIRM(reqString);
            case "SZFH_RECFRPER":
                return processRECFRPER(reqString);
            case "SZFH_QRECFRPER":
                return processQRECFRPER(reqString);
            case "SZFH_DELPERAMT":
                return processDELPERAMT(reqString);
            case "PAYPER":
                return processPAYPER(reqString);
            case "QPAYPER":
                return processQPAYPER(reqString);
            case "QPAYPERTM":
                return processQPAYPERTM(reqString);
            case "PERDIS"://批扣,说明:controller中人为添加了一个>,此处去除最后一个>
                String retPERDIS = processPERDIS(reqString);
                String ret = retPERDIS.substring(0, retPERDIS.length() - 1);
                return ret;
            case "QPERDIS":
                String retQPERDIS = processQPERDIS(reqString);
                return retQPERDIS.substring(0, retQPERDIS.length() - 1);
            case "QPERDISTM":
                String retQPERDISTM = processQPERDISTM(reqString);
                return retQPERDISTM.substring(0, retQPERDISTM.length() - 1);
        }
        return null;
    }

    private String processQPERDIS(String reqString) {
        log.info("============ 工行银企批扣查询(PERDIS)开始 ============");
        XStream icbc2Parser;
        icbc2Parser = getXStreamInstance();
        icbc2Parser.processAnnotations(com.shhxzq.bs.mapping.icbc2.CMS.class);
        com.shhxzq.bs.mapping.icbc2.CMS cms = (com.shhxzq.bs.mapping.icbc2.CMS) icbc2Parser.fromXML(reqString);
        Transaction transaction = transactionService.findTransactionBySerNo4Query(cms.getEb().getPub().getFSeqno());
        String retCode = "";
        String retMsg = "";
        com.shhxzq.bs.mapping.icbc2.Out out = new com.shhxzq.bs.mapping.icbc2.Out();
        com.shhxzq.bs.mapping.icbc2.Pub pub = cms.getEb().getPub();
        if (transaction == null) {
            retCode = "6";
            retMsg = "流水号不存在";

        } else {
            log.info("查询的交易BeSer为: {}", transaction.getBeSer());
            retCode = transaction.getRespCode();
            if ("7".equals(retCode)) {
                retMsg = "处理成功";
            }
            out.setTotalAmt(transaction.getAmount());
            out.setRecAccNo(transaction.getAccoNo());
        }
        //拼装返回response
        Rd rd = new Rd();
        rd.setIRetCode("0");
        rd.setResult(retCode);
        rd.setIRetMsg(retMsg);
        List<Rd> rds = new ArrayList<Rd>();
        rds.add(rd);
        out.setRds(rds);
        out.setQryfSeqno(cms.getEb().getIn().getQryfSeqno());
        out.setQrySerialNo(cms.getEb().getPub().getFSeqno());
        out.setOnlBatF("1");
        pub.setRetCode("0");
        pub.setRetMsg("");
        Eb eb = new Eb();
        eb.setPub(pub);
        eb.setOut(out);
        com.shhxzq.bs.mapping.icbc2.CMS response = new com.shhxzq.bs.mapping.icbc2.CMS();
        response.setEb(eb);
        String respStr = XML_DECLARATION + icbc2Parser.toXML(response);
        return respStr;
    }


    public String processPERDIS(String reqString) {
        log.info("============ 工行银企批扣(PERDIS)开始 ============");
        XStream icbc2Parser;
        icbc2Parser = getXStreamInstance();
        icbc2Parser.processAnnotations(com.shhxzq.bs.mapping.icbc2.CMS.class);
        com.shhxzq.bs.mapping.icbc2.CMS cms = (com.shhxzq.bs.mapping.icbc2.CMS) icbc2Parser.fromXML(reqString);
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_ICBC);//工行编号
        Config config = configService.findBankSelectConfig(bank.getCode(), "predis");
        String retCode = "7";
        String retMsg = "处理成功";
        if (config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
        }
        Transaction transaction = new Transaction();
        transaction.setBeSer(cms.getEb().getPub().getFSeqno());// 基金公司流水号
        transaction.setMerId(bank.getMerId());// 商户号
        transaction.setBankNo(bank.getBankNo());// 银行编号
        transaction.setAccoNo(cms.getEb().getIn().getRd().getRecAccNo());// 交易账户(卡号)
        transaction.setTransType("predis");// 交易类型
        transaction.setAmount(cms.getEb().getIn().getTotalAmt());// 交易金额
        transaction.setRespCode(retCode);// 响应码: e.g. "0"
        transaction.setWorkDay(cms.getEb().getPub().getTranDate());// 工作日
        transaction.setSendTime(DateUtil.format14Date(new Date()));// 基金公司交易时间
        transactionService.saveTransaction(transaction);
        //拼装返回response
        Rd rd = new Rd();
        rd.setIRetMsg(retMsg);
        rd.setIRetCode("0");//返回码
        Rd requestRd = cms.getEb().getIn().getRd();
        rd.setIseqno(requestRd.getIseqno());
        rd.setPayAccNameCN(requestRd.getIseqno());
        rd.setPayAccNameEN(requestRd.getPayAccNameEN());
        rd.setPayBranch(requestRd.getPayBranch());
        rd.setPortno(requestRd.getPortno());
        rd.setContractNo(requestRd.getContractNo());
        rd.setCurrType(requestRd.getCurrType());
        rd.setPayAmt(requestRd.getPayAmt());
        rd.setUseCode(requestRd.getUseCode());
        rd.setUseCN(requestRd.getUseCN());
        rd.setEnSummary(requestRd.getEnSummary());
        rd.setPostScript(requestRd.getPostScript());
        rd.setSummary(requestRd.getSummary());
        rd.setRef(requestRd.getRef());
        rd.setOref(requestRd.getOref());
        rd.setErpsqn(requestRd.getErpsqn());
        rd.setBusCode(requestRd.getBusCode());
        rd.setResult(retCode);//响应码
        com.shhxzq.bs.mapping.icbc2.Out out = new com.shhxzq.bs.mapping.icbc2.Out();
        List<Rd> rds = new ArrayList<Rd>();
        rds.add(rd);
        out.setRds(rds);
        out.setOnlBatF(cms.getEb().getIn().getOnlBatF());
        out.setSettleMode(cms.getEb().getIn().getSettleMode());
        out.setRecAccNo(cms.getEb().getIn().getRecAccNo());
        out.setRecAccNameCN(cms.getEb().getIn().getRecAccNameCN());
        out.setRecAccNameEN(cms.getEb().getIn().getRecAccNameEN());
        out.setTotalAmt(cms.getEb().getIn().getTotalAmount());
        com.shhxzq.bs.mapping.icbc2.Pub pub = cms.getEb().getPub();
        pub.setRetCode("0");//交易返回码
        pub.setRetMsg("");
        Eb eb = new Eb();
        eb.setPub(pub);
        eb.setOut(out);
        com.shhxzq.bs.mapping.icbc2.CMS response = new com.shhxzq.bs.mapping.icbc2.CMS();
        response.setEb(eb);
        String respStr = XML_DECLARATION + icbc2Parser.toXML(response);
        return respStr;

    }

    private String processQPERDISTM(String reqString) {
        log.info("============ 工行银企批扣对账(QPAYPERTM)开始 ============");
        XStream icbc2Parser;
        icbc2Parser = getXStreamInstance();
        icbc2Parser.processAnnotations(com.shhxzq.bs.mapping.icbc2.CMS.class);
        com.shhxzq.bs.mapping.icbc2.CMS request = (com.shhxzq.bs.mapping.icbc2.CMS) icbc2Parser.fromXML(reqString);
        Example example = new Example(Transaction.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bankNo", AppConstants.BANK_NO_ICBC);
        criteria.andEqualTo("transType", TransTypeEnum.PREDIS.getType());
        String workDay = request.getEb().getIn().getBeginTime().substring(0, 8);
        criteria.andEqualTo("workDay", workDay);
        List<Transaction> transactions = transactionService.findTransatctionByExample(example);
        List<Rd> rds = new ArrayList<Rd>();
        for (Transaction tran : transactions) {
            Rd rd = new Rd();
            rd.setSerialNo(tran.getBsSer());
            rd.setOrderNo("1");
            rd.setFSeqno(tran.getBeSer());
            rd.setIseqno(tran.getBeSer());
            rd.setOnlBatF("1");
            rd.setSettleMode("0");
            rd.setCurrType("001");
            rd.setBusType("");
            rd.setPayAmt(tran.getAmount());
            rd.setRecAccNo(tran.getAmount());
            rd.setUseCN("");
            rd.setPostScript(tran.getBeSer());
            rd.setResult(tran.getRespCode());//返回码
            rd.setIRetCode("0");//返回码
            rd.setInstrRetCode("0");//返回码
            rds.add(rd);
        }

        com.shhxzq.bs.mapping.icbc2.Out out = new com.shhxzq.bs.mapping.icbc2.Out();
        out.setRds(rds);
        out.setBeginTime(request.getEb().getIn().getBeginTime());
        out.setEndTime(request.getEb().getIn().getEndTime());
        out.setResultType("");
        out.setNextTag("");
        out.setRepReserved1("");
        out.setRepReserved2("");
        out.setRetTotalNum(String.valueOf(transactions.size()));
        com.shhxzq.bs.mapping.icbc2.Pub pub = request.getEb().getPub();
        pub.setRetCode("0");//交易返回码
        pub.setRetMsg("");
        Eb eb = new Eb();
        eb.setPub(pub);
        eb.setOut(out);
        com.shhxzq.bs.mapping.icbc2.CMS response = new com.shhxzq.bs.mapping.icbc2.CMS();
        response.setEb(eb);
        String respStr = XML_DECLARATION + icbc2Parser.toXML(response);
        return respStr;


    }


    private String processQPAYPERTM(String reqString) {
        log.info("============ 工行银企赎回对账(QPAYPERTM)开始 ============");
        CMS request = (CMS) parser.fromXML(reqString);
        String workDay = request.getEBody().getIn().getBeginTime();
        Example example = new Example(Transaction.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bankNo", "885");
        criteria.andEqualTo("transType", "redeem");
        criteria.andEqualTo("workDay", workDay.substring(0, 8));
        List<Transaction> transactions = transactionService.findTransatctionByExample(example);
        List<Query> querys = new ArrayList<Query>();
        for (Transaction tran : transactions) {
            Query query = new Query();
            query.setSerialNo(tran.getBsSer());
            query.setOrderNo("1");
            query.setFSeqno(tran.getBeSer());
            query.setISeqno(tran.getBeSer());
            query.setOnlBatF("1");
            query.setSettleMode("0");
            query.setPayType("1");
            query.setAccNo("1234567890123456789");// 写死19位
            query.setAccNameCN(tran.getAccoNo());
            query.setRecAccNo(tran.getAccoNo());
            query.setRecAccNameCN(tran.getAccoNo());
            query.setSysIOFlg("1");
            query.setIsSameCity("1");
            query.setRecICBCCode("4000");
            query.setRecBankName("1234");
            query.setCurrType("001");
            query.setPayAmt(tran.getAmount());
            query.setUseCN("mock");
            query.setPostScript(tran.getBeSer());
            query.setIRetCode("0");
            query.setResult(tran.getRespCode());
            query.setBankRetTime(tran.getSendTime());
            // 7代表成功, 工作对账用的这个值
            if (tran.getRespCode().equals("7")) {
                query.setInstrRetCode("0");
            } else {
                query.setInstrRetCode(tran.getRespCode());
            }
            String msg = "成功";
            if (!"Y".equals(tran.getStat())) {
                msg = "失败";
            }
            query.setInstrRetMsg(msg);
            querys.add(query);
        }

        Out out = new Out();
        out.setBeginTime(request.getEBody().getIn().getBeginTime());
        out.setEndTime(request.getEBody().getIn().getEndTime());
        out.setResultType("");
        out.setNextTag("");
        out.setRepReserved1("");
        out.setRepReserved2("");
        out.setQuerys(querys);
        Pub pub = request.getEBody().getPub();
        pub.setRetCode("");
        pub.setRetMsg("");
        EBody eBody = new EBody();
        eBody.setPub(pub);
        eBody.setOut(out);
        CMS response = new CMS();
        response.setEBody(eBody);
        String respStr = XML_DECLARATION + parser.toXML(response);
        return respStr;


    }


    private String processQPAYPER(String reqString) {
        log.info("============ 工行银企赎回查询(QPAYPER)开始 ============");
        CMS request = (CMS) parser.fromXML(reqString);
        Transaction transaction = transactionService.findTransactionBySerNo4Query(request.getEBody().getIn().getQryfSeqno());
        log.info("查询的交易为: {}", transaction);
        String retCode = "7";
        String retMsg = "交易成功";
        if (transaction == null) {
            retCode = "6";
            retMsg = "流水号不存在";
            transaction = new Transaction();
            transaction.setAccoNo("");
            transaction.setAmount("");
        } else {
            retCode = transaction.getRespCode();
        }
        Config config = configService.findConfigByGrpAndK("icbc-code-redeem", retCode);
        if (config == null) {
            config = new Config();
            config.setVal("流水号不存在");
        }
        if (!"7".equals(retCode)) {
            retMsg = config.getVal();
        }

        //拼装返回response
        Query rd = new Query();
        rd.setISeqno("1");
        rd.setQryiSeqno("1");
        rd.setQryOrderNo("1");
        rd.setPayType("1");
        rd.setPayAccNo("4000023029200124946");
        rd.setPayAccNameCN("mock");
        rd.setRecAccNo(transaction.getAccoNo());
        rd.setRecAccNameCN(transaction.getAccoNo());
        rd.setSysIOFlg("1");
        rd.setIsSameCity("1");
        rd.setRecICBCCode("4000");
        rd.setCurrType("001");
        rd.setPayAmt(transaction.getAmount());
        rd.setUseCode("22");
        rd.setUseCN("基金赎回");
        rd.setBusCode("953");
        rd.setIRetCode("0");
        rd.setResult(retCode);
        rd.setInstrRetCode("0");
        rd.setInstrRetMsg(retMsg);
        rd.setBankRetTime(RandomStringUtils.randomNumeric(14));
        List<Query> rds = new ArrayList<Query>();
        rds.add(rd);

        Out out = new Out();
        out.setQryfSeqno(request.getEBody().getIn().getQryfSeqno());
        out.setQrySerialNo(request.getEBody().getPub().getFSeqno());
        out.setOnlBatF("1");
        out.setBusType("");
        out.setRepReserved1("");
        out.setRepReserved2("");

        out.setQuerys(rds);

        Pub pub = request.getEBody().getPub();
        pub.setRetCode("0");
        pub.setRetMsg("");

        EBody eBody = new EBody();
        eBody.setPub(pub);
        eBody.setOut(out);

        CMS response = new CMS();
        response.setEBody(eBody);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;
    }

    private String processPAYPER(String reqString) {
        log.info("============ 工行银企赎回(PAYPER)开始 ============");
        String serNo = configService.getSerNo();
        CMS request = (CMS) parser.fromXML(reqString);
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_ICBC);
        Config config = configService.findBankSelectConfig(bank.getCode(), "redeem");
        String retCode = "7";
        String retMsg = "处理成功";
        if (config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
            log.info("强制改变赎回响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变赎回响应码, 正常赎回...");
        }
        Transaction tran = transactionService.findTransactionBySerNo(request.getEBody().getPub().getFSeqno());
        if (tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "7";
            retMsg = "重复交易";
        } else {
            log.info("交易不重复,生成交易并入库...");
            Transaction transaction = IcbcUtil.buildRedeemTransaction(request, bank, TransTypeEnum.REDEEM.getType(), serNo, retCode);
            transactionService.saveTransaction(transaction);
        }
        //拼装response
        Pub pub = request.getEBody().getPub();
        pub.setRetCode("0");
        pub.setRetMsg("成功");

        Query rd = request.getEBody().getIn().getQuery();
        rd.setOrderNo("1");
        rd.setResult(retCode);
        rd.setIRetCode("0");
        rd.setIRetMsg(retMsg);
        List<Query> rds = new ArrayList<Query>();
        rds.add(rd);

        Out out = new Out();
        out.setOnlBatF("1");
        out.setSettleMode("0");
        out.setTotalNum("1");
        out.setTotalAmount(request.getEBody().getIn().getTotalAmount());
        out.setRepReserved1("");
        out.setRepReserved2("");
        out.setQuerys(rds);

        EBody eBody = new EBody();
        eBody.setPub(pub);
        eBody.setOut(out);

        CMS response = new CMS();
        response.setEBody(eBody);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;
    }

    private String processDELPERAMT(String reqString) {
        log.info("============ 工行快捷解约(SZFH_DELPERAMT)开始 ============");
        CMS request = (CMS) parser.fromXML(reqString);
        //拼装response
        Out out = new Out();
        out.setCorpAccNo(request.getEBody().getIn().getCorpAccNo());
        out.setAccNo("");
        out.setAccName("");
        out.setMobilePhone("");
        out.setCorpNo(request.getEBody().getIn().getCorpNo());
        out.setPersonNo(request.getEBody().getIn().getPersonNo());
        out.setReqReserved1("");
        out.setReqReserved2("");
        out.setReqReserved3("");
        out.setReqReserved4("");
        out.setBankSeq("");
        out.setDelTime("");
        out.setRepReserved1("");
        out.setRepReserved2("");
        out.setRepReserved3("");
        out.setRepReserved4("");

        String retCode = "00000";
        String retMsg = "交易成功";
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_ICBC);
        Config config = configService.findBankSelectConfig(bank.getCode(), TransTypeEnum.CANCEL.getType());
        if (config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
        }
        Pub pub = request.getEBody().getPub();
        pub.setRetCode(retCode);
        pub.setRetMsg(retMsg);
        EBody eBody = new EBody();
        eBody.setPub(pub);
        eBody.setOut(out);

        CMS response = new CMS();
        response.setEBody(eBody);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;

    }


    private String processQRECFRPER(String reqString) {
        CMS request = (CMS) parser.fromXML(reqString);
        if ("".equals(request.getEBody().getIn().getQryfSeqno())) {
            return processDz(request);
        } else {
            return processQuery(request);
        }

    }

    private String processDz(CMS request) {
        log.info("============ 工行快捷申购对账(SZFH_QRECFRPER)开始 ============");
        String workDay = request.getEBody().getIn().getBeginDate();
        Example example = new Example(Transaction.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bankNo", "885");
        criteria.andEqualTo("transType", "pay");
        criteria.andEqualTo("workDay", workDay);
        List<Transaction> transactions = transactionService.findTransatctionByExample(example);
        List<Query> querys = new ArrayList<Query>();
        for (Transaction tran : transactions) {
            Query query = new Query();
            query.setBankSeq(tran.getBsSer());
            query.setCurfSeqno(tran.getBeSer());
            query.setPcode("MPREPAY_PAY");
            query.setUserAcc("1234567890123456789");// 写死19位
            query.setUserName(tran.getAccoNo());
            query.setIdType("0");
            query.setIdCode("12345678910");
            query.setMobilePhone("12345678910");
            query.setTotalAmount(tran.getAmount());
            query.setCurrType("001");
            query.setSummary("");
            String status = "2";
            if (!"Y".equals(tran.getStat())) {
                status = "3";
            }
            query.setStatus(status);
            query.setBankRem("");
            query.setRepReserved1("BDP800008");
            query.setRepReserved2("CM103432016060115");
            query.setRepReserved3("");
            query.setRepReserved4("");
            querys.add(query);
        }

        Out out = new Out();
        out.setCorpAccNo(request.getEBody().getIn().getCorpAccNo());
        out.setBeginDate(request.getEBody().getIn().getBeginDate());
        out.setEndDate(request.getEBody().getIn().getEndDate());
        out.setQryfSeqno("");
        out.setQryBankSeq("");
        out.setNextTag("");
        out.setReqReserved1("");
        out.setReqReserved2("");
        out.setReqReserved3("");
        out.setReqReserved4("");
        out.setQuerys(querys);

        Pub pub = request.getEBody().getPub();
        pub.setRetCode("00000");
        pub.setRetMsg("交易成功");

        EBody eBody = new EBody();
        eBody.setPub(pub);
        eBody.setOut(out);

        CMS response = new CMS();
        response.setEBody(eBody);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;

    }

    private String processQuery(CMS request) {
        log.info("============ 工行快捷申购查询(SZFH_QRECFRPER)开始 ============");
        Transaction transaction = transactionService.findTransactionBySerNo4Query(request.getEBody().getIn().getQryfSeqno());
        log.info("查询的交易为: {}", transaction);

        //拼装返回response
        Query rd = new Query();
        rd.setBankSeq("");
        rd.setCurfSeqno("");
        rd.setPcode("");
        rd.setUserAcc("");
        rd.setUserName("");
        rd.setIdType("");
        rd.setIdCode("");
        rd.setMobilePhone("");
        rd.setTotalAmount("");
        rd.setCurrType("");
        rd.setSummary("");
        String status = "3";
        if ("Y".equals(transaction.getStat())) {
            status = "2";
        } else if ("I".equals(transaction.getStat())) {
            status = "4";
        }
        rd.setStatus(status);
        rd.setBankRem("");
        rd.setRepReserved1("");
        rd.setRepReserved2("");
        rd.setRepReserved3("");
        rd.setRepReserved4("");
        List<Query> rds = new ArrayList<Query>();
        rds.add(rd);

        Out out = new Out();
        out.setCorpAccNo(request.getEBody().getIn().getCorpAccNo());
        out.setBeginDate(request.getEBody().getIn().getBeginDate());
        out.setEndDate(request.getEBody().getIn().getEndDate());
        out.setQryfSeqno(request.getEBody().getIn().getQryfSeqno());
        out.setQryBankSeq(request.getEBody().getIn().getQryBankSeq());
        out.setNextTag(request.getEBody().getIn().getNextTag());
        out.setReqReserved1("");
        out.setReqReserved2("");
        out.setReqReserved3("");
        out.setReqReserved4("");


        out.setQuerys(rds);

        Pub pub = request.getEBody().getPub();
        pub.setRetCode("00000");
        pub.setRetMsg("交易成功");

        EBody eBody = new EBody();
        eBody.setPub(pub);
        eBody.setOut(out);

        CMS response = new CMS();
        response.setEBody(eBody);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;

    }


    private String processRECFRPER(String reqString) {
        log.info("============ 工行快捷申购(SZFH_RECFRPER)开始 ============");
        String serNo = configService.getSerNo();
        CMS request = (CMS) parser.fromXML(reqString);
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_ICBC);
        Config config = configService.findBankSelectConfig(bank.getCode(), "pay");
        String retCode = "00000";
        String retMsg = "交易成功";
        if (config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
            log.info("强制改变申购响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变申购响应码, 正常申购...");
        }
        Transaction tran = transactionService.findTransactionBySerNo(request.getEBody().getPub().getFSeqno());
        if (tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "00100";
            retMsg = "重复交易";
        } else {
            log.info("交易不重复,生成交易并入库...");
            Transaction transaction = IcbcUtil.buildTransaction(request, bank, TransTypeEnum.PAY.getType(), serNo, retCode);
            transactionService.saveTransaction(transaction);
        }
        //拼装response
        Pub pub = request.getEBody().getPub();
        pub.setRetCode(retCode);
        pub.setRetMsg(retMsg);

        Out out = new Out();
        out.setCorpAccNo(request.getEBody().getIn().getCorpAccNo());
        out.setPcode(request.getEBody().getIn().getPcode());
        out.setUserAcc(request.getEBody().getIn().getUserAcc());
        out.setUserName(request.getEBody().getIn().getUserName());
        out.setIdType(request.getEBody().getIn().getIdType());
        out.setIdCode(request.getEBody().getIn().getIdCode());
        out.setMobilePhone(request.getEBody().getIn().getMobilePhone());
        out.setTotalAmount(request.getEBody().getIn().getTotalAmount());
        out.setCurrType(request.getEBody().getIn().getCurrType());
        out.setSummary(request.getEBody().getIn().getSummary());
        out.setUserinfo1("");
        out.setUserinfo2("");
        out.setUserinfo3("");
        out.setUserinfo4(request.getEBody().getIn().getUserinfo4());
        out.setUserinfo5(request.getEBody().getIn().getUserinfo5());
        out.setUserinfo6(request.getEBody().getIn().getUserinfo6());
        out.setUserinfo7(request.getEBody().getIn().getUserinfo7());
        out.setUserinfo8("");
        out.setUserinfo9("");
        out.setUserinfo10("");
        out.setReqReserved1(request.getEBody().getIn().getReqReserved1());
        out.setReqReserved2(request.getEBody().getIn().getReqReserved2());
        out.setReqReserved3(request.getEBody().getIn().getReqReserved3());
        out.setReqReserved4(request.getEBody().getIn().getReqReserved4());
        out.setBankSeq(RandomStringUtils.randomNumeric(20));
        out.setHostDate("");
        out.setRepReserved1("");
        out.setRepReserved2("");
        out.setRepReserved3("");
        out.setRepReserved4("");

        EBody eBody = new EBody();
        eBody.setPub(pub);
        eBody.setOut(out);

        CMS response = new CMS();
        response.setEBody(eBody);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;
    }

    private String processSMSCONFIRM(String reqString) {
        log.info("============ 工行快捷鉴权(SZFH_SMSCONFIRM)开始 ============");
        CMS request = (CMS) parser.fromXML(reqString);
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_ICBC);
        Config config = configService.findBankSelectConfig(bank.getCode(), "verify");
        String retCode = "1";
        String retMsg = "成功";
        if (config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
            log.info("强制改变鉴权响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常鉴权...");
        }
        //拼装response
        Pub pub = request.getEBody().getPub();
        pub.setRetCode("00000");
        pub.setRetMsg("交易成功");

        Out out = new Out();
        out.setCorpAccNo(request.getEBody().getIn().getCorpAccNo());
        out.setCorpNo(request.getEBody().getIn().getCorpNo());
        out.setPersonNo(request.getEBody().getIn().getPersonNo());
        out.setSmsSendNo(request.getEBody().getIn().getSmsSendNo());
        out.setVeriCode("");
        out.setReqReserved1("");
        out.setReqReserved2("");
        out.setReqReserved3("");
        out.setReqReserved4("");
        out.setBankSeq("");
        out.setVfRetCode(retCode);
        out.setVfRetMsg(retMsg);
        out.setRepReserved1("");
        out.setRepReserved2("");
        out.setRepReserved3("");
        out.setRepReserved4("");

        EBody eBody = new EBody();
        eBody.setPub(pub);
        eBody.setOut(out);

        CMS response = new CMS();
        response.setEBody(eBody);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;
    }

    private String processSMSAPPLY(String reqString) {
        log.info("============ 工行快捷发短信(SZFH_SMSAPPLY)开始 ============");
        CMS request = (CMS) parser.fromXML(reqString);
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_ICBC);
        Config config = configService.findBankSelectConfig(bank.getCode(), "sms");
        String retCode = "00000";
        String retMsg = "交易成功";
        if (config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
            log.info("强制改变短信响应码为: {} - {}", retCode, retMsg);
        } else {
            log.info("不强制改变响应码, 正常发短信...");
        }
        //拼装response
        Pub pub = request.getEBody().getPub();
        pub.setRetCode(retCode);
        pub.setRetMsg(retMsg);

        Out out = new Out();
        out.setCorpAccNo(request.getEBody().getIn().getCorpAccNo());
        out.setAccNo(request.getEBody().getIn().getAccNo());
        out.setSupType("0");
        out.setAccName(request.getEBody().getIn().getAccName());
        out.setIdType(request.getEBody().getIn().getIdType());
        out.setIdCode(request.getEBody().getIn().getIdCode());
        out.setMobilePhone(request.getEBody().getIn().getMobilePhone());
        out.setCorpNo(request.getEBody().getIn().getCorpNo());
        out.setPersonNo(request.getEBody().getIn().getPersonNo());
        out.setDeadLine(request.getEBody().getIn().getDeadLine());
        out.setReqReserved1("");
        out.setReqReserved2("");
        out.setReqReserved3("");
        out.setReqReserved4("");
        out.setBankSeq(RandomStringUtils.randomNumeric(20));
        out.setAccType("3");
        out.setAreaCode("4000");
        out.setSmsSendFlag("");
        out.setSmsSendNo(RandomStringUtils.randomNumeric(8));
        out.setRepReserved1("");
        out.setRepReserved2("");
        out.setRepReserved3("");
        out.setRepReserved4("");

        EBody eBody = new EBody();
        eBody.setPub(pub);
        eBody.setOut(out);

        CMS response = new CMS();
        response.setEBody(eBody);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;
    }


    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }
}
