package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.Spdb6THService;
import com.shhxzq.bs.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zuodongxiang on 17/5/8.
 */
@Component
@Log4j2
public class Spdb6THServiceImpl implements Spdb6THService {

    @Autowired
    private ConfigService configService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BankService bankService;


    @Override
    public String payTongHang(Map<String, String> params) {
        log.info("============ 受托支付同行单笔扣款开始 ============");
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_SPDB6);
        Config config = configService.findBankSelectConfig(bank.getCode(), "payTongHang");//查询代扣响应码
        String retCode = "AAAAAAA";
        String retMsg = "交易成功";
        if (config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
        }
        //对交易参数落库,此处不考虑流水号重复的情况
        String beSer = params.get("DSFSN");//交易流水号
        String transAmount = params.get("AMOUNT");//交易金额
        Transaction transaction = new Transaction();
        transaction.setAmount(transAmount);
        transaction.setBeSer(beSer);
        transaction.setBankNo(AppConstants.BANK_NO_SPDB6);
        transaction.setRespCode(retCode);
        transaction.setTransType("payTongHang");
        transaction.setMemo1(retMsg);//向预留字段中添加错误码描述
        transactionService.saveTransaction(transaction);
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<STP>\n" +
                "<HEAD>\n" +
                "<TRNCODE>GATHER</TRNCODE>\n" +
                "<VERSION>1.0</VERSION>\n" +
                "<RETCODE>" +
                retCode +
                "</RETCODE>\n" +
                "<ERRMSG>" +
                retMsg +
                "</ERRMSG>\n" +
                "<DSFSN>" + beSer +
                "</DSFSN>\n" +
                "<TRNSN>20170503000001609015</TRNSN>\n" +
                "<TRNDATE>20280317</TRNDATE>\n" +
                "</HEAD>\n" +
                "</STP>\n";
        return xml;
    }


    @Override
    public String redeemTongHang(Map<String, String> params) {
        log.info("============ 受托支付同行单笔付款开始 ============");
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_SPDB6);
        Config config = configService.findBankSelectConfig(bank.getCode(), "redeemTongHang");//查询代扣响应码
        String retCode = "AAAAAAA";
        String retMsg = "交易成功";
        if (config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
        }
        //对交易参数落库,此处不考虑流水号重复的情况
        String beSer = params.get("DSFSN");//交易流水号
        String transAmount = params.get("AMOUNT");//交易金额
        Transaction transaction = new Transaction();
        transaction.setAmount(transAmount);
        transaction.setBeSer(beSer);
        transaction.setBankNo(AppConstants.BANK_NO_SPDB6);
        transaction.setRespCode(retCode);
        transaction.setTransType("redeemTongHang");
        transaction.setMemo1(retMsg);//向预留字段中添加错误码描述
        transactionService.saveTransaction(transaction);
        String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?><STP><HEAD><TRNCODE>PAY</TRNCODE><VERSION>1.0</VERSION><RETCODE>" +
                retCode +
                "</RETCODE><ERRMSG>" +
                retMsg +
                "</ERRMSG><DSFSN>" + beSer +
                "</DSFSN><TRNSN>20170503000001609017</TRNSN><TRNDATE>20280317</TRNDATE></HEAD></STP>";
        return xml;
    }

    @Override
    public String queryTrade(Map<String, String> params) {
        log.info("============ 受托支付同行支付查询开始 ============");
        String beSer = params.get("DSFSN");//交易请求流水
        Transaction transaction = transactionService.findTransactionBySerNo4Query(beSer);
        String retCode;
        String retMsg;
        String transAmount = null;
        if (transaction == null) {
            retCode = "STP1402";
            retMsg = "查询签约商户信息失败";
            transAmount = "";
        } else {
            transAmount = transaction.getAmount();
            retCode = transaction.getRespCode();
            retMsg = transaction.getMemo1();
            if (transAmount == null) {
                transAmount = "";//如果没有查询到记录,置空
            }
        }
        String responseXml = "<?xml version=\"1.0\" encoding=\"GBK\"?><STP><HEAD><TRNCODE>QRYRST</TRNCODE><VERSION>1.0</VERSION><RETCODE>AAAAAAA</RETCODE><ERRMSG>查询成功</ERRMSG><DSFSN>" +
                beSer +
                "</DSFSN><TRNSN></TRNSN><TRNDATE></TRNDATE></HEAD><BODY><QDSFSN>" +
                beSer +
                "</QDSFSN><ACCCODE>6217934100005780</ACCCODE><ACCNAME>浦发1285803937</ACCNAME><BIZTYPE>redeem</BIZTYPE><MERNO>9900000351</MERNO><MERNAME>上海华信证券</MERNAME><AMOUNT>" +
                transAmount +
                "</AMOUNT><DSFTIME>151138</DSFTIME><ORETCODE>" +
                retCode +
                "</ORETCODE><OERRMSG>" +
                retMsg +
                "</OERRMSG><REMARK>华信现金宝</REMARK></BODY></STP>";
        return responseXml;
    }
}
