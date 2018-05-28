package com.shhxzq.bs.mapping.citicb1;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The body of request message
 * Created by wanglili on 2017/03/03.
 */
@Setter
@Getter
@XmlRootElement
@XStreamAlias("MESSAGE")
public class BasicCheckRequest {

    @XStreamAlias("PARTNER_ID")
    private String partnerId;

    @XStreamAlias("PARTNER_APPLY_ID")
    private String partnerApplyId;

    @XStreamAlias("PARTNER_USER_ID")
    private String partnerUserId;

    @XStreamAlias("PARTNER_USER_NICK")
    private String partnerUserNick;

    @XStreamAlias("APPLY_ID_TYPE")
    private String applyIdType;

    @XStreamAlias("APPLY_ID_NBR")
    private String applyIdNbr;

    @XStreamAlias("APPLY_NAME")
    private String applyName;

    @XStreamAlias("APPLY_PINYIN_NAME")
    private String applyPinyinName;

    @XStreamAlias("MOBILE")
    private String mobile;

    @XStreamAlias("APPLY_SEX")
    private String applySex;

    @XStreamAlias("NET_POINT_ID")
    private String netPointId;

    @XStreamAlias("NET_ID")
    private String netId;

    @XStreamAlias("RECOMMEND_NO")
    private String recommendNo;

    @XStreamAlias("RECOMMEND_NO_MGM")
    private String recommendNoMgm;

    @XStreamAlias("ISSUE_DEPART")
    private String issueDepart;

    @XStreamAlias("LIMIT_DATE_START")
    private String limitDateStart;

    @XStreamAlias("LIMIT_DATE_END")
    private String limitDateEnd;

    @XStreamAlias("FLAG_ID")
    private String flagId;

    @XStreamAlias("CARD_PRODUCT_ID")
    private String cardProductId;

    @XStreamAlias("APP_CARD_NBR")
    private String appCardNbr;

    @XStreamAlias("FAST_SIGN_TYPE")
    private String fastSignType;

    @XStreamAlias("FAST_SIGN_NO")
    private String fastSignNo;

    @XStreamAlias("SUBMIT_DATE")
    private String submitDate;

    @XStreamAlias("IP_ADDR")
    private String ipAddr;

    @XStreamAlias("MAC_ADDR")
    private String macAddr;

    @XStreamAlias("SIGNATURE_VAL")
    private String signatureVal;

}


