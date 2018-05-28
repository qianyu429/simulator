package com.shhxzq.bs.config;

import com.shhxzq.bs.config.shiro.SuperTag;
import com.shhxzq.bs.model.Menu;
import com.shhxzq.bs.model.ShiroUser;
import com.shhxzq.bs.service.MenuService;
import com.shhxzq.bs.service.UserService;
import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 16/5/18
 */
@Component
public class AdminMenuDirective extends SuperTag {

    String getProperty(Map params) {
        return getParam(params, "property");
    }

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        ShiroUser user = userService.getShiroUser();
        if (user != null) {
            List<Menu> menus = menuService.findAdminMenusByUserId(user.getId());
            env.setVariable("menus", ObjectWrapper.DEFAULT_WRAPPER.wrap(menus));
        }
        renderBody(env, body);
    }
}
