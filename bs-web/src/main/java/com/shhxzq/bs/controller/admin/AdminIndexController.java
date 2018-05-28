package com.shhxzq.bs.controller.admin;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 后台管理
 *
 * @author kangyonggan
 * @since 16/5/15
 */
@Controller
@RequestMapping("admin")
public class AdminIndexController {

    private static final String PATH_ROOT = "admin";
    private static final String PATH_INDEX = PATH_ROOT + "/index";

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("admin")
    public String index() {
        return PATH_INDEX;
    }

}
