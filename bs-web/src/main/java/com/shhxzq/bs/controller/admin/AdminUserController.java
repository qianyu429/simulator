package com.shhxzq.bs.controller.admin;

import com.github.pagehelper.PageInfo;
import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.model.Role;
import com.shhxzq.bs.model.User;
import com.shhxzq.bs.model.ValidationResponse;
import com.shhxzq.bs.service.RoleService;
import com.shhxzq.bs.service.UserService;
import com.shhxzq.bs.util.Collections3;
import freemarker.ext.beans.BeansWrapper;
import lombok.extern.log4j.Log4j2;
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
 * 用户管理
 *
 * @author kangyonggan
 * @since 2016/05/18
 */
@Controller
@RequestMapping(value = "admin/user")
@Log4j2
public class AdminUserController {

    private static final String PATH_ROOT = "admin/user";
    private static final String PATH_INDEX = PATH_ROOT + "/index";
    private static final String PATH_CREATE_MODAL = PATH_ROOT + "/create-modal";
    private static final String PATH_DETAIL_MODAL = PATH_ROOT + "/detail-modal";
    private static final String PATH_ROLES_MODAL = PATH_ROOT + "/roles-modal";
    private static final String PATH_USER_TR = PATH_ROOT + "/user-tr";

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 用户管理list界面
     *
     * @param pageNow
     * @param status
     * @param username
     * @param realname
     * @param mobile
     * @param email
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("admin-user-manage")
    public String index(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNow,
                        @RequestParam(value = "status", required = false, defaultValue = "") String status,
                        @RequestParam(value = "username", required = false, defaultValue = "") String username,
                        @RequestParam(value = "realname", required = false, defaultValue = "") String realname,
                        @RequestParam(value = "mobile", required = false, defaultValue = "") String mobile,
                        @RequestParam(value = "email", required = false, defaultValue = "") String email,
                        Model model) {
        List<User> users = userService.searchUsers(pageNow, AppConstants.PAGE_SIZE, status, username, realname, mobile, email);
        PageInfo<User> page = new PageInfo(users);

        model.addAttribute("enums", BeansWrapper.getDefaultInstance().getEnumModels());
        model.addAttribute("page", page);
        return PATH_INDEX;
    }

    /**
     * 添加用户
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("admin-user-manage")
    public String create(Model model) {
        model.addAttribute("user", new User());
        return PATH_CREATE_MODAL;
    }

    /**
     * 编辑用户
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("admin-user-manage")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return PATH_CREATE_MODAL;
    }

    /**
     * 添加用户
     *
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("admin-user-manage")
    public ValidationResponse save(@ModelAttribute("user") @Valid User user, BindingResult result) {
        ValidationResponse res = new ValidationResponse();

        if (!result.hasErrors()) {
            int count = userService.saveUserAndRoleAndConfig(user);
            res.setStatus(count == 1 ? AppConstants.SUCCESS : AppConstants.FAIL);
        } else {
            res.setStatus(AppConstants.FAIL);
            res.setMessage("添加用户失败, 可能是用户名重复或字段长度不符合");
            log.info("添加用户失败, 可能是用户名重复或字段长度不符合");
        }

        return res;
    }

    /**
     * 更新用户
     *
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("admin-user-manage")
    public ValidationResponse update(@ModelAttribute("user") @Valid User user, BindingResult result) {
        ValidationResponse res = new ValidationResponse();

        if (!result.hasErrors()) {
            int count = userService.updateUser(user);
            res.setStatus(count == 1 ? AppConstants.SUCCESS : AppConstants.FAIL);
        } else {
            res.setStatus(AppConstants.FAIL);
            res.setMessage("更新用户失败, 可能是用户名重复或字段长度不符合");
            log.info("更新用户失败, 可能是用户名重复或字段长度不符合");
        }

        return res;
    }

    /**
     * 查看用户详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}", method = RequestMethod.GET)
    @RequiresPermissions("admin-user-manage")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return PATH_DETAIL_MODAL;
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/delete", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("admin-user-manage")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "true";
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
        User user = userService.getUser(id);
        user.setStatus(status);
        userService.updateUser(user);

        model.addAttribute("user", user);
        return PATH_USER_TR;
    }

    /**
     * 设置角色
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/roles", method = RequestMethod.GET)
    @RequiresPermissions("admin-user-manage")
    public String roles(@PathVariable Long id, Model model) {
        List<Role> user_roles = roleService.findRolesByUserId(id);
        user_roles = Collections3.extractToList(user_roles, "name");
        List<Role> roles = roleService.findAllRoles();

        model.addAttribute("user_roles", user_roles);
        model.addAttribute("roles", roles);
        model.addAttribute("userId", id);
        return PATH_ROLES_MODAL;
    }

    /**
     * 保存角色
     *
     * @param id
     * @param roles
     * @return
     */
    @RequestMapping(value = "{id}/roles", method = RequestMethod.POST)
    @RequiresPermissions("admin-user-manage")
    @ResponseBody
    public Boolean updateUserRoles(@PathVariable Long id,
                                   @RequestParam(value = "roles", defaultValue = "") String roles) {
        userService.updateUserRoles(id, roles);
        return true;
    }

    /**
     * 用户名唯一性校验
     *
     * @param username
     * @param vaild
     * @return
     */
    @RequestMapping(value = "/verify-username", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("admin-user-manage")
    public boolean verifyName(@RequestParam String username, @RequestParam Boolean vaild) {
        if (!vaild) {
            return true;
        }
        User user = userService.findUserByUsername(username);
        return user == null;
    }

}
