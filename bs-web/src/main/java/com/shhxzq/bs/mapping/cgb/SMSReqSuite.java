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
public class SMSReqSuite {
    @XStreamAlias("Message")
    private Message message;

    @Setter
    @Getter
    public static class Message{
        @XStreamAsAttribute()
        @XStreamAlias("id")
        private String id;

        @XStreamAlias("CSMSReq")
        private CSMSReq csmsReq;

        @Getter
        @Setter
        public static class CSMSReq{
            @XStreamAsAttribute()
            @XStreamAlias("id")
            private String id;

            @XStreamAlias("version")
            private String version;

            @XStreamAlias("instId")
            private String instId;

            @XStreamAlias("certId")
            private String certId;

            @XStreamAlias("date")
            private String date;

            @XStreamAlias("bankCardNo")
            private String bankCardNo;

            @XStreamAlias("bankCardType")
            private String bankCardType;

            @XStreamAlias("phoneVerification")
            private String phoneVerification;

            @XStreamAlias("mobilePhone")
            private String mobilePhone;

            @XStreamAlias("extension")
            private String extension;

        }
    }
}
