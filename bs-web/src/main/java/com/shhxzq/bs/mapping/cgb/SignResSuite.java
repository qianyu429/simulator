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
@XStreamAlias("CSVReq")
public class SignResSuite {
    @XStreamAlias("Message")
    private Message message;

    @Setter
    @Getter
    public static class Message {
        @XStreamAsAttribute()
        @XStreamAlias("id")
        private String id;

        @XStreamAlias("CSVRes")
        private CSVRes csvRes;

        @Setter
        @Getter
        public static class CSVRes {
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

            @XStreamAlias("BkTrxId")
            private String bkTrxId;

            @XStreamAlias("SgnNo")
            private String sgnNo;

            @XStreamAlias("BkAcctTp")
            private String bkAcctTp;

            @XStreamAlias("BkAcctNo")
            private String bkAcctNo;

            @XStreamAlias("BkAcctLvl")
            private String bkAcctLvl;

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
