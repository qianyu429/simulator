package com.shhxzq.bs.mapping.bs;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 2016/11/21.
 */
@Setter
@Getter
@XStreamAlias("root")
public class Response {

    @XStreamAlias("attach")
    private String attach;

    @XStreamAlias("ecpay_code")
    private String ecpayCode;

    @XStreamAlias("input_charset")
    private String inputCharset;

    @XStreamAlias("modify_time")
    private String modifyTime;

    @XStreamAlias("partner")
    private String partner;

    @XStreamAlias("partner_fund_no")
    private String partnerFundNo;

    @XStreamAlias("retcode")
    private String retcode;

    @XStreamAlias("retmsg")
    private String retmsg;

    @XStreamAlias("service_version")
    private String serviceVersion;

    @XStreamAlias("sign_key_index")
    private String signKeyIndex;

    @XStreamAlias("sign_type")
    private String signType;

    @XStreamAlias("sp_trans_id")
    private String spTransId;

    @XStreamAlias("sp_user")
    private String spUser;

    @XStreamAlias("trade_status")
    private String tradeStatus;

    @XStreamAlias("sign")
    private String sign;

    @XStreamAlias("confirm_units")
    private String confirmUnits;

    @XStreamAlias("fee_type")
    private String feeType;

    @XStreamAlias("fund_date")
    private String fundDate;

    @XStreamAlias("inc_arrive_date")
    private String incArriveDate;

    @XStreamAlias("inc_begin_date")
    private String incBeginDate;

    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    @XStreamAlias("time_end")
    private String timeEnd;

    @XStreamAlias("total_fund_units")
    private String totalFundUnits;

    @XStreamAlias("transaction_id")
    private String transactionId;

    @XStreamAlias("allot_date")
    private String allotDate;

    @XStreamAlias("fundshare_type")
    private String fundshareType;

    @XStreamAlias("oriconfirm_amount")
    private String oriconfirmAmount;

    @XStreamAlias("oriconfirm_units")
    private String oriconfirmUnits;

    @XStreamAlias("referserial_id")
    private String referserialId;



}
