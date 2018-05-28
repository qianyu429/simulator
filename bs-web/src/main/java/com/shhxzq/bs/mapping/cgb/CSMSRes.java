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
@XStreamAlias("CSMSRes")
public class CSMSRes {
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

    @XStreamAlias("mobilePhone")
    private String mobilePhone;

    @XStreamAlias("extension")
    private String extension;
}
