package com.shhxzq.bs.mapping.cgb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wanglili on 17/5/4.
 */
@Setter
@Getter
@XStreamAlias("SoEv")
public class PayReqSuite {
    @XStreamAlias("Message")
    private Message message;

    @Setter
    @Getter
    public static class Message {
        @XStreamAsAttribute()
        @XStreamAlias("id")
        private String id;

        @XStreamAlias("CPReq")
        private CPReq cpReq;

        @Setter
        @Getter
        public static class CPReq {
            @XStreamAlias("version")
            private String version;

            @XStreamAlias("instId")
            private String instId;

            @XStreamAlias("certId")
            private String certId;

            @XStreamAlias("TrxId")
            private String trxId;

            @XStreamAlias("TrxDtTm")
            private String trxDtTm;

            @XStreamAlias("TrxAmt")
            private String trxAmt;

            @XStreamAlias("charge")
            private String charge;

            @XStreamAlias("TrxCcyCd")
            private String trxCcyCd;

            @XStreamAlias("mccCode")
            private String mccCode;

            @XStreamAlias("TrxTp")
            private String trxTp;

            @XStreamAlias("SgnNo")
            private String sgnNo;

            @XStreamAlias("PyerNm")
            private String pyerNm;

            @XStreamAlias("mobile")
            private String mobile;

            @XStreamAlias("PyerAcctId")
            private String pyerAcctId;

            @XStreamAlias("PyerAcctTp")
            private String pyerAcctTp;

            @XStreamAlias("PyerAcctIssrId")
            private String pyerAcctIssrId;

            @XStreamAlias("PyerTrxTrmNo")
            private String pyerTrxTrmNo;

            @XStreamAlias("CardCvn2")
            private String cardCvn2;

            @XStreamAlias("PyeeNm")
            private String pyeeNm;

            @XStreamAlias("CardExprDt")
            private String cardExprDt;

            @XStreamAlias("PyeeAcctId")
            private String pyeeAcctId;

            @XStreamAlias("PyeeAcctTp")
            private String pyeeAcctTp;

            @XStreamAlias("PyeeAcctIssrId")
            private String pyeeAcctIssrId;

            @XStreamAlias("PyeeCtryNo")
            private String pyeeCtryNo;

            @XStreamAlias("PyeeAreaNo")
            private String pyeeAreaNo;

            @XStreamAlias("AcqrrId")
            private String acqrrId;

            @XStreamAlias("PyeeTrxTrmTp")
            private String pyeeTrxTrmTp;

            @XStreamAlias("PyeeTrxTrmNo")
            private String pyeeTrxTrmNo;

            @XStreamAlias("MrchntNm")
            private String mrchntNm;

            @XStreamAlias("MrchntNo")
            private String mrchntNo;

            @XStreamAlias("MrchntTp")
            private String mrchntTp;

            @XStreamAlias("MrchntCertTp")
            private String mrchntCertTp;

            @XStreamAlias("MrchntCertId")
            private String mrchntCertId;

            @XStreamAlias("MrchntCtgyCd")
            private String mrchntCtgyCd;

            @XStreamAlias("OrdrId")
            private String ordrId;

            @XStreamAlias("OrdrDesc")
            private String ordrDesc;

            @XStreamAlias("InstgId")
            private String instgId;

            @XStreamAlias("InstgAcctId")
            private String instgAcctId;

            @XStreamAlias("InstgAcctIssrId")
            private String instgAcctIssrId;

            @XStreamAlias("productType")
            private String productType;

            @XStreamAlias("sendWay")
            private String sendWay;

            @XStreamAlias("productName")
            private String productName;

            @XStreamAlias("MrchntPltfrmNm")
            private String mrchntPltfrmNm;

            @XStreamAlias("netAddress")
            private String netAddress;

            @XStreamAlias("comment")
            private String comment;

            @XStreamAlias("clientIP")
            private String clientIP;

            @XStreamAlias("clientMAC")
            private String clientMAC;

            @XStreamAlias("PyerTrxTrmTp")
            private String pyerTrxTrmTp;

        }
    }
}
