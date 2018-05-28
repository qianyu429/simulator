package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.TransactionService;
import com.shhxzq.bs.util.CmbcUtil;
import com.shhxzq.bs.util.EctUtil;
import com.shhxzq.bs.util.HttpUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 民生超网模拟器
 *
 * @author kangyonggan
 * @since 16/5/20
 */
@Controller
@RequestMapping("cmbc")
@Log4j2
public class CmbcController {

    @Autowired
    private ConfigService configService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BankService bankService;

    /**
     * 民生交易统一入口
     *
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("---------------------------------- 进入民生模拟器 ----------------------------------");
        // 解析请求报文
        String message = HttpUtil.transFromRequest(request);
        log.info("请求报文: {}", message);

        // 解析xml
        Map<String, String> params = EctUtil.parseXml(message);
        log.info("请求报文-名值对: {}", params);

        // 应答报文模板
        String msgTemplate = "<?xml version=\"1.0\"?><CMBC><responseHeader>%s</responseHeader><xDataBody>%s</xDataBody></CMBC>";
        log.info("应答报文模板: {}", msgTemplate);

        // 交易类型不存在时, 使用此默认返回报文
        List<String> list = new ArrayList();
        String header = "<status><code>0</code><message>ok</message></status>";
        String body = "<statusId><statusCode>0</statusCode><statusErrMsg>交易成功</statusErrMsg></statusId>";
        list.add(header);
        list.add(body);

        // 根据交易类型分发
        boolean isQuery = message.indexOf("trnCode=\"qryXfer\"") > -1;
        log.info("交易类型: {}", isQuery ? "单笔查询" : "赎回");
        if (isQuery) {
            list = query(params);
        } else {
            list = redeem(params);
        }

        log.info("应答消息header: {}", list.get(0));
        log.info("应答消息body: {}", list.get(1));

        // 组装报文
        String xml = String.format(msgTemplate, list.get(0), list.get(1));
        log.info("响应报文: {}", xml);

        response.setCharacterEncoding("gb2312");
        PrintWriter out = response.getWriter();

        out.print(xml);
        out.flush();
        out.close();
        log.info("---------------------------------- 离开民生模拟器 ----------------------------------");
    }

    /**
     * 赎回
     *
     * @param params
     * @return
     */
    public List<String> redeem(Map<String, String> params) {
        log.info("============ 进入赎回接口 ============");

        // 模拟器交易流水号
        String serNo = configService.getSerNo();
        log.info("模拟器交易流水号: {}", serNo);

        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CMBC);

        // 响应码 = 0:交易成功, 2:交易失败, 其他:交易处理中
        Config config = configService.findBankSelectConfig(bank.getCode(), "redeem");
        String code = "0";
        String val = "交易成功";
        if (config != null) {
            code = config.getK();
            val = config.getVal();
            log.info("强制改变发赎回响应码: {} - {}", code, val);
        } else {
            log.info("不强制改变赎回响应码: {} - {}", code, val);
        }
        // 交易落库
        Transaction tran = transactionService.findTransactionBySerNo(params.get("trnId"));
        if (tran != null) {
            log.info("交易重复, 基金公司流水号为: {}", tran);
            code = "88888888";
            val = "重复交易";
        } else {
            log.info("交易不重复, 生成交易并入库..");
            // 交易内容
            Transaction transaction = CmbcUtil.buildTransaction(params, bank, TransTypeEnum.REDEEM.getType(), serNo, code, val);
            log.info("交易内容为: {}", transaction);

            // 交易入库
            transactionService.saveTransaction(transaction);
        }

        List<String> list = new ArrayList();
        String header = "<status><code>" + code + "</code><message>" + val + "</message></status>";
        String body = "";
        list.add(header);
        list.add(body);

        log.info("============ 离开赎回接口 ============");
        return list;
    }

    /**
     * 单笔查询
     *
     * @param params
     * @return
     */
    public List<String> query(Map<String, String> params) {
        log.info("============ 进入单笔查询接口 ============");

        // 查询交易
        Transaction transaction = transactionService.findTransactionBySerNo4Query(params.get("trnId"));
        log.info("查询的交易为: {}", transaction);

        String code = "0";
        String val = "查询交易成功";
        if (transaction == null) {
            code = "E1602";
            val = "此流水号不存在，请查证!";
            transaction = new Transaction();
            transaction.setRespCode("");
        }
        log.info(val);

        Config config = configService.findConfigByGrpAndK("cmbc-code-redeem", code);
        if (config == null) {
            config = new Config();
            config.setVal("流水号不存在");
        }

//        if ("F".equals(transaction.getStat())) {
//            transaction.setRespCode("2");
//        }

        List<String> list = new ArrayList();
        String header = "<status><code>" + code + "</code><message>" + val + "</message></status>";
        String body = "<statusId><statusCode>" + transaction.getRespCode() + "</statusCode><statusErrMsg>" + config.getVal() + "</statusErrMsg></statusId>";
        list.add(header);
        list.add(body);

        log.info("============ 离开单笔查询接口 ============");
        return list;
    }


}
