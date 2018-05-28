package com.shhxzq.bs.service;

import java.util.Map;

/**
 * 同行受托支付
 * Created by zuodongxiang on 17/5/8.
 */
public interface Spdb6THService {
    public String payTongHang(Map<String, String> params);
    public String redeemTongHang(Map<String, String> params);
    public String queryTrade(Map<String, String> params);
}
