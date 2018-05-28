package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.model.User;
import com.shhxzq.bs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 注册
 *
 * @author kangyonggan
 * @since 16/5/16
 */
@Controller
@RequestMapping("register")
public class RegisterController {

    private static final String PATH_ROOT = "web/register";
    private static final String PATH_INDEX = PATH_ROOT + "/index";
    private static final String PATH_REGISTER_RESULT = PATH_ROOT + "/register-result";
    private static final String PATH_PROTOCOL = PATH_ROOT + "/protocol";

    @Autowired
    private UserService userService;

    /**
     * 注册界面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String register() {
        return PATH_INDEX;
    }

    /**
     * 注册
     *
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String register(User user, Model model) {
        model.addAttribute("message", "注册失败, 请稍后重试!");
        try {
            if (userService.saveUserAndRoleAndConfig(user) == 1) {
                model.addAttribute("message", "注册成功");
            }
        } catch (Exception e) {
            return PATH_INDEX;
        }
        return PATH_REGISTER_RESULT;
    }

    /**
     * 注册协议
     *
     * @return
     */
    @RequestMapping(value = "protocol", method = RequestMethod.GET)
    public String protocol() {
        return PATH_PROTOCOL;
    }

}
