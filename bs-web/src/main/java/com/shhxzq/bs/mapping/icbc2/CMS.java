package com.shhxzq.bs.mapping.icbc2;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by zuodongxiang on 17/6/20.
 */
@Setter
@Getter
@XStreamAlias("CMS")
public class CMS {

    @XStreamAlias("eb")
    private Eb eb;
}
