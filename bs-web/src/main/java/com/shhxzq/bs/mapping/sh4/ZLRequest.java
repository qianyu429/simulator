package com.shhxzq.bs.mapping.sh4;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by zhangzhenzhen on 17/7/13.
 */
@Setter
@Getter
@XStreamAlias("CSReq")
public class ZLRequest {

    @XStreamAsAttribute()
    @XStreamAlias("id")
    private String id;

    @XStreamAlias("instId")
    private String instId;

    @XStreamAlias("orderNum")
    private String orderNum;

    @XStreamAlias("date")
    private String date;

    @XStreamAlias("signNo")
    private String signNo;

    @XStreamAlias("amount")
    private String amount;

    @XStreamAlias("currency")
    private String currency;

    @XStreamAlias("KoalB64Cert")
    private String KoalB64Cert;

    @XStreamAlias("serialNo")
    private String serialNo;

    @XStreamAlias("type")
    private String type;

    @XStreamAlias("beginDate")
    private String beginDate;

    @XStreamAlias("endDate")
    private String endDate;

    @XStreamAlias("checkSerialNoList")
    private String checkSerialNoList;


}