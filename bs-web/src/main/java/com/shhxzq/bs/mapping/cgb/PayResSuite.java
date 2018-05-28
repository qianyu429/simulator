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
public class PayResSuite {
    @XStreamAlias("Message")
    private Message message;

    @Setter
    @Getter
    public static class Message {
        @XStreamAsAttribute()
        @XStreamAlias("id")
        private String id;

        @XStreamAlias("CPRes")
        private CPRes cpRes;

        @Setter
        @Getter
        public static class CPRes {
            @XStreamAlias("version")
            private String version;

            @XStreamAlias("instId")
            private String instId;

            @XStreamAlias("certId")
            private String certId;

            @XStreamAlias("TrxId")
            private String trxId;

            @XStreamAlias("PyerAcctId")
            private String pyerAcctId;

            @XStreamAlias("BkTrxId")
            private String bkTrxId;

            @XStreamAlias("TrxAmt")
            private String trxAmt;

            @XStreamAlias("overdraft")
            private String overdraft;

            @XStreamAlias("TrxCcyCd")
            private String trxCcyCd;

            @XStreamAlias("BkSttlDt")
            private String bkSttlDt;

            @XStreamAlias("SysRtnCd")
            private String sysRtnCd;

            @XStreamAlias("SgnNo")
            private String sgnNo;

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
