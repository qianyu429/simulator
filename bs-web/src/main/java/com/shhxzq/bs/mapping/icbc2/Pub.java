package com.shhxzq.bs.mapping.icbc2;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by zuodongxiang on 17/6/20.
 */
@Setter
@Getter
public class Pub {

    @XStreamAlias("TransCode")
    private String transCode;
    @XStreamAlias("CIS")
    private String cis;
    @XStreamAlias("BankCode")
    private String bankCode;
    @XStreamAlias("ID")
    private String id;
    @XStreamAlias("TranDate")
    private String tranDate;
    @XStreamAlias("TranTime")
    private String tranTime;
    @XStreamAlias("fSeqno")
    private String fSeqno;

    @XStreamAlias("SerialNo")
    private String serialNo;

    @XStreamAlias("RetCode")
    private String retCode;

    @XStreamAlias("RetMsg")
    private String retMsg;


}