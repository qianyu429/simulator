package com.shhxzq.bs.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by houjiagang on 16/11/21.
 */
public interface BSService {

    public String handle(HttpServletRequest request, String transCode) throws Exception;
}
