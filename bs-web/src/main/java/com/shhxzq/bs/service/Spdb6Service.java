package com.shhxzq.bs.service;

import java.util.Map;

/**
 * Created by zuodongxiang on 17/4/20.
 */
public interface Spdb6Service {
    public String verify();

    public String deliy();

    public String pay(Map<String, String> params);

    public String queryPay(Map<String, String> params);

    public String redeem(Map<String, String> params);

    public String queryRedeem(Map<String, String> params);


}
