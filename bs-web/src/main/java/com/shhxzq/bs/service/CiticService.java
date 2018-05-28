package com.shhxzq.bs.service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by wanglili on 17/03/02.
 * 中信联名卡
 */
public interface CiticService {

    public String handleBasicCheck(HttpServletRequest request) throws Exception;

    public String handleApplyNewCard(HttpServletRequest request) throws Exception;

    public HashMap<String, String> handleApplySecondCard(String partner_apply_id);

}
