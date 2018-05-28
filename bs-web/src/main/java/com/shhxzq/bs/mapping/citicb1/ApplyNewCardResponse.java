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
public class ApplyNewCardResponse {

    @XStreamAlias("RESULT_CODE")
    private String resultCode;

    @XStreamAlias("RESULT_MSG")
    private String resultMsg;
}
