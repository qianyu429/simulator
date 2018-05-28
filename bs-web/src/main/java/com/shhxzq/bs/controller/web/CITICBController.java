package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.StatusEnum;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Code;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.ValidationResponse;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wanglili on 17/1/10.
 */
@Controller
@Log4j2
@RequestMapping("dashboard/citicb")
public class CITICBController {
    @Autowired
    private ConfigService configService;
    @Autowired
    private BankService bankService;

    /**
     * 更新模拟器配置
     *
     * @param code
     * @param date
     * @param payAmt
     * @param minAmt
     * @return
     */
    @RequestMapping(value = "config", method = RequestMethod.POST)
    @RequiresPermissions("dashboard-citicb-config")
    public String config(Code code, String date, String payAmt, String minAmt) {
        ValidationResponse res = new ValidationResponse();
        try {
            Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CITICB);

            configService.updateSelectConfigs(bank.getCode(), code);
            configService.deleteZXVerifyConfig(bank.getCode());
            Config c1 = new Config();
            c1.setGrp(bank.getCode() + "-verify-date");
            c1.setK("date");
            c1.setVal(date);
            c1.setStatus(StatusEnum.ABLE.getStatus());
            c1.setUserId(0L);
            configService.saveConfig(c1);

            Config c2 = new Config();
            c2.setGrp(bank.getCode() + "-verify-payAmt");
            c2.setK("payAmt");
            c2.setVal(payAmt);
            c2.setStatus(StatusEnum.ABLE.getStatus());
            c2.setUserId(0L);
            configService.saveConfig(c2);

            Config c3 = new Config();
            c3.setGrp(bank.getCode() + "-verify-minAmt");
            c3.setK("minAmt");
            c3.setVal(minAmt);
            c3.setStatus(StatusEnum.ABLE.getStatus());
            c3.setUserId(0L);
            configService.saveConfig(c3);

            res.setStatus(AppConstants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(AppConstants.FAIL);
            res.setMessage("模拟器配置失败!");
            log.info("模拟器配置失败!");
        }
        return "redirect:/dashboard/citicb/config";
    }

}
