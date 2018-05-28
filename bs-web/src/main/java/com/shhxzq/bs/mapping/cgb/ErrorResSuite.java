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
public class ErrorResSuite {
    @XStreamAlias("Message")
    private Message message;

    @Setter
    @Getter
    public static class Message {
        @XStreamAsAttribute()
        @XStreamAlias("id")
        private String id;

        @XStreamAlias("Error")
        private Err error;

        @Getter
        @Setter
        public static class Err{
            @XStreamAlias("version")
            private String version;

            @XStreamAlias("instId")
            private String instId;

            @XStreamAlias("certId")
            private String certId;

            @XStreamAlias("SysRtnCd")
            private String sysRtnCd;

            @XStreamAlias("SysRtnDesc")
            private String sysRtnDesc;

            @XStreamAlias("BizStsCd")
            private String bizStsCd;

            @XStreamAlias("BizStsDesc")
            private String bizStsDesc;

        }

    }

    @XStreamAlias("ds:Signature")
    private String signature;
}
