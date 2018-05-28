package com.shhxzq.bs.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shhxzq.bs.mapping.songguo.HopRequest;
import com.shhxzq.bs.service.SongguoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * Created by houjiagang on 2016/12/22.
 */
@Component
@Log4j2
public class SongguoServiceImpl implements SongguoService {

      @Override
    public String handle(HopRequest request) {
        switch (request.getInterfaceId()) {
            case "0CIF000002":
                return handle0CIF000002(request);


        }

        return null;
    }

    private String handle0CIF000002(HopRequest request){
        JSONObject map = JSON.parseObject(request.getContent());
        StringBuffer str = new StringBuffer();
        str.append("partnerCustNo=").append(map.get("partnerCustNo")).append("&");
        str.append("custType=").append(map.get("custType")).append("&");
        str.append("name=").append(map.get("name")).append("&");
        str.append("certType=").append(map.get("certType")).append("&");
        str.append("mobile=").append(map.get("mobile")).append("&");
        str.append("riskLevel=").append(map.get("riskLevel")).append("&");
        str.append("remark=").append(map.get("remark")).append("&");
        return str.toString();
    }
}
