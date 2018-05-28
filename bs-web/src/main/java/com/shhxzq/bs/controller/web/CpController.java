package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.model.*;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.TransactionService;
import com.shhxzq.bs.util.CpUtil;
import com.shhxzq.bs.util.HttpUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 银联模拟器
 *
 * @author kangyonggan
 * @since 16/5/20
 */
@Controller
@RequestMapping("cp")
@Log4j2
public class CpController {

    @Autowired
    private ConfigService configService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BankService bankService;

    /**
     * 鉴权
     *
     * @param cpVerifyRequest
     * @return
     */
    @RequestMapping(value = "verify", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String verify(CpVerifyRequest cpVerifyRequest) {
        log.info("============ 鉴权开始 ============");
        log.info("请求报文: {}", cpVerifyRequest);
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CP);

        // 响应码
        Config config = configService.findBankSelectConfig(bank.getCode(), "verify");
        String code = "000";
        String val = "交易成功";
        if (config != null) {
            code = config.getK();
            val = config.getVal();
            log.info("强制改变鉴权响应码为: {} - {}", code, val);
        } else {
            log.info("不强制改变鉴权响应码, 正常鉴权...");
        }

        // 银联方流水号
        String serNo = configService.getSerNo();
        log.info("银联方流水号为: {}", serNo);

        Transaction tran = transactionService.findTransactionBySerNo(cpVerifyRequest.getEncMsg().split("\\|")[5]);
        if (tran != null) {
            log.info("交易重复, 基金公司流水号为: {}", tran);
            code = "094";
            val = "重复交易";
        } else {
            log.info("交易不重复, 生成交易并入库...");
            // 交易内容
            Transaction transaction = CpUtil.verifyRequestToTransaction(cpVerifyRequest, bank, code, "", serNo);
            log.info("交易内容为: {}", transaction);

            // 交易入库
            transactionService.saveTransaction(transaction);
        }

        if ("000".equals(code)) {
            log.info("鉴权成功, 异步通知be...{}", code);
            asyncNoticeBe(cpVerifyRequest, code, bank.getCode());
        } else if (code.startsWith("000_")) {
            log.info("鉴权成功, 异步通知be...{}", code);
            asyncNoticeBe(cpVerifyRequest, code.substring(4), bank.getCode());
        } else {
            log.info("鉴权失败, 不异步通知be...{}", code);
        }

        // 返回主体
        String respCodeMsg = CpUtil.buildVerifyRespCodeMsg(cpVerifyRequest, code, val, serNo);
        log.info("返回主体: {}", respCodeMsg);

        // 返回签名
        String sign = respCodeMsg;
        log.info("返回签名: {}", sign);

        // 返回报文
        String msg = CpUtil.buildVerifyMsg(cpVerifyRequest, respCodeMsg, sign);
        log.info("返回报文: {}", msg);

        log.info("============ 鉴权结束 ============");
        return msg;
    }

    /**
     * 申购(代扣)
     *
     * @param cpPayRequest
     * @return
     */
    @RequestMapping(value = "pay", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String pay(CpPayRequest cpPayRequest) {
        log.info("============ 申购开始 ============");
        log.info("请求报文: {}", cpPayRequest);
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CP);

        // 响应码
        Config config = configService.findBankSelectConfig(bank.getCode(), "pay");
        String code = "00";
        String val = "响应成功";
        if (config != null) {
            code = config.getK();
            val = config.getVal();
            log.info("强制改变申购响应码为: {} - {}", code, val);
        } else {
            log.info("不强制改变申购响应码, 正常申购...");
        }

        // 状态码
        Config configStat = configService.findBankSelectConfig(bank.getCode(), "payStat");
        String stat = "1001";
        String valStat = "交易成功";
        if (configStat != null) {
            stat = configStat.getK();
            valStat = configStat.getVal();
            log.info("强制改变申购状态码为: {} - {}", stat, val);
        } else {
            log.info("不强制改变申购状态码, 正常申购...");
        }

        // 银联方流水号
        String serNo = configService.getSerNo();
        log.info("银联方流水号为: {}", serNo);

        Transaction tran = transactionService.findTransactionBySerNo(cpPayRequest.getOrderNo());
        if (tran != null) {
            log.info("交易重复, 基金公司流水号为: {}", tran);
            code = "94";
            val = "重复业务";
            stat = "2094";
            valStat = "重复业务";
        } else {
            log.info("交易不重复, 生成交易并入库...");
            // 交易内容
            Transaction transaction = CpUtil.payRequestToTransaction(cpPayRequest, bank, code, stat, serNo);
            log.info("交易内容为: {}", transaction);

            // 交易入库
            transactionService.saveTransaction(transaction);
        }

        // 返回主体
        String body = CpUtil.buildPayBody(cpPayRequest, code, stat, valStat);
        log.info("返回主体: {}", body);

        // 返回报文
        String msg = CpUtil.buildPayMsg(body, code, stat);
        log.info("返回报文: {}", msg);

        log.info("============ 申购结束 ============");
        return msg;
    }

    /**
     * 赎回(代付)
     *
     * @param cpRedeemRequest
     * @return
     */
    @RequestMapping(value = "redeem", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String redeem(CpRedeemRequest cpRedeemRequest) {
        log.info("============ 赎回开始 ============");
        log.info("请求报文: {}", cpRedeemRequest);
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CP);

        // 响应码
        Config config = configService.findBankSelectConfig(bank.getCode(), "redeem");
        String code = "0000";
        String val = "响应成功";
        if (config != null) {
            code = config.getK();
            val = config.getVal();
            log.info("强制改变赎回响应码为: {} - {}", code, val);
        } else {
            log.info("不强制改变赎回响应码, 正常赎回...");
        }

        // 状态码
        Config configStat = configService.findBankSelectConfig(bank.getCode(), "redeemStat");
        String stat = "s";
        String valStat = "交易成功";
        if (configStat != null) {
            stat = configStat.getK();
            valStat = configStat.getVal();
            log.info("强制改变赎回状态码为: {} - {}", stat, val);
        } else {
            log.info("不强制改变赎回状态码, 正常赎回...");
        }

        // 银联方流水号
        String serNo = configService.getSerNo();
        log.info("银联方流水号为: {}", serNo);

        Transaction tran = transactionService.findTransactionBySerNo(cpRedeemRequest.getMerSeqId());
        if (tran != null) {
            log.info("交易重复, 基金公司流水号为: {}", tran);
            code = "0105";
            val = "重复交易";
        } else {
            log.info("交易不重复, 生成交易并入库...");
            // 交易内容
            Transaction transaction = CpUtil.redeemRequestToTransaction(cpRedeemRequest, bank, code, stat, serNo);
            log.info("交易内容为: {}", transaction);

            // 交易入库
            transactionService.saveTransaction(transaction);
        }

        // 返回报文
        String msg = CpUtil.buildRedeemMsg(cpRedeemRequest, code, serNo, stat);
        log.info("返回报文: {}", msg);

        log.info("============ 赎回结束 ============");
        return msg;
    }

    /**
     * 代付单笔查询
     *
     * @param cpQueryRequest
     * @return
     */
    @RequestMapping(value = "query", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String query(CpQueryRequest cpQueryRequest) {
        log.info("============ 代付单查开始 ============");
        log.info("请求报文: {}", cpQueryRequest);

        Transaction transaction = transactionService.findTransactionBySerNo4Query(cpQueryRequest.getMerSeqId());
        log.info("查询的交易: {}", transaction);

        // 银联方流水号
        String serNo = configService.getSerNo();
        log.info("银联方流水号为: {}", serNo);

        // 返回报文
        String msg = CpUtil.buildQueryMsg(cpQueryRequest, transaction, serNo);
        log.info("返回报文: {}", msg);

        log.info("============ 代付单查结束 ============");
        return msg;
    }

    /**
     * 代扣单笔查询
     *
     * @param cpQuery2Request
     * @return
     */
    @RequestMapping(value = "query2", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String query2(CpQuery2Request cpQuery2Request) {
        log.info("============ 代扣单查开始 ============");
        log.info("请求报文: {}", cpQuery2Request);

        Transaction transaction = transactionService.findTransactionBySerNo4Query(cpQuery2Request.getOrderNo());
        log.info("查询的交易: {}", transaction);

        // 银联方流水号
        String serNo = configService.getSerNo();
        log.info("银联方流水号为: {}", serNo);

        // 返回报文
        String msg = CpUtil.buildQuery2Msg(cpQuery2Request, transaction, serNo);
        log.info("返回报文: {}", msg);

        log.info("============ 代扣单查结束 ============");
        return msg;
    }

    /**
     * 异步通知be
     *
     * @param cpVerifyRequest
     */
    private void asyncNoticeBe(CpVerifyRequest cpVerifyRequest, String code, String bankCode) {
        String param = CpUtil.buildVerifyParam(cpVerifyRequest, code);
        Config config = configService.findConfigByK(bankCode + "-verify-sleep");
        log.info("通知参数: {}", param);
        new Thread() {
            public void run() {
                try {
                    Long sleep = config == null ? 3000 : Long.parseLong(config.getVal()) * 1000;
                    sleep(sleep);
                    log.info("异步通知be睡了{}ms", sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String result = HttpUtil.sendPost(cpVerifyRequest.getReturnUrl(), param);
                if ("chinapayok".equals(result)) {
                    log.info("异步通知be成功!");
                } else {
                    log.info("异步通知be失败!");
                }
            }
        }.start();
    }


}
