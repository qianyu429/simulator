package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.mapping.cmb.CMBPack;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.CMBService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.TransactionService;
import com.shhxzq.bs.util.CmbUtil;
import com.shhxzq.bs.util.DateUtil;
import com.shhxzq.bs.util.StreamUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import lombok.extern.log4j.Log4j2;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by houjiagang on 2016/10/27.
 */
@Component
@Log4j2
public class CMBServiceImpl implements CMBService {

    private static final String ENCODING = "GBK";

    private XStream parser;

    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n";

    @Autowired
    private BankService bankService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private TransactionService transactionService;


    private static final Pattern PATTERN_REPLACEMENT;

    static {
        PATTERN_REPLACEMENT = Pattern.compile("(?<=<FUNNAM>)(.*?)(?=</FUNNAM>)");
    }


    @Override
    public String handle(HttpServletRequest request) throws Exception {
        //init xstream
        parser = getXStreamInstance();
        parser.processAnnotations(CMBPack.class);
        byte[] requestBody = StreamUtil.readBytes(request.getInputStream());
        String reqStr = new String(requestBody, ENCODING);
        log.info("Receive the request: {}", reqStr);
        String respStr = convertResponse(reqStr);
        log.info("Response is {}", respStr);

        return respStr;
    }


    private String convertResponse(String reqStr) {
        Matcher matcher = PATTERN_REPLACEMENT.matcher(reqStr);
        if (!matcher.find()) {
            throw new UnsupportedOperationException("The FUNNAM cannot be found in request xml!");
        }
        String transCode = matcher.group(0);
        switch (transCode) {
            case "AgentRequest":
                return processRedeem(reqStr);
            case "GetAgentInfo":
                return processRedeemQuery(reqStr);
        }
        return null;

    }

    private String processRedeem(String reqString) {
        log.info("============ 招行银企赎回开始 ============");
        String serNo = configService.getSerNo();
        CMBPack cmbPack = (CMBPack) parser.fromXML(reqString);
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CMB);
        Config config = configService.findBankSelectConfig(bank.getCode(), "redeem");
        String retCode = "S";
        if (config != null) {
            retCode = config.getK();
            log.info("强制改变赎回响应码为: {}", retCode);
        } else {
            log.info("不强制改变申购响应码, 正常赎回...");
        }

        Transaction tran = transactionService.findTransactionBySerNo(cmbPack.getSdkatsrqx().getYurRef());
        if (tran != null) {
            log.info("重复交易,流水号为: {}", tran);
            retCode = "F";
        } else {
            log.info("交易不重复,生成交易并入库...");
            Transaction transaction = CmbUtil.buildTransaction(cmbPack, bank, TransTypeEnum.REDEEM.getType(), serNo, retCode);
            transactionService.saveTransaction(transaction);
        }
        //拼装response
        CMBPack.Info info = new CMBPack.Info();
        info.setDatTyp("2");
        info.setErrMsg("");
        info.setFunNam(cmbPack.getInfo().getFunNam());
        info.setLgnNam("PAY1");
        info.setRetCod("0");

        CMBPack.NTREQNBRY ntreqnbry = new CMBPack.NTREQNBRY();
        ntreqnbry.setReqNbr(serNo);
        ntreqnbry.setRsv50z("OPR");

        CMBPack response = new CMBPack();
        response.setInfo(info);
        response.setNtreqnbry(ntreqnbry);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;

    }


    private String processRedeemQuery(String reqStr){
        log.info("============ 招行银企赎回查询开始 ============");
        CMBPack cmbPack = (CMBPack) parser.fromXML(reqStr);
        Transaction transaction = transactionService.findTransactionBySerNo4Query(cmbPack.getSdkatsqyx().getYurRef());
        String retCode = "S";;
        String reqNbr = "";
        String accNbr = "";
        String amount = "";
        if (transaction != null) {
            retCode = transaction.getRespCode();
            reqNbr = transaction.getBsSer();
            accNbr = transaction.getAccoNo();
            amount = transaction.getAmount();
        } else {
            retCode = "F";
        }

        String serialNo = cmbPack.getSdkatsqyx().getYurRef();

        CMBPack.Info info = new CMBPack.Info();
        info.setFunNam(cmbPack.getInfo().getFunNam());
        info.setDatTyp(cmbPack.getInfo().getDatTyp());
        info.setRetCod("0");
        info.setErrMsg("");

        CMBPack.NTQATSQYZ ntqatsqyz = new CMBPack.NTQATSQYZ();
        ntqatsqyz.setReqNbr(reqNbr);
        ntqatsqyz.setBusCod(cmbPack.getSdkatsqyx().getBusCod());
        ntqatsqyz.setCbusCod("mockserver");
        ntqatsqyz.setBusMod("00001");
        ntqatsqyz.setOprDat(DateUtil.format8Date(new Date()));
        ntqatsqyz.setEptDat(DateUtil.format8Date(new Date()));
        ntqatsqyz.setEptTim("000000");
        ntqatsqyz.setBbkNbr("91");
        ntqatsqyz.setCbbkNbr("上海");
        ntqatsqyz.setAccNbr(accNbr);
        ntqatsqyz.setAccNam(accNbr);
        ntqatsqyz.setTrsNum("1");
        ntqatsqyz.setTotAmt(amount);
        ntqatsqyz.setSucNum("000001");
        ntqatsqyz.setSucAmt(amount);
        ntqatsqyz.setCccyNbr("人民币");
        ntqatsqyz.setTrsTyp("BYSA");
        ntqatsqyz.setCtrsTyp("test");
        ntqatsqyz.setNUsage("test");
        ntqatsqyz.setYurRef(serialNo);

        ntqatsqyz.setReqSta("FIN");
        ntqatsqyz.setRtnFlg(retCode);

        CMBPack response = new CMBPack();
        response.setInfo(info);
        response.setNtqatsqyz(ntqatsqyz);

        String respStr = XML_DECLARATION + parser.toXML(response);

        return respStr;
    }


    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }
}
