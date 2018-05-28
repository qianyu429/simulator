package com.shhxzq.bs.model;

import lombok.Data;

/**
 * 返回码
 *
 * @author kangyonggan
 * @since 16/5/19
 */
@Data
public class Code {

    private String sms;
    private String verify;
    private String verifyStat;
    private String sign;
    private String signStat;
    private String pay;
    private String payStat;
    private String redeem;
    private String redeemStat;
    private String interRedeem;
    private String payTongHang; //同行单笔扣款
    private String redeemTongHang;//同行单笔付款
    private String cancel;//解约
    private String predis;//批量扣款
    private String score;//分数
    private String citizenresult;//身份核查结果
    private String faceresult;//人像比对结果
    private String calsignZL;//直联解约
    private String payZL;//直联支付


}
