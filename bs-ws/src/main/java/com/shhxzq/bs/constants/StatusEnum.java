package com.shhxzq.bs.constants;

/**
 * 状态枚举
 *
 * @author kangyonggan
 * @since 16/5/10
 */
public enum StatusEnum {

    ABLE("able", "已启用"),
    DISABLE("disable", "已禁用");

    private final String status;
    private final String value;

    private StatusEnum(String status, String value) {
        this.status = status;
        this.value = value;
    }

    public String getStatus() {
        return this.status;
    }

    public String getValue() {
        return this.value;
    }

}
