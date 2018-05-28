package com.shhxzq.bs.mapping.citicb1;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * The body of request message
 * Created by wanglili on 2017/03/03.
 */
@Setter
@Getter
@XStreamAlias("MESSAGE")
public class ApplyNewCardRequest {

    @XStreamAlias("PARTNER_ID")
    private String partnerId;

    @XStreamAlias("PARTNER_APPLY_ID")
    private String partnerApplyId;

    @XStreamAlias("PARTNER_USER_ID")
    private String partnerUserId;

    @XStreamAlias("PARTNER_USER_NICK")
    private String partnerUserNick;

    @XStreamAlias("APPLY_NAME")
    private String applyName;

    @XStreamAlias("APPLY_PINYIN_NAME")
    private String applyPinyinName;

    @XStreamAlias("APPLY_SEX")
    private String applySex;

    @XStreamAlias("APPLY_ID_TYPE")
    private String applyIdType;

    @XStreamAlias("APPLY_ID_NBR")
    private String applyIdNbr;

    @XStreamAlias("MOBILE")
    private String mobile;

    @XStreamAlias("EMAIL")
    private String email;

    @XStreamAlias("HOUSE_PROVINCE_NM")
    private String houseProvinceNm;

    @XStreamAlias("HOUSE_PROVINCE_ID")
    private String houseProvinceId;

    @XStreamAlias("HOUSE_CITY_NM")
    private String houseCityNm;

    @XStreamAlias("HOUSE_CITY_ID")
    private String houseCityId;

    @XStreamAlias("HOUSE_ADDR")
    private String houseAddr;

    @XStreamAlias("HOUSE_ZONE")
    private String houseZone;

    @XStreamAlias("HOUSE_TEL")
    private String houseTel;

    @XStreamAlias("HOUSE_POSTAL_CODE")
    private String housePostalCode;

    @XStreamAlias("CORP_NAME")
    private String corpName;

    @XStreamAlias("CALLING_TYPE")
    private String callingType;

    @XStreamAlias("CORP_PROVINCE_ID")
    private String corpProvinceId;

    @XStreamAlias("CORP_PROVINCE_NM")
    private String corpProvinceNM;

    @XStreamAlias("CORP_CITY_NM")
    private String corpCityNm;

    @XStreamAlias("CORP_CITY_ID")
    private String corpCityId;

    @XStreamAlias("CORP_ADDR")
    private String corpAddr;

    @XStreamAlias("CORP_POSTAL_CODE")
    private String corpPostalCode;

    @XStreamAlias("POSITION")
    private String position;

    @XStreamAlias("POSITION_LEVEL")
    private String positionLevel;

    @XStreamAlias("CORP_KIND")
    private String corpKind;

    @XStreamAlias("CORP_ZONE")
    private String corpZone;

    @XStreamAlias("CORP_TEL")
    private String corpTel;

    @XStreamAlias("CORP_EXTENSION")
    private String corpExtension;

    @XStreamAlias("CONTACT_NAME")
    private String contactName;

    @XStreamAlias("CONTACT_REL")
    private String contactRel;

    @XStreamAlias("CONTACT_ZONE")
    private String contactZone;

    @XStreamAlias("CONTACT_TEL")
    private String contactTel;

    @XStreamAlias("CONTACT_MOBILE")
    private String contactMobile;

    @XStreamAlias("INS_CONTACT_REL")
    private String insContactRel;

    @XStreamAlias("INS_CONTACT_NAME")
    private String insContactName;

    @XStreamAlias("INS_CONTACT_MOBILE")
    private String insContactMobile;

    @XStreamAlias("SEND_CARD_TYPE")
    private String sendCardType;

    @XStreamAlias("APP_ZD_ADDR")
    private String appZdAddr;

    @XStreamAlias("IS_AUTO_REPAY")
    private String isAutoRepay;

    @XStreamAlias("CARD_RMB")
    private String cardRmb;

    @XStreamAlias("CARD_RMB_TYPE")
    private String cardRmbType;

    @XStreamAlias("CARD_WB")
    private String cardWb;

    @XStreamAlias("CARD_WB_TYPE")
    private String cardWbType;

    @XStreamAlias("IS_AUTO_GHHK")
    private String isAutoGhhk;

    @XStreamAlias("ISSUE_DEPART")
    private String issueDepart;

    @XStreamAlias("LIMIT_DATE_START")
    private String limitDateStart;

    @XStreamAlias("LIMIT_DATE_END")
    private String limitDateEnd;

    @XStreamAlias("CARD_PRODUCT_ID")
    private String cardProductId;

    @XStreamAlias("APP_CARD_NBR")
    private String appCardNbr;

    @XStreamAlias("NET_POINT_ID")
    private String netPointId;

    @XStreamAlias("NET_ID")
    private String netId;

    @XStreamAlias("RECOMMEND_NO")
    private String recommendNo;

    @XStreamAlias("RECOMMEND_NO_MGM")
    private String recommendNoMgm;

    @XStreamAlias("FLAG_ID")
    private String flagId;

    @XStreamAlias("JOB_LEVEL")
    private String jobLevel;

    @XStreamAlias("EDUCATION_LEVEL")
    private String educationLevel;

    @XStreamAlias("MEMO")
    private String memo;

    @XStreamAlias("IP_ADDR")
    private String ipAddr;

    @XStreamAlias("MAC_ADDR")
    private String macAddr;

    @XStreamAlias("FAST_SIGN_TYPE")
    private String fastSignType;

    @XStreamAlias("FAST_SIGN_NO")
    private String fastSignNo;

    @XStreamAlias("SUBMIT_DATE")
    private String submitDate;

    @XStreamAlias("SIGNATURE_VAL")
    private String signatureVal;
}


