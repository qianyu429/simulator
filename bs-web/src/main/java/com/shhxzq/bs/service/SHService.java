package com.shhxzq.bs.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by houjiagang on 2016/11/16.
 */
public interface SHService {

    public String handle(HttpServletRequest request) throws Exception;

    public String handleYQ(HttpServletRequest request) throws Exception;
}
