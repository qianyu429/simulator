package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.CmbcT0Service;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by wanglili on 17/1/3.
 */
@Service
@Log4j2
public class CmbcT0ServiceImpl implements CmbcT0Service {

    @Autowired
    ConfigService configService;

    @Autowired
    BankService bankService;

    @Autowired
    TransactionService transactionService;

    public String redeem(Map<String, String> params) {

        log.info("进入民生T+0代付");

        //读取配置信息
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CMBCT0);
        Config config = configService.findBankSelectConfig(bank.getCode(), "redeem");
        log.info("民生T+0代付的配置:" + config);

        //商户编号
        String companyNo = bank.getMerId();

        //生成银行交易日期BANK_TRAN_DATE
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String bankTranDate = df.format(now);

        //生成银行处理流水号BANK_TRAN_ID
        String bankTranId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        String tranDate = params.get("TRAN_DATE");
        String tranId = params.get("TRAN_ID");
        String tranTime = params.get("TRAN_TIME");
        String tranAcco = params.get("PAY_ACCT_NO");
        String tranAmt = params.get("TRANS_AMT");
        String workDay = params.get("COMPANY_DATE");

        log.info("根据交易响码设置交易状态");
        String code = "00";
        String status = "S";
        String tranStatus = "Y";
        if (config != null) {
            code = config.getK();

            if (code.equals("00")) {
                status = "S";
                tranStatus = "Y";
            } else if (code.equals("99") || code.equals("R1")) {
                status = "R";
                tranStatus = "I";
            } else {
                status = "E";
                tranStatus = "F";
            }

        }

        log.info("transaction saved to db...");
        Transaction tran = transactionService.findTransactionBySerNo(tranId);
        if (tran != null) {
            log.info("交易重复, 基金公司流水号为: {}", tran);
            code = "";
        } else {
            log.info("交易不重复, 生成交易并入库...");
            // 交易内容
            Transaction transaction = new Transaction();
            transaction.setBeSer(tranId);// 基金公司流水号
            transaction.setMerId(bank.getMerId());// 商户号
            transaction.setBsSer(bankTranId);// 银联方流水号
            transaction.setBankNo(bank.getBankNo());// 银行编号
            transaction.setAccoNo(tranAcco);// 交易账户(卡号)
            transaction.setTransType(TransTypeEnum.REDEEM.getType());// 交易类型
            transaction.setAmount(tranAmt);// 交易金额
            transaction.setRespCode(code);// 响应码: e.g. "00"
            transaction.setTransStat(tranStatus);// 状态码: e.g. "Y"
            transaction.setWorkDay(workDay);// 状态码: e.g. "Y"
            log.info("交易内容为: {}", transaction);

            // 交易入库
            transactionService.saveTransaction(transaction);
        }

        // 响应报文
        log.info("generate redeem response xml.");
        String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<TRAN_RESP>\n" +
                "<RESP_TYPE>" + status + "</RESP_TYPE>\n" +
                "<RESP_CODE>" + code + "</RESP_CODE>\n" +
                "<RESP_MSG></RESP_MSG>\n" +
                "<MCHNT_CD>" + companyNo + "</MCHNT_CD>\n" +
                "<TRAN_DATE>" + tranDate + "</TRAN_DATE>\n" +
                "<TRAN_TIME>" + tranTime + "</TRAN_TIME>\n" +
                "<TRAN_ID>" + tranId + "</TRAN_ID>\n" +
                "<BANK_TRAN_DATE>" + bankTranDate + "</BANK_TRAN_DATE>\n" +
                "<BANK_TRAN_ID>" + bankTranId + "</BANK_TRAN_ID>\n" +
                "<RESV>redeem</RESV>\n" +
                "</TRAN_RESP>";

        return result.replaceAll("\\n", "");
    }

    /**
     * 根据bankNO + workday + 交易类型 查找所有的交易，生成对账文件
     */

    /**
     * @param params
     * @return
     */
    public String query(Map<String, String> params) {

        //读取配置信息
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CMBCT0);

        //商户编号
        String companyNo = bank.getMerId();

        String tranDate = params.get("TRAN_DATE");
        String tranId = params.get("TRAN_ID");
        String tranTime = params.get("TRAN_TIME");
        String oriTranDate = params.get("ORI_TRAN_DATE");
        String oriTranId = params.get("ORI_TRAN_ID");
        String oriBankTranDate = "";
        String oriBankTranId = "";
        String oriBankMsg = "";

        String code = "00";
        //查询响应码类型
        String respType = "S";
        String oriStatus = "";
        String oriCode = "";

        log.info("query original transaction in DB.");
        Transaction tran = transactionService.findTransactionBySerNo4Query(oriTranId);

        if (tran != null) {
            String status = tran.getTransStat();
            oriCode = tran.getRespCode();

            if (status.equals("Y")) {
                oriStatus = "S";
            } else if (status.equals("I")) {
                oriStatus = "R";
            } else if (status.equals("F")) {
                oriStatus = "E";
            }

        } else {
            oriStatus = "R";
            oriCode = "R1";
        }

        // 响应报文
        log.info("generate query response xml.");
        String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<TRAN_RESP>\n" +
                "<RESP_TYPE>" + respType + "</RESP_TYPE>\n" +
                "<RESP_CODE>" + code + "</RESP_CODE>\n" +
                "<RESP_MSG></RESP_MSG>\n" +
                "<MCHNT_CD>" + companyNo + "</MCHNT_CD>\n" +
                "<TRAN_DATE>" + tranDate + "</TRAN_DATE>\n" +
                "<TRAN_TIME>" + tranTime + "</TRAN_TIME>\n" +
                "<TRAN_ID>" + tranId + "</TRAN_ID>\n" +
                "<ORI_TRAN_DATE>" + oriTranDate + "</ORI_TRAN_DATE>\n" +
                "<ORI_TRAN_ID>" + oriTranId + "</ORI_TRAN_ID>\n" +
                "<ORI_BANK_TRAN_DATE>" + oriBankTranDate + "</ORI_BANK_TRAN_DATE>\n" +
                "<ORI_BANK_TRAN_ID>" + oriBankTranId + "</ORI_BANK_TRAN_ID>\n" +
                "<ORI_RESP_TYPE>" + oriStatus + "</ORI_RESP_TYPE>\n" +
                "<ORI_RESP_CODE>" + oriCode + "</ORI_RESP_CODE>\n" +
                "<ORI_RESP_MSG>" + oriBankMsg + "</ORI_RESP_MSG>\n" +
                "<RESV>query</RESV>\n" +
                "</TRAN_RESP>";

        return result;
    }

}
