package com.shhxzq.bs.mapping.sh;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 2016/11/15.
 */
@Setter
@Getter
@XStreamAlias("Message")
public class Message {

    @XStreamAsAttribute()
    @XStreamAlias("id")
    private String id;

    @XStreamAlias("CSReq")
    private com.shhxzq.bs.mapping.sh.Request request;

    @XStreamAlias("CSRes")
    private Response response;

    @XStreamAlias("Signature")
    private String signature;
}
