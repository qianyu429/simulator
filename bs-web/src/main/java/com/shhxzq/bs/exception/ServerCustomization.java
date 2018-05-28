package com.shhxzq.bs.exception;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.http.HttpStatus;

/**
 * @author kangyonggan
 * @since 16/5/19
 */
public class ServerCustomization extends ServerProperties {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        super.customize(container);
        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
        container.addErrorPages(new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/405"));
    }

}
