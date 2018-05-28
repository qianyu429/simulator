package com.shhxzq.bs.constants;

/**
 * 角色枚举
 *
 * @author kangyonggan
 * @since 16/5/10
 */
public enum RoleEnum {

    /**
     * 系统管理员
     */
    ROLE_SYS_ADMIN(1L, "ROLE_SYS_ADMIN"),
    /**
     * 普通用户
     */
    ROLE_USER(2L, "ROLE_USER");

    private final Long id;
    private final String name;

    private RoleEnum(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

}
