package com.shhxzq.bs.mapping.icbc2;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by zuodongxiang on 17/6/20.
 */
@Setter
@Getter
public class Eb {

    @XStreamAlias("pub")
    private Pub pub;
    @XStreamAlias("in")
    private In in;
    @XStreamAlias("out")
    private Out out;


}