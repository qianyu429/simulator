package com.shhxzq.bs.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "be_simulator_user")
public class User {
    /**
     * 主键, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐
     */
    private String salt;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 是否禁用, able:已启用, disable:已禁用
     */
    private String status;

    /**
     * 小头像
     */
    @Column(name = "small_avatar")
    private String smallAvatar;

    /**
     * 中头像
     */
    @Column(name = "medium_avatar")
    private String mediumAvatar;

    /**
     * 大头像
     */
    @Column(name = "large_avatar")
    private String largeAvatar;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdTime;

    /**
     * 最后更新时间
     */
    @Column(name = "updated_at")
    private Date updatedTime;
}