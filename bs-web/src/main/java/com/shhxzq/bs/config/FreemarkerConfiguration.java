package com.shhxzq.bs.config;

import com.shhxzq.bs.config.freemarker.BlockDirective;
import com.shhxzq.bs.config.freemarker.ExtendsDirective;
import com.shhxzq.bs.config.freemarker.OverrideDirective;
import com.shhxzq.bs.config.freemarker.SuperDirective;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.*;

/**
 * @author kangyonggan
 * @since 16/5/13
 */
@Configuration
@ConfigurationProperties(prefix = "app")
public class FreemarkerConfiguration extends FreeMarkerAutoConfiguration.FreeMarkerWebConfiguration {

    @Getter
    private List<String> prop = new ArrayList();

    @Value("${server.context-path}")
    private String ctx;

    @Autowired
    private ShiroTags shiroTags;

    @Autowired
    private UserDirective userDirective;

    @Autowired
    private DashboardMenuDirective dashboardMenuDirective;

    @Autowired
    private AdminMenuDirective adminMenuDirective;

    @Override
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = super.freeMarkerConfigurer();
        Map<String, Object> sharedVariables = new HashMap();
        sharedVariables.put("appName", prop.get(0));
        sharedVariables.put("version", prop.get(1));
        sharedVariables.put("author", prop.get(2));
        sharedVariables.put("submit", prop.get(3));
        sharedVariables.put("cancel", prop.get(4));
        sharedVariables.put("ctx", ctx);
        sharedVariables.put("app_user", userDirective);
        sharedVariables.put("app_dashboard_menus", dashboardMenuDirective);
        sharedVariables.put("app_admin_menus", adminMenuDirective);
        sharedVariables.put("block", new BlockDirective());
        sharedVariables.put("extends", new ExtendsDirective());
        sharedVariables.put("override", new OverrideDirective());
        sharedVariables.put("super", new SuperDirective());
        sharedVariables.put("shiro", shiroTags);

        configurer.setFreemarkerVariables(sharedVariables);

        Properties properties = new Properties();
        properties.setProperty("template_update_delay", "10");
        properties.setProperty("defaultEncoding", "UTF-8");
        properties.setProperty("url_escaping_charset", "UTF-8");
        properties.setProperty("locale", "zh_CN");
        properties.setProperty("number_format", "#.##");
        properties.setProperty("time_format", "HH:mm:ss");
        properties.setProperty("date_format", "yyyy-MM-dd");
        properties.setProperty("datetime_format", "yyyy-MM-dd HH:mm:ss");
        properties.setProperty("whitespace_stripping", "true");
        properties.setProperty("auto_import", "common.ftl as c");
        configurer.setFreemarkerSettings(properties);
        configurer.setDefaultEncoding("UTF-8");

        configurer.setTemplateLoaderPath("/WEB-INF/templates/");

        return configurer;
    }

}
