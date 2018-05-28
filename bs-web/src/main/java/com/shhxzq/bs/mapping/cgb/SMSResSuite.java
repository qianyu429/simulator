package com.shhxzq.bs.mapping.cgb;

import com.shhxzq.bs.mapping.ceb.Plain;
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
public class SMSResSuite {

    @XStreamAlias("Message")
    private Message message;

    @Setter
    @Getter
    public static class Message{

        @XStreamAsAttribute()
        @XStreamAlias("id")
        private String id;

        @XStreamAlias("CSMSRes")
        private CSMSRes csmsRes;

    }

    @XStreamAlias("ds:Signature")
    private String signature;

}
