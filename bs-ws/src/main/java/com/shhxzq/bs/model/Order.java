package com.shhxzq.bs.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by houjiagang on 2016/12/16.
 */
@Data
@Table(name = "th_order")
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "company_no", nullable = false, length = 4)
    private String companyNo;

    @Column(name = "sub_company_no", nullable = false, length = 8)
    private String subCompanyNo;

    @Column(name = "partner_cust_no", nullable = false, length = 20)
    private String partnerCustNo;

    @Column(name = "order_type", length = 1)
    private String orderType;

    @Column(name = "cust_type", length = 1)
    private String custType;

    @Column(name = "name", length = 40)
    private String name;

    @Column(name = "cert_type", length = 2)
    private String certType;

    @Column(name = "cert_no", length = 20)
    private String certNo;

    @Column(name = "mobile", length = 11)
    private String mobile;

    @Column(name = "bank_card_no", length = 32)
    private String bankCardNo;

    @Column(name = "risk_level", length = 1)
    private String riskLevel;

    @Column(name = "remark", length = 256)
    private String remark;

    @Column(name = "serial_no", length = 32)
    private String serialNo;

    @Column(name = "trade_acct", length = 32)
    private String tradeAcct;

    @Column(name = "prod_id", length = 32)
    private String prodId;

    @Column(name = "amount",length = 16)
    private String amount;

    @Column(name = "share", length = 16)
    private String share;

    @Column(name = "payment_type", length = 1)
    private String paymentType;

    @Column(name = "charge_type", length = 1)
    private String chargeType;

    @Column(name = "apkind", length = 3)
    private String apkind;
}
