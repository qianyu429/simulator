//package com.shhxzq.bs.exception;
//
//import lombok.extern.log4j.Log4j2;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Properties;
//
///**
// * @author kangyonggan
// * @since 16/5/24
// */
//@Component
//@Log4j2
//public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
//
//    public CustomSimpleMappingExceptionResolver() {
//        setExceptionMappings(new Properties());
//    }
//
//    @Override
//    public void setExceptionMappings(Properties mappings) {
//        mappings.put(".UnauthorizedException", "error/403");
//        mappings.put(".MultipartException", "error/upload-error");
//        mappings.put("java.lang.Exception", "error/500");
//        super.setExceptionMappings(mappings);
//    }
//
//    @Override
//    protected ModelAndView doResolveException(HttpServletRequest request,
//                                              HttpServletResponse response,
//                                              Object handler, Exception ex) {
//        String viewName = determineViewName(ex, request);
//        if (viewName != null) {
//            if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
//                    .getHeader("X-Requested-With") != null && request
//                    .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
//                // 如果不是异步请求
//                Integer statusCode = determineStatusCode(request, viewName);
//                if (statusCode != null) {
//                    applyStatusCodeIfPossible(request, response, statusCode);
//                }
//                log.error(ex.getMessage());
//                ex.printStackTrace();
//                return getModelAndView(viewName, ex, request);
//            } else {
//                try {
//                    PrintWriter writer = response.getWriter();
//                    writer.write(ex.getMessage());
//                    writer.flush();
//                } catch (IOException e) {
//                    logger.error("出错了!", e);
//                }
//                return null;
//
//            }
//        } else {
//            return null;
//        }
//    }
//}
