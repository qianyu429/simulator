package com.shhxzq.bs.controller.admin;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.model.Menu;
import com.shhxzq.bs.model.Role;
import com.shhxzq.bs.model.ValidationResponse;
import com.shhxzq.bs.service.MenuService;
import com.shhxzq.bs.service.RoleService;
import com.shhxzq.bs.util.Collections3;
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
 * 角色管理
 *
 * @author kangyonggan
 * @since 2016/05/18
 */
@Controller
@RequestMapping(value = "admin/role")
public class AdminRoleController {

    private static final String PATH_ROOT = "admin/role";
    private static final String PATH_INDEX = PATH_ROOT + "/list";
    private static final String PATH_CREATE_MODAL = PATH_ROOT + "/create-modal";
    private static final String PATH_DETAIL_MODAL = PATH_ROOT + "/detail-modal";
    private static final String PATH_MENU_MODAL = PATH_ROOT + "/menu-modal";
    private static final String PATH_ROLE_TR = PATH_ROOT + "/role-tr";

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 列表界面
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("admin-role-manage")
    public String index(Model model) {
        List<Role> roles = roleService.findAllRoles();

        model.addAttribute("roles", roles);
        return PATH_INDEX;
    }

    /**
     * 添加界面
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("admin-role-manage")
    public String create(@ModelAttribute("role") Role role) {
        return PATH_CREATE_MODAL;
    }

    /**
     * 保存
     *
     * @param role
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("admin-role-manage")
    public ValidationResponse save(@ModelAttribute("role") @Valid Role role,
                                   BindingResult result) {
        ValidationResponse res = new ValidationResponse();

        if (result.hasErrors()) {
            res.setStatus(AppConstants.FAIL);
        } else {
            roleService.saveRole(role);
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
    @RequiresPermissions("admin-role-manage")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("role", roleService.getRole(id));
        return PATH_CREATE_MODAL;
    }

    /**
     * 更新
     *
     * @param role
     * @param result
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("admin-role-manage")
    public ValidationResponse update(@ModelAttribute("role") @Valid Role role,
                                     BindingResult result) {
        ValidationResponse res = new ValidationResponse();

        if (result.hasErrors()) {
            res.setStatus(AppConstants.FAIL);
        } else {
            roleService.updateRole(role);
            res.setStatus(AppConstants.SUCCESS);
        }

        return res;
    }

    /**
     * 详情界面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}", method = RequestMethod.GET)
    @RequiresPermissions("admin-role-manage")
    public String detail(@PathVariable("id") Long id, Model model) {
        Role role = roleService.getRole(id);

        model.addAttribute("role", role);
        return PATH_DETAIL_MODAL;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/delete", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("admin-role-manage")
    public String delete(@PathVariable Long id) {
        roleService.deleteRole(id);
        return "true";
    }


    /**
     * 禁用/启动
     *
     * @param id
     * @param status
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/{status:\\bable\\b|\\bdisable\\b}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @RequiresPermissions("admin-user-manage")
    public String lock(@PathVariable("id") Long id, @PathVariable("status") String status, Model model) {
        Role role = roleService.getRole(id);
        role.setStatus(status);
        roleService.updateRole(role);

        model.addAttribute("role", role);
        return PATH_ROLE_TR;
    }

    /**
     * 设置工作台菜单
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/dashboard", method = RequestMethod.GET)
    @RequiresPermissions("admin-user-manage")
    public String dashboardMenu(@PathVariable Long id, Model model) {
        List<Menu> role_menus = menuService.findDashboardMenusByRoleId(id);
        if (role_menus != null) {
            role_menus = Collections3.extractToList(role_menus, "name");
        }
        List<Menu> all_menus = menuService.findDashboardMenus();

        model.addAttribute("role_menus", role_menus);
        model.addAttribute("all_menus", all_menus);
        model.addAttribute("root", "dashboard");
        return PATH_MENU_MODAL;
    }

    /**
     * 设置后台菜单
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/admin", method = RequestMethod.GET)
    @RequiresPermissions("admin-user-manage")
    public String adminMenu(@PathVariable Long id, Model model) {
        List<Menu> role_menus = menuService.findAdminMenusByRoleId(id);
        if (role_menus != null) {
            role_menus = Collections3.extractToList(role_menus, "name");
        }
        List<Menu> all_menus = menuService.findAdminMenus();

        model.addAttribute("role_menus", role_menus);
        model.addAttribute("all_menus", all_menus);
        model.addAttribute("root", "admin");
        return PATH_MENU_MODAL;
    }

    /**
     * 保存角色菜单
     *
     * @param id
     * @param menus
     * @param root
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/menus", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("admin-user-manage")
    public Boolean menu(@PathVariable Long id,
                        @RequestParam(value = "menus") String menus,
                        @RequestParam(value = "root") String root) {
        List<Menu> role_menus = null;
        if ("dashboard".equals(root)) {
            role_menus = menuService.findDashboardMenusByRoleId(id);
        } else if ("admin".equals(root)) {
            role_menus = menuService.findAdminMenusByRoleId(id);
        }

        roleService.updateRoleMenus(id, menus, role_menus);
        return true;
    }

    /**
     * 校验菜单名称是否已用
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/verify-name", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("admin-role-manage")
    public boolean verifyName(@RequestParam String name) {
        Role role = roleService.findRoleByName(name);
        return role == null;
    }

}
