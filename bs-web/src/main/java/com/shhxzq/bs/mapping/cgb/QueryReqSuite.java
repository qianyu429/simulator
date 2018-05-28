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
public class QueryReqSuite {
    @XStreamAlias("Message")
    private Message message;

    @Setter
    @Getter
    public static class Message {
        @XStreamAsAttribute()
        @XStreamAlias("id")
        private String id;

        @XStreamAlias("STQReq")
        private STQReq stqReq;

        @Setter
        @Getter
        public static class STQReq {
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

            @XStreamAlias("OriTrxId")
            private String oriTrxId;

            @XStreamAlias("type")
            private String type;

            @XStreamAlias("extension")
            private String extension;

        }
    }
}
