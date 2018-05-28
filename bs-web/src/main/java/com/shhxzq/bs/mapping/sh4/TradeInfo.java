package com.shhxzq.bs.mapping.sh4;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * Created by zhangzhenzhen on 17/7/14.
 */
@Data
@XStreamAlias("TradeInfo")
public class TradeInfo {

    @XStreamAlias("serialNo")
    private String serialNo;

    @XStreamAlias("date")
    private String date;

    @XStreamAlias("signNo")
    private String signNo;

    @XStreamAlias("amount")
    private String amount;

    @XStreamAlias("charge")
    private String charge;

    @XStreamAlias("currency")
    private String currency;

    @XStreamAlias("cardType")
    private String cardType;

    @XStreamAlias("originalSerialNo")
    private String originalSerialNo;

    @XStreamAlias("originalDate")
    private String originalDate;

    @XStreamAlias("status")
    private String status;

}
