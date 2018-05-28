package com.shhxzq.bs.mapping.sh4;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzhenzhen on 17/7/13.
 */

@Setter
@Getter
@XStreamAlias("CSRes")
public class ZLResponse {

    @XStreamAsAttribute()
    @XStreamAlias("id")
    private String id;

    @XStreamAlias("version")
    private String version;

    @XStreamAlias("instId")
    private String instId;

    @XStreamAlias("orderNum")
    private String orderNum;

    @XStreamAlias("signNo")
    private String signNo;

    @XStreamAlias("serialNo")
    private String serialNo;

    @XStreamAlias("date")
    private String date;

    @XStreamAlias("errorCode")
    private String errorCode;

    @XStreamAlias("type")
    private String type;

    @XStreamAlias("beginDate")
    private String beginDate;

    @XStreamAlias("endDate")
    private String endDate;

    @XStreamAlias("checkSerialNoList")
    private String checkSerialNoList;

    private List<TradeInfo> tradeInfoList = new ArrayList();

}