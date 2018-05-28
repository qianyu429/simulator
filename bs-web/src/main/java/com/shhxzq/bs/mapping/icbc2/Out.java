package com.shhxzq.bs.mapping.icbc2;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by zuodongxiang on 17/6/20.
 */
@Setter
@Getter
public class Out extends Transaction {

    //    @XStreamAlias("OnlBatF")
//    private String onlBatF;
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
//    @XStreamAlias("RepReserved1")
//    private String repReserved1;
//    @XStreamAlias("RepReserved2")
//    private String repReserved2;
//    @XStreamAlias("rd")
//    private Rd rd;
    private List<Rd> rds;


    @XStreamAlias("RetTotalNum")
    private String retTotalNum;//对账特有字段


}
