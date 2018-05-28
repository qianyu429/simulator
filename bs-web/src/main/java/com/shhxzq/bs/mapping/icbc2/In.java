package com.shhxzq.bs.mapping.icbc2;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by zuodongxiang on 17/6/20.
 */
@Setter
@Getter
public class In extends Transaction {

    //    @XStreamAlias("OnlBatF")
//    private String onlBatf;
//    @XStreamAlias("SettleMode")
//    private String settleMode;
    @XStreamAlias("RecAccNo")
    private String recAccNo;
    @XStreamAlias("RecAccNameCN")
    private String recAccNameCN;
    @XStreamAlias("RecAccNameEN")
    private String recAccNameEN;
//    @XStreamAlias("TotalNum")
//    private String totalNum;
//    @XStreamAlias("TotalAmt")
//    private String totalAmt;
//    @XStreamAlias("SignTime")
//    private String signTime;
//    @XStreamAlias("QryfSeqno")
//    private String qryfSeqno;
//    @XStreamAlias("QrySerialNo")
//    private String qrySerialNo;
//    @XStreamAlias("ReqReserved1")
//    private String reqReserved1;
//    @XStreamAlias("ReqReserved2")
//    private String reqReserved2;

    //    @XStreamAlias("BeginTime")
//    private String beginTime;
//    @XStreamAlias("EndTime")
//    private String endTime;
//    @XStreamAlias("ResultType")
//    private String resultType;
//    @XStreamAlias("NextTag")
//    private String nextTag;
    @XStreamAlias("rd")
    private Rd rd;


}