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
public class SignReqSuite {
    @XStreamAlias("Message")
    private Message message;

    @Setter
    @Getter
    public static class Message{
        @XStreamAsAttribute()
        @XStreamAlias("id")
        private String id;

        @XStreamAlias("CSVReq")
        private CSVReq csvReq;

        @Setter
        @Getter
        public static class CSVReq{
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

            @XStreamAlias("CstmrNm")
            private String cstmrNm;

            @XStreamAlias("BkAcctTp")
            private String bkAcctTp;

            @XStreamAlias("BkAcctNo")
            private String bkAcctNo;

            @XStreamAlias("CardCvn2")
            private String cardCvn2;

            @XStreamAlias("CardExprDt")
            private String cardExprDt;

            @XStreamAlias("IDTp")
            private String idTp;

            @XStreamAlias("IDNo")
            private String idNo;

            @XStreamAlias("MobNo")
            private String mobNo;

            @XStreamAlias("extension")
            private String extension;
        }

    }

}
