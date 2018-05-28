package com.shhxzq.bs;

import com.shhxzq.bs.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;

/**
 * @author kangyonggan
 * @since 16/5/24
 */
@SpringBootApplication
@EnableWebMvc
@EnableScheduling
public class Application extends SpringBootServletInitializer {

    public static void main(String args[]) throws IOException {
        final ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        SpringUtil.setApplicationContext(applicationContext);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

}
