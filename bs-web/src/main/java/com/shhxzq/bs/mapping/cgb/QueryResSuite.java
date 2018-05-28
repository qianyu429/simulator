package com.shhxzq.bs.mapping.cgb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wanglili on 17/5/4.
 */
@Getter
@Setter
@XStreamAlias("SoEv")
public class QueryResSuite {
    @XStreamAlias("Message")
    private Message message;

    @Setter
    @Getter
    public static class Message {
        @XStreamAsAttribute()
        @XStreamAlias("id")
        private String id;

        @XStreamAlias("STQRes")
        private STQRes stqRes;

        @Setter
        @Getter
        public static class STQRes {
            @XStreamAlias("version")
            private String version;

            @XStreamAlias("instId")
            private String instId;

            @XStreamAlias("certId")
            private String certId;

            @XStreamAlias("TrxId")
            private String trxId;

            @XStreamAlias("date")
            private String date;

            @XStreamAlias("OriTrxId")
            private String oriTrxId;

            @XStreamAlias("type")
            private String type;

            @XStreamAlias("signNo")
            private String signNo;

            @XStreamAlias("OriBkTrxId")
            private String oriBkTrxId;

            @XStreamAlias("originalSerialNo")
            private String originalSerialNo;

            @XStreamAlias("originalDate")
            private String originalDate;

            @XStreamAlias("TrxAmt")
            private String TrxAmt;

            @XStreamAlias("charge")
            private String charge;

            @XStreamAlias("TrxCcyCd")
            private String TrxCcyCd;

            @XStreamAlias("OriBizSts")
            private String oriBizSts;

            @XStreamAlias("SysRtnCd")
            private String sysRtnCd;

            @XStreamAlias("SysRtnDesc")
            private String sysRtnDesc;

            @XStreamAlias("BizStsCd")
            private String bizStsCd;

            @XStreamAlias("BizStsDesc")
            private String bizStsDesc;

            @XStreamAlias("extension")
            private String extension;

        }
    }

    @XStreamAlias("ds:Signature")
    private String signature;
}
