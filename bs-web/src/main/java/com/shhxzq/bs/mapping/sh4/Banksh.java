package com.shhxzq.bs.mapping.sh4;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by zhangzhenzhen on 17/7/14.
 */
@Setter
@Getter
@XStreamAlias("Banksh")
public class Banksh {
    @XStreamAlias("Message")
    private Message message;


}
