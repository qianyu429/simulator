package com.shhxzq.bs.mapping.citicb1;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * The body of response message
 * Created by wanglili on 2017/03/03.
 */
@Setter
@Getter
@XStreamAlias("MESSAGE")
public class BasicCheckResponse {

    @XStreamAlias("RESULT_CODE")
    private String resultCode;

    @XStreamAlias("RESULT_MSG")
    private String resultMsg;

    @XStreamAlias("REDIRECT_URL")
    private String redirectUrl;

    @XStreamAlias("REDIRECT_URL_PC")
    private String redirectUrlPc;

}
