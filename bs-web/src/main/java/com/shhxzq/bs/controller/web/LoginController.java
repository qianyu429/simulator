package com.shhxzq.bs.controller.web;

import com.shhxzq.bs.model.User;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录
 *
 * @author kangyonggan
 * @since 16/5/15
 */
@Controller
@RequestMapping
@Log4j2
public class LoginController {

    private static final String PATH_ROOT = "web/login";
    private static final String PATH_INDEX = PATH_ROOT + "/index";
    private static final String PATH_FORGET = PATH_ROOT + "/forget";

    /**
     * 登录界面
     *
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String index() {
        return PATH_INDEX;
    }

    /**
     * 登录
     *
     * @param user
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(User user, Model model, HttpServletRequest request) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        log.info("Authenticating {}", token.getUsername());
        final Subject subject = SecurityUtils.getSubject();

        try {
            //会调用 ShiroDbRealm 的认证方法 doGetAuthenticationInfo(AuthenticationToken)
            subject.login(token);
        } catch (UnknownAccountException uae) {
            model.addAttribute("message", "该账号不存在！");
            return PATH_INDEX;
        } catch (IncorrectCredentialsException ice) {
            model.addAttribute("message", "密码错误，请重新输入！");
            return PATH_INDEX;
        } catch (LockedAccountException lae) {
            model.addAttribute("message", "账号已禁用！");
            return PATH_INDEX;
        } catch (Exception e) {
            model.addAttribute("message", "未知错误，请联系管理员。");
            log.error("登录未知错误", e);
            return PATH_INDEX;
        }

        SavedRequest savedRequest = WebUtils.getSavedRequest(request);
        // 获取保存的URL
        if (savedRequest == null || savedRequest.getRequestUrl() == null) {
            return "redirect:/dashboard";
        }
        return "redirect:" + savedRequest.getRequestUrl();
    }

    /**
     * 注销
     *
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout() {
        final Subject subject = SecurityUtils.getSubject();
        log.info("logout {}", subject.getPrincipal());
        subject.logout();
        return "redirect:login";
    }

    /**
     * 忘记密码界面
     *
     * @return
     */
    @RequestMapping(value = "forget", method = RequestMethod.GET)
    public String forget() {
        return PATH_FORGET;
    }

}
