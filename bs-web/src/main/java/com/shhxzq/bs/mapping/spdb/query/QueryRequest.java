package com.shhxzq.bs.mapping.spdb.query;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by houjiagang on 16/7/19.
 */
@XStreamAlias("packet")
public class QueryRequest {

    @XStreamAlias("head")
    private QueryHead head;

    @XStreamAlias("body")
    private QueryBody body;

    public QueryBody getBody() {
        return body;
    }

    public void setBody(QueryBody body) {
        this.body = body;
    }

    public QueryHead getHead() {
        return head;
    }

    public void setHead(QueryHead head) {
        this.head = head;
    }
}
