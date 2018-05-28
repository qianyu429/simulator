package com.shhxzq.bs.task;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.TransactionService;
import com.shhxzq.bs.util.DateUtil;
import com.shhxzq.bs.util.HttpUtil;
import com.shhxzq.bs.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 银联定时生成对账文件的任务
 *
 * @author kangyonggan
 * @since 16/5/27
 */
@Component
@Log4j2
public class CpTask {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BankService bankService;

    @Autowired
    private ConfigService configService;

    /**
     * 根据cron表达式定时执行
     */
//    @Scheduled(cron = "0 0 2 * * ?")
    public void task() throws Exception {
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CP);
        Config ipConfig = configService.findConfigByK("ip");
        Config portConfig = configService.findConfigByK("port");

        log.info("============= 生成对账文件开始 =============");
        // 生成代扣对账文件
        String fileNamePay = transactionService.genDzFile(bank, TransTypeEnum.PAY.getType(), DateUtil.format8Date(new Date()));

        String payPath = "http://" + ipConfig.getVal() + ":" + portConfig.getVal() + "/" + AppConstants.PATH_DOWNLOAD_DZ + bank.getCode() + "/" + DateUtil.format8Date(new Date()) + "/" + fileNamePay;
        log.info("把代扣对账文件路径{}推给be...", payPath);
        if (StringUtil.isNotEmpty(fileNamePay)) {
            String param = "download=" + payPath + "?filename=" + fileNamePay;
            log.info("推送对账文件参数url  : {}", bank.getPayUrl());
            log.info("推送对账文件参数param: {}", param);
            String result = HttpUtil.sendPost(bank.getPayUrl(), param);
            if (StringUtil.isEmpty(result)) {
                log.info("推送失败!!");
            } else {
                log.info("推送成功!!");
            }
        }

        log.info("\n-----------------------------\n");

        Thread.sleep(1000);

        // 生成代付对账文件
        String fileNameRedeem = transactionService.genDzFile(bank, TransTypeEnum.REDEEM.getType(), DateUtil.format8Date(new Date()));

        String redeemPath = "http://" + ipConfig.getVal() + ":" + portConfig.getVal() + "/" + AppConstants.PATH_DOWNLOAD_DZ + bank.getCode() + "/" + DateUtil.format8Date(new Date()) + "/" + fileNameRedeem;
        log.info("把代付对账文件路径{}推给be...", redeemPath);
        // TODO 如果be没收到, 需要不停的(5次)通知be, 也可人工通知(可能需要落库)
        if (StringUtil.isNotEmpty(fileNameRedeem)) {
            String param = "download=" + redeemPath + "?filename=" + fileNameRedeem;
            log.info("推送对账文件参数url  : {}", bank.getRedeemUrl());
            log.info("推送对账文件参数param: {}", param);
            String result = HttpUtil.sendPost(bank.getPayUrl(), param);
            if (StringUtil.isEmpty(result)) {
                log.info("推送失败!!");
            } else {
                log.info("推送成功!!");
            }
        }
        log.info("============= 生成对账文件结束 =============");
    }

}
