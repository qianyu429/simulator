package com.shhxzq.bs.mapping.ceb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 16/9/8.
 */
@Setter
@Getter
@XStreamAlias("Transaction")
public class Transaction {

    @XStreamAlias("SystemHead")
    private SystemHead systemHead;

    @XStreamAlias("TransHead")
    private TransHead transHead;

    @XStreamAlias("TransContent")
    private TransContent transContent;
}
