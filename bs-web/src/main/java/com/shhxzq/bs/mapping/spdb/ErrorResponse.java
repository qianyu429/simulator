package com.shhxzq.bs.mapping.spdb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by houjiagang on 16/7/15.
 */
@XStreamAlias("packet")
public class ErrorResponse {
    @XStreamAlias("ErrorCode")
    private String errorCode;

    @XStreamAlias("ErrorMsg")
    private String errorMsg;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
