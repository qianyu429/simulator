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

}
