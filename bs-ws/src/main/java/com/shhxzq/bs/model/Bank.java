package com.shhxzq.bs.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "be_simulator_bank")
public class Bank {
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
     * 银行名称
     */
    @Column(name = "bank_name")
    private String bankName;

    /**
     * 商户号
     */
    @Column(name = "mer_id")
    private String merId;

    /**
     * 银行代码
     */
    private String code;

    /**
     * 代扣对账文件第一行模板
     */
    @Column(name = "pay_head")
    private String payHead;

    /**
     * 代扣对账文件从第二行起的行模板
     */
    @Column(name = "pay_template")
    private String payTemplate;

    /**
     * 代扣对账回调地址
     */
    @Column(name = "pay_url")
    private String payUrl;

    /**
     * 代付对账文件第一行模板
     */
    @Column(name = "redeem_head")
    private String redeemHead;

    /**
     * 代付对账文件从第二行起的行模板
     */
    @Column(name = "redeem_template")
    private String redeemTemplate;

    /**
     * 代付对账回调地址
     */
    @Column(name = "redeem_url")
    private String redeemUrl;

    /**
     * 银行管理员ID
     */
    @Column(name = "admin_user_id")
    private Long adminUserId;

    /**
     * 银行管理员姓名
     */
    @Column(name = "admin_realname")
    private String adminRealname;

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