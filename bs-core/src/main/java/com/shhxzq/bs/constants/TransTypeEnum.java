package com.shhxzq.bs.constants;

/**
 * 交易类型枚举
 *
 * @author kangyonggan
 * @since 16/5/10
 */
public enum TransTypeEnum {

    VERIFY("verify", "鉴权"),
    PAY("pay", "申购"),
    REDEEM("redeem", "赎回"),
    CANCEL("cancel", "解约"),
    PREDIS("predis","批扣"),
    SIGN("sign", "签约"),
    INTERREDEEM("interRedeem", "跨行赎回"),
    AUTOASSIGN("autoAssign", "自动签约"),
    AUTOUNASSIGN("autoUnassign", "自动解约"),
    SCORE("score", "分数"),
    CITIZENRESULT("citizenresult", "身份核查结果"),
    FACERESULT("faceresult", "人像比对结果");



    private final String type;
    private final String value;

    private TransTypeEnum(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

}
