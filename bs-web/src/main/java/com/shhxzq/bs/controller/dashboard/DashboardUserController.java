package com.shhxzq.bs.controller.dashboard;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.model.ShiroUser;
import com.shhxzq.bs.model.User;
import com.shhxzq.bs.model.ValidationResponse;
import com.shhxzq.bs.service.UserService;
import com.shhxzq.bs.util.FileUpload;
import com.shhxzq.bs.util.Images;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * 个人
 *
 * @author kangyonggan
 * @since 16/5/19
 */
@Controller
@RequestMapping("dashboard/user")
@Log4j2
public class DashboardUserController {

    private static final String PATH_ROOT = "dashboard/user";
    private static final String PATH_AVATAR = PATH_ROOT + "/avatar";
    private static final String PATH_PROFILE = PATH_ROOT + "/profile";
    private static final String PATH_PASSWORD = PATH_ROOT + "/password";

    @Autowired
    private UserService userService;

    /**
     * 修改头像
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "avatar", method = RequestMethod.GET)
    @RequiresPermissions("dashboard-user-avatar")
    public String avatar(Model model) {
        model.addAttribute("user", userService.getUser(userService.getShiroUser().getId()));
        return PATH_AVATAR;
    }

    /**
     * 保存头像修改
     *
     * @param id
     * @param avatar
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/avatar", method = RequestMethod.POST)
    @RequiresPermissions("dashboard-user-avatar")
    public String avatar(@PathVariable("id") Long id,
                         @RequestParam(value = "avatar") MultipartFile avatar) throws Exception {
        String fileName = FileUpload.upload(avatar);
        User user = userService.getUser(id);

        String large = Images.large(fileName);
        user.setLargeAvatar(large);
        String middle = Images.middle(fileName);
        user.setMediumAvatar(middle);
        String small = Images.small(fileName);
        user.setSmallAvatar(small);

        userService.updateUser(user);
        return "redirect:/dashboard/user/avatar";
    }

    /**
     * 修改资料
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "profile", method = RequestMethod.GET)
    @RequiresPermissions("dashboard-user-profile")
    public String profile(Model model) {
        ShiroUser shiroUser = userService.getShiroUser();
        User user = userService.getUser(shiroUser.getId());

        model.addAttribute("user", user);
        return PATH_PROFILE;
    }

    /**
     * 保存资料修改
     *
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/profile", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("dashboard-user-profile")
    public ValidationResponse profile(@ModelAttribute("user") @Valid User user, BindingResult result) {
        ValidationResponse res = new ValidationResponse();
        if (!result.hasErrors()) {
            try {
                int count = userService.updateUser(user);
                res.setStatus(count == 1 ? AppConstants.SUCCESS : AppConstants.FAIL);
            } catch (Exception e) {
                res.setStatus(AppConstants.FAIL);
                res.setMessage("修改资料失败, 可能是字段长度不符合或用户名重复!");
                log.info("修改资料失败, 可能是字段长度不符合或用户名重复!");
            }
        } else {
            res.setStatus(AppConstants.FAIL);
            res.setMessage("修改资料失败, 可能是字段长度不符合或用户名重复!");
            log.info("修改资料失败, 可能是字段长度不符合或用户名重复!");
        }
        return res;
    }

    /**
     * 修改密码
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "password", method = RequestMethod.GET)
    @RequiresPermissions("dashboard-user-password")
    public String password(Model model) {
        model.addAttribute("id", userService.getShiroUser().getId());
        return PATH_PASSWORD;
    }

    /**
     * 保存密码修改
     *
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/password", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("dashboard-user-password")
    public ValidationResponse password(@ModelAttribute("user") @Valid User user, BindingResult result) {
        ValidationResponse res = new ValidationResponse();
        if (!result.hasErrors()) {
            int count = userService.updateUserPassword(user);
            res.setStatus(count == 1 ? AppConstants.SUCCESS : AppConstants.FAIL);
        } else {
            res.setStatus(AppConstants.FAIL);
            res.setMessage("修改密码失败, 请检查密码长度!");
            log.info("修改密码失败, 请检查密码长度!");
        }
        return res;
    }

}
