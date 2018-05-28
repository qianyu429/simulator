package com.shhxzq.bs.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;


/**
 * 普通类调用Spring bean对象：
 * 说明：
 * 1、此类需要放到App.java同包或者子包下才能被扫描，否则失效。
 *
 * @author Administrator
 */

@Log4j2
public class SpringUtil {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext ac) {
        applicationContext = ac;
    }

    //通过name获取 Bean.
    public static Object getBean(String name) {
        return applicationContext.getBean(name);

    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

}