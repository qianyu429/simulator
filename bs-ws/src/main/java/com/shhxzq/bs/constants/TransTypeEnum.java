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
    INTERREDEEM("interRedeem", "跨行赎回"),
    AUTOASSIGN("autoAssign", "自动签约"),
    AUTOUNASSIGN("autoUnassign", "自动解约");

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
