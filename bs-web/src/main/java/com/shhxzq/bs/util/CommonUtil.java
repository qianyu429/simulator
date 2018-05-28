package com.shhxzq.bs.util;

import com.shhxzq.bs.model.Config;

import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 16/6/1
 */
public class CommonUtil {
    public static void initMap(Map<String, Object> map) {
        for (String key : map.keySet()) {
            List<Config> list = (List<Config>) map.get(key);
            if (list.isEmpty()) {
                Config config = new Config();
                config.setK("默认");
                list.add(config);
                map.put(key, list);
            }
        }
    }
}
