package com.shhxzq.bs.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "be_simulator_dz")
public class Dz {
    /**
     * 主键, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 银行编号
     */
    @Column(name = "bank_no")
    private String bankNo;

    /**
     * 对账文件路径
     */
    private String path;

    /**
     * 交易类型
     */
    @Column(name = "trans_type")
    private String transType;

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

    @Transient
    private String workDay;
}