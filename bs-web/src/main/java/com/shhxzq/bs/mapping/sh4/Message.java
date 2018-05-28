package com.shhxzq.bs.mapping.sh4;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by zhangzhenzhen on 17/7/14.
 */
@Setter
@Getter
@XStreamAlias("Message")
public class Message {

    @XStreamAsAttribute()
    @XStreamAlias("id")
    private String id;

    @XStreamAlias("CSReq")
    private com.shhxzq.bs.mapping.sh4.ZLRequest zLRequest;

    @XStreamAlias("CSRes")
    private ZLResponse ZLResponse;

    @XStreamAlias("Signature")
    private String signature;
}

