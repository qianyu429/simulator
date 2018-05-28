package com.shhxzq.bs.mapping.icbc2;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by zuodongxiang on 17/6/20.
 */
@Setter
@Getter
@XStreamAlias("rd")
public class Rd {

    @XStreamAlias("iSeqno")
    private String iseqno;
    @XStreamAlias("PayAccNo")
    private String payAccNo;
    @XStreamAlias("PayAccNameCN")
    private String payAccNameCN;
    @XStreamAlias("PayAccNameEN")
    private String payAccNameEN;
    @XStreamAlias("PayBranch")
    private String payBranch;
    @XStreamAlias("Portno")
    private String portno;
    @XStreamAlias("ContractNo")
    private String contractNo;

    @XStreamAlias("PayAmt")
    private String payAmt;
    @XStreamAlias("UseCode")
    private String useCode;
    @XStreamAlias("UseCN")
    private String useCN;
    @XStreamAlias("EnSummary")
    private String enSummary;
    @XStreamAlias("PostScript")
    private String postScript;
    @XStreamAlias("Ref")
    private String ref;
    @XStreamAlias("Oref")
    private String oref;
    @XStreamAlias("ERPSqn")
    private String erpsqn;
    @XStreamAlias("BusCode")
    private String busCode;
    @XStreamAlias("ERPcheckno")
    private String erpcheckno;
    @XStreamAlias("CrvouhType")
    private String crvouhType;
    @XStreamAlias("CrvouhName")
    private String crvouhName;
    @XStreamAlias("CrvouhNo")
    private String crvouhNo;
    @XStreamAlias("ReqReserved3")
    private String reqReserved3;
    @XStreamAlias("ReqReserved4")
    private String reqReserved4;

    @XStreamAlias("BankSeq")
    private String bankSeq;

    @XStreamAlias("CurfSeqno")
    private String curfSeqno;

    @XStreamAlias("Pcode")
    private String pcode;

    @XStreamAlias("UserAcc")
    private String userAcc;

    @XStreamAlias("UserName")
    private String userName;

    @XStreamAlias("IdType")
    private String idType;

    @XStreamAlias("IdCode")
    private String idCode;

    @XStreamAlias("MobilePhone")
    private String mobilePhone;

    @XStreamAlias("TotalAmount")
    private String totalAmount;

    @XStreamAlias("BusType")
    private String busType;
    @XStreamAlias("Summary")
    private String summary;
    @XStreamAlias("Status")
    private String status;

    @XStreamAlias("BankRem")
    private String bankRem;

    @XStreamAlias("RepReserved1")
    private String repReserved1;

    @XStreamAlias("RepReserved2")
    private String repReserved2;

    @XStreamAlias("RecAccNo")
    private String recAccNo;

    @XStreamAlias("RecAccNameCN")
    private String recAccNameCN;

    @XStreamAlias("RecAccNameEN")
    private String recAccNameEN;
    @XStreamAlias("CurrType")
    private String currType;
    @XStreamAlias("RecBankNo")
    private String recBankNo;
    @XStreamAlias("OrderNo")
    private String orderNo;

    @XStreamAlias("Result")
    private String result;

    @XStreamAlias("iRetCode")
    private String iRetCode;

    @XStreamAlias("iRetMsg")
    private String iRetMsg;

    @XStreamAlias("QryiSeqno")
    private String qryiSeqno;

    @XStreamAlias("QryOrderNo")
    private String qryOrderNo;


    @XStreamAlias("SerialNo")
    private String serialNo;

    @XStreamAlias("fSeqno")
    private String fSeqno;

    @XStreamAlias("OnlBatF")
    private String onlBatF;

    @XStreamAlias("SettleMode")
    private String settleMode;

    @XStreamAlias("instrRetCode")
    private String instrRetCode;
    @XStreamAlias("instrRetCode")
    private String instrRetMsg;


}