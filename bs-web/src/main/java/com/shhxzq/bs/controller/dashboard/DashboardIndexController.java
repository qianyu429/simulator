package com.shhxzq.bs.controller.dashboard;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author kangyonggan
 * @since 16/5/15
 */
@Controller
@RequestMapping("dashboard")
public class DashboardIndexController {

    private static final String PATH_ROOT = "dashboard";
    private static final String PATH_INDEX = PATH_ROOT + "/index";

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("dashboard")
    public String index() {
        return PATH_INDEX;
    }

}
