package com.shhxzq.bs.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "be_simulator_transaction")
public class Transaction {
    /**
     * 主键, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * be流水号
     */
    @Column(name = "be_ser")
    private String beSer;

    /**
     * bs流水号
     */
    @Column(name = "bs_ser")
    private String bsSer;

    /**
     * 商户号
     */
    @Column(name = "mer_id")
    private String merId;

    /**
     * 银行编号
     */
    @Column(name = "bank_no")
    private String bankNo;

    /**
     * 交易账号
     */
    @Column(name = "acco_no")
    private String accoNo;

    /**
     * 交易类型
     */
    @Column(name = "trans_type")
    private String transType;

    /**
     * 交易金额
     */
    private String amount;

    /**
     * 响应码
     */
    @Column(name = "resp_code")
    private String respCode;

    /**
     * 状态码
     */
    @Column(name = "trans_stat")
    private String transStat;

    /**
     * 内部码
     */
    private String stat;

    /**
     * 工作日
     */
    @Column(name = "work_day")
    private String workDay;

    /**
     * 交易时间(be方)
     */
    @Column(name = "send_time")
    private String sendTime;

    /**
     * 是否禁用, able:已启用, disable:已禁用
     */
    private String status;

    /**
     * 创建日期(bs方)
     */
    @Column(name = "created_date")
    private String createdDate;

    /**
     * 创建时间(bs方)
     */
    @Column(name = "created_at")
    private Date createdTime;

    /**
     * 最后更新时间
     */
    @Column(name = "updated_at")
    private Date updatedTime;

    /**
     * 协议号
     */
    @Column(name = "protocolNo")
    private String protocolNo;

    /**
     * 证件号码
     */
    @Column(name = "idNum")
    private String idNum;

    /**
     * 预留字段1
     */
    @Column(name = "memo1")
    private String memo1;

    /**
     * 预留字段2
     */
    @Column(name = "memo2")
    private String memo2;


}