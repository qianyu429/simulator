package com.shhxzq.bs.controller.admin;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.model.Menu;
import com.shhxzq.bs.model.ValidationResponse;
import com.shhxzq.bs.service.MenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 菜单管理
 *
 * @author kangyonggan
 * @since 2016/05/18
 */
@Controller
@RequestMapping(value = "admin/menu")
public class AdminMenuController {

    private static final String PATH_ROOT = "admin/menu";
    private static final String PATH_INDEX = PATH_ROOT + "/index";
    private static final String PATH_FORM = PATH_ROOT + "/create-modal";

    @Autowired
    private MenuService menuService;

    /**
     * 菜单树界面
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("admin-menu-manage")
    public String index(Model model) {
        List<Menu> all_admin_menus = menuService.findAdminMenus();
        List<Menu> all_dashboard_menus = menuService.findDashboardMenus();

        model.addAttribute("all_admin_menus", all_admin_menus);
        model.addAttribute("all_dashboard_menus", all_dashboard_menus);
        return PATH_INDEX;
    }

    /**
     * 添加界面
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("admin-menu-manage")
    public String create(@RequestParam(value = "pid", defaultValue = "0") Long pid,
                         Model model) {
        model.addAttribute("menu", new Menu());
        model.addAttribute("parent_menu", menuService.getMenu(pid));
        return PATH_FORM;
    }

    /**
     * 保存
     *
     * @param menu
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("admin-menu-manage")
    public ValidationResponse save(@ModelAttribute("menu") @Valid Menu menu,
                                   BindingResult result) {
        ValidationResponse res = new ValidationResponse();
        if (result.hasErrors()) {
            res.setStatus(AppConstants.FAIL);
        } else {
            menuService.saveMenu(menu);
            res.setStatus(AppConstants.SUCCESS);
        }
        return res;
    }

    /**
     * 编辑界面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("admin-menu-manage")
    public String edit(@PathVariable Long id, Model model) {
        Menu menu = menuService.getMenu(id);
        model.addAttribute("menu", menu);
        model.addAttribute("parent_menu", menuService.getMenu(menu.getPid()));
        return PATH_FORM;
    }

    /**
     * 更新
     *
     * @param menu
     * @param result
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("admin-menu-manage")
    public ValidationResponse update(@ModelAttribute("menu") @Valid Menu menu,
                                     BindingResult result) {
        ValidationResponse res = new ValidationResponse();

        if (result.hasErrors()) {
            res.setStatus(AppConstants.FAIL);
        } else {
            menuService.updateMenu(menu);
            res.setStatus(AppConstants.SUCCESS);
        }

        return res;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/delete", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("admin-menu-manage")
    public String delete(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return "true";
    }

    /**
     * 校验菜单名称唯一性
     *
     * @param name
     * @param old_name
     * @return
     */
    @RequestMapping(value = "verify-name", method = RequestMethod.POST)
    @RequiresPermissions("admin-menu-manage")
    @ResponseBody
    public boolean verifyName(@RequestParam String name, @RequestParam String old_name) {
        if (old_name.equals(name)) {
            return true;
        }
        Menu menu = menuService.findMenuByName(name);
        return menu == null;
    }

}
