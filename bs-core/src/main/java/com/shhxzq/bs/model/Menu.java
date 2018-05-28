package com.shhxzq.bs.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "be_simulator_menu")
public class Menu {
    /**
     * 主键, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单描述
     */
    private String description;

    /**
     * 父菜单ID
     */
    private Long pid;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 是否禁用, able:已启用, disable:已禁用
     */
    private String status;

    /**
     * 菜单排序
     */
    private Integer sort;

    /**
     * 菜单图标的class
     */
    private String icon;

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

    @Transient
    private List<Menu> leaf;
}