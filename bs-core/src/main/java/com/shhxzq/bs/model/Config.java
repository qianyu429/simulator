package com.shhxzq.bs.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "be_simulator_config")
public class Config {
    /**
     * 主键, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 配置所属的用户, 0:代表默认
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 组
     */
    private String grp;

    /**
     * 键
     */
    private String k;

    /**
     * 值
     */
    private String val;

    /**
     * 是否禁用, able:已启用, disable:已禁用
     */
    private String status;

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