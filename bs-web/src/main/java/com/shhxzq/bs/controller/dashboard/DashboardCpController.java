package com.shhxzq.bs.controller.dashboard;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Code;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.ValidationResponse;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.util.CommonUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 银联模拟器
 *
 * @author kangyonggan
 * @since 16/5/19
 */
@Controller
@RequestMapping("dashboard/cp")
@Log4j2
public class DashboardCpController {

    private static final String PATH_ROOT = "dashboard/cp";
    private static final String PATH_CODE = PATH_ROOT + "/code";
    private static final String PATH_CONFIG = PATH_ROOT + "/config";

    @Autowired
    private ConfigService configService;

    @Autowired
    private BankService bankService;

    /**
     * 模拟器配置界面
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "config", method = RequestMethod.GET)
    @RequiresPermissions("dashboard-cp-config")
    public String config(Model model) throws Exception {
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CP);
        Config config = configService.findConfigByK(bank.getCode() + "-verify-sleep");
        Map<String, Object> codeMap = configService.findConfigsMap(bank.getCode() + "-code");
        Map<String, Object> selectMap = configService.findConfigsMap(bank.getCode() + "-select");
        CommonUtil.initMap(selectMap);
        if (config == null) {
            config = new Config();
            config.setGrp("");
            config.setK(bank.getCode() + "-verify-sleep");
            config.setVal("3");
            config.setUserId(0L);
            configService.saveConfig(config);
        }

        model.addAttribute("config", config);
        model.addAttribute("codeMap", codeMap);
        model.addAttribute("selectMap", selectMap);
        return PATH_CONFIG;
    }

    /**
     * 更新模拟器配置
     *
     * @param code
     * @param val
     * @return
     */
    @RequestMapping(value = "config", method = RequestMethod.POST)
    @RequiresPermissions("dashboard-cp-config")
    public String config(Code code, String val) {
        ValidationResponse res = new ValidationResponse();
        try {
            Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_CP);
            configService.updateSelectConfigs(bank.getCode(), code);
            Config config = configService.findConfigByK(bank.getCode() + "-verify-sleep");
            config.setVal(val);
            configService.updateConfig(config);
            res.setStatus(AppConstants.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(AppConstants.FAIL);
            res.setMessage("模拟器配置失败!");
            log.info("模拟器配置失败!");
        }
        return "redirect:/dashboard/cp/config";
    }

}
