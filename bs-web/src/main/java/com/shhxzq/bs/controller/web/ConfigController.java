package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 配置
 *
 * @author kangyonggan
 * @since 16/5/17
 */
@Controller
@RequestMapping("config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    /**
     * 加载皮肤
     *
     * @return
     */
    @RequestMapping(value = "skin", method = RequestMethod.GET)
    @ResponseBody
    public Config skin() {
        return configService.findConfigByGrp("skin");
    }

    /**
     * 更新皮肤
     *
     * @return
     */
    @RequestMapping(value = "skin", method = RequestMethod.POST)
    @ResponseBody
    public void skin(String k, String val) {
        Config cfg = configService.findConfigByGrp("skin");
        cfg.setK(k);
        cfg.setVal(val);

        configService.updateConfig(cfg);
    }

    /**
     * 加载其他配置
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Config> config() {
        return configService.findConfigsByGrp("sys");
    }

    /**
     * 更新其他配置
     *
     * @param k
     */
    @RequestMapping(value = "{k}", method = RequestMethod.GET)
    @ResponseBody
    public void config(@PathVariable("k") String k, @RequestParam(name = "val", required = true) String val) {
        Config cfg = configService.findConfigsByGrpAndK("sys", k);
        cfg.setVal(val);

        configService.updateConfig(cfg);
    }

}
