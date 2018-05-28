package com.shhxzq.bs.service;

import javax.servlet.http.HttpServletRequest;
/**
 * Created by houjiagang on 16/7/15.
 */
public interface SpdbService {

    public String handle(HttpServletRequest request) throws Exception;

    public String handleCompany(HttpServletRequest request) throws Exception;
}
