package com.shhxzq.bs.controller.dashboard;

import com.shhxzq.bs.model.Code;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.util.CommonUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * 模拟器通用设置
 *
 * @author kangyonggan
 * @since 2017/1/5
 */
@Controller
@RequestMapping("dashboard")
@Log4j2
public class DashboardSettingController {

    @Autowired
    private ConfigService configService;

    /**
     * 错误码配置界面
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "{bankCode}/code", method = RequestMethod.GET)
    public String code(@PathVariable("bankCode") String bankCode, Model model) throws Exception {
        Map<String, Object> codeMap = configService.findConfigsMap(bankCode + "-code");

        model.addAttribute("codeMap", codeMap);
        return "dashboard/" + bankCode + "/code";
    }

    /**
     * 更新错误码
     *
     * @param bankCode
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "{bankCode}/code", method = RequestMethod.POST)
    public String code(@PathVariable("bankCode") String bankCode, Code code) throws Exception {
        configService.updateConfigs(bankCode, code);
        return "redirect:/dashboard/" + bankCode + "/code";
    }

    /**
     * 模拟器配置界面
     *
     * @param bankCode
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "{bankCode}/config", method = RequestMethod.GET)
    public String config(@PathVariable("bankCode") String bankCode, Model model) throws Exception {
        Map<String, Object> codeMap = configService.findConfigsMap(bankCode + "-code");
        Map<String, Object> selectMap = configService.findConfigsMap(bankCode + "-select");
        CommonUtil.initMap(selectMap);

        model.addAttribute("codeMap", codeMap);
        model.addAttribute("selectMap", selectMap);
        return "dashboard/" + bankCode + "/config";
    }

    /**
     * 更新模拟器配置
     *
     * @param bankCode
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "{bankCode}/config", method = RequestMethod.POST)
    public String config(@PathVariable("bankCode") String bankCode, Code code) throws Exception {
        configService.updateSelectConfigs(bankCode, code);
        return "redirect:/dashboard/" + bankCode + "/config";
    }

}
