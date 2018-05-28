package com.shhxzq.bs.service;

import java.text.ParseException;
import java.util.Map;

/**
 * Created by zuodongxiang on 17/6/5.
 * 民生网关
 */
public interface CmbcGwService {
    String verify(String param);
    String pay(String param);
    String query(String param) throws ParseException;
}
