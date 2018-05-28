package com.shhxzq.bs.mapping.bs;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 2016/11/21.
 */
@Setter
@Getter
@XStreamAlias("root")
public class Request {

    @XStreamAlias("transaction_id")
    private String transactionId;

    @XStreamAlias("partner_fund_no")
    private String partnerFundNo;

    @XStreamAlias("buyer_cert_no")
    private String buyerCertNo;

    @XStreamAlias("input_charset")
    private String inputCharset;

    @XStreamAlias("sign")
    private String sign;

    @XStreamAlias("buyer_name")
    private String buyerName;

    @XStreamAlias("service_version")
    private String serviceVersion;

    @XStreamAlias("acc_time")
    private String accTime;

    @XStreamAlias("partner")
    private String partner;

    @XStreamAlias("ecpay_code")
    private String ecpayCode;

    @XStreamAlias("buyer_cert_type")
    private String buyerCertType;

    @XStreamAlias("mobile_number")
    private String mobileNumber;

    @XStreamAlias("sign_type")
    private String signType;

    @XStreamAlias("charge_type")
    private String chargeType;

    @XStreamAlias("fund_no")
    private String fundNo;

    @XStreamAlias("time_start")
    private String timeStart;

    @XStreamAlias("fee_type")
    private String feeType;

    @XStreamAlias("order_type")
    private String orderType;

    @XStreamAlias("apply_amount")
    private String applyAmount;

    @XStreamAlias("sp_user")
    private String spUser;

    @XStreamAlias("fundshare_type")
    private String fundshareType;

    @XStreamAlias("apply_units")
    private String applyUnits;

    @XStreamAlias("legal_person")
    private String legalPerson;

    @XStreamAlias("address")
    private String address;

    @XStreamAlias("operator_cert_no")
    private String operatorCertNo;

    @XStreamAlias("lic_file_name")
    private String licFileName;

    @XStreamAlias("operator_cert_type")
    private String operatorCertType;

    @XStreamAlias("company_license")
    private String companyLicense;

    @XStreamAlias("lic_file_path")
    private String licFilePath;

    @XStreamAlias("license_end_date")
    private String licenseEndDate;

    @XStreamAlias("operator_name")
    private String operatorName;

    @XStreamAlias("company_name")
    private String companyName;

    @XStreamAlias("operator_mobile_number")
    private String operatorMobileNumber;

    @XStreamAlias("legal_person_cert_type")
    private String legal_person_cert_type;

    @XStreamAlias("legal_person_cert_no")
    private String legal_person_cert_no;

    @XStreamAlias("cancel_type")
    private String cancel_type;

    @XStreamAlias("referserial_id")
    private String referserialId;

    @XStreamAlias("sp_trans_id")
    private String spTransId;

    @XStreamAlias("businesscode")
    private String businessCode;

    @XStreamAlias("to_ecno")
    private String toEcno;

    @XStreamAlias("fundcode")
    private String fundcode;

    @XStreamAlias("to_sp_trans_id")
    private String to_sp_trans_id;

    @XStreamAlias("to_partner_fund_no")
    private String to_partner_fund_no;

    @XStreamAlias("to_sp_user")
    private String to_sp_user;

    @XStreamAlias("transfer_type")
    private String transfer_type;

    @XStreamAlias("company_code")
    private String company_code;

    @XStreamAlias("attach")
    private String attach;

    @XStreamAlias("out_trade_no")
    private String out_trade_no;

}


