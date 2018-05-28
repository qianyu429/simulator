package com.shhxzq.bs.controller.dashboard;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.util.FileUpload;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 民生超网模拟器
 *
 * @author kangyonggan
 * @since 16/5/19
 */
@Controller
@RequestMapping("dashboard/cmbc")
@Log4j2
public class DashboardCmbcController {

    private static final String PATH_ROOT = "dashboard/cmbc";
    private static final String PATH_IMPORT_MODAL = PATH_ROOT + "/import-modal";

    @Autowired
    private ConfigService configService;

    /**
     * 导入错误码
     *
     * @return
     */
    @RequestMapping(value = "import", method = RequestMethod.GET)
    @RequiresPermissions("dashboard-cmbc-config")
    public String importExcel() {
        return PATH_IMPORT_MODAL;
    }

    /**
     * 导入
     *
     * @param excel
     * @return
     */
    @RequestMapping(value = "import", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> importUsersExcel(@RequestParam(value = "excel") MultipartFile excel) {
        Map<String, Object> map = new HashMap();
        try {
            String filePath = FileUpload.upload(excel);
            configService.importConfig("cmbc-code-redeem", filePath);
        } catch (Exception e) {
            log.error("导入错误码失败!", e);
            map.put("status", AppConstants.FAIL);
            map.put("errorMessage", e.getMessage());
        }
        return map;
    }

}
