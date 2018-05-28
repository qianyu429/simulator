package com.shhxzq.bs.controller.admin;

import com.github.pagehelper.PageInfo;
import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.ValidationResponse;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.UserService;
import freemarker.ext.beans.BeansWrapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/05/29
 */
@Controller
@RequestMapping(value = "admin/config")
public class AdminConfigController {

    private static final String PATH_ROOT = "admin/config";
    private static final String PATH_INDEX = PATH_ROOT + "/index";
    private static final String PATH_CREATE_MODAL = PATH_ROOT + "/create-modal";
    private static final String PATH_DETAIL_MODAL = PATH_ROOT + "/detail-modal";
    private static final String PATH_CONFIG_TR = PATH_ROOT + "/config-tr";

    @Autowired
    private ConfigService configService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("admin-config-manage")
    public String index(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNow,
                        @RequestParam(value = "status", required = false, defaultValue = "") String status,
                        @RequestParam(value = "grp", required = false, defaultValue = "") String grp,
                        @RequestParam(value = "k", required = false, defaultValue = "") String k,
                        @RequestParam(value = "val", required = false, defaultValue = "") String val,
                        Model model) {
        List<Config> configs = configService.searchConfigs(pageNow, status, grp, k, val);
        PageInfo<Config> page = new PageInfo(configs);

        model.addAttribute("enums", BeansWrapper.getDefaultInstance().getEnumModels());
        model.addAttribute("page", page);
        return PATH_INDEX;
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("admin-config-manage")
    public String create(Model model) {
        model.addAttribute("config", new Bank());
        return PATH_CREATE_MODAL;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("admin-config-manage")
    public ValidationResponse save(@ModelAttribute("config") @Valid Config config,
                                   BindingResult result) {
        ValidationResponse res = new ValidationResponse();

        if (result.hasErrors()) {
            res.setStatus(AppConstants.FAIL);
        } else {
            configService.saveConfig(config);
            res.setStatus(AppConstants.SUCCESS);
        }

        return res;
    }

    @RequestMapping(value = "{id:[\\d]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("admin-config-manage")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("config", configService.getConfig(id));
        return PATH_CREATE_MODAL;
    }

    @RequestMapping(value = "{id:[\\d]+}/update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("admin-config-manage")
    public ValidationResponse update(@ModelAttribute("config") @Valid Config config,
                                     BindingResult result) {
        ValidationResponse res = new ValidationResponse();

        if (result.hasErrors()) {
            res.setStatus(AppConstants.FAIL);
        } else {
            configService.updateConfig(config);
            res.setStatus(AppConstants.SUCCESS);
        }

        return res;
    }

    @RequestMapping(value = "{id:[\\d]+}", method = RequestMethod.GET)
    @RequiresPermissions("admin-config-manage")
    public String detail(@PathVariable("id") Long id, Model model) {
        Config config = configService.getConfig(id);

        model.addAttribute("config", config);
        return PATH_DETAIL_MODAL;
    }

    /**
     * 禁用/启用
     *
     * @param id
     * @param status
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/{status:\\bable\\b|\\bdisable\\b}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @RequiresPermissions("admin-user-manage")
    public String lock(@PathVariable("id") Long id, @PathVariable("status") String status, Model model) {
        Config config = configService.getConfig(id);
        config.setStatus(status);
        configService.updateConfig(config);

        model.addAttribute("config", config);
        return PATH_CONFIG_TR;
    }

    @RequestMapping(value = "{id:[\\d]+}/delete", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("admin-config-manage")
    public String delete(@PathVariable Long id) {
        configService.deleteConfig(id);
        return "true";
    }

}
