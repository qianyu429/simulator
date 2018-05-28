package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.ICBC1Service;
import com.shhxzq.bs.util.Base64Util;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zuodongxiang on 17/6/19.
 */
@Component
@Log4j
public class ICBC1ServiceImpl implements ICBC1Service {
    @Autowired
    private BankService bankService;
    @Autowired
    private ConfigService configService;

    @Override
    public String sign(HttpServletRequest request) throws Exception {
        //receive request
        String certData = request.getParameter("certData");
        if (!"<?".equals(certData.substring(0, 2))) {
            certData = Base64Util.base64Decode(certData);
        }
        log.info("Receive the certData is: ");
        log.info(certData);
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_ICBC1);
        Config config = configService.findBankSelectConfig(bank.getCode(), TransTypeEnum.SIGN.getType());
        String retCode = "0";
        String retMsg = "签约成功";
        if (config != null) {
            retCode = config.getK();
            retMsg = config.getVal();
        }
        Map<String, String> paras = getParas(certData);
        String interfaceName = paras.get("interfaceName") != null ? paras.get("interfaceName") : "";
        String interfaceVersion = paras.get("interfaceVersion") != null ? paras.get("interfaceVersion") : "";
        String selserialNo = paras.get("selserialNo") != null ? paras.get("selserialNo") : "";
        String payNo = paras.get("payNo") != null ? paras.get("payNo") : "";
        String selpayType = paras.get("selpayType") != null ? paras.get("selpayType") : "";
        String selcorpId = paras.get("selcorpId") != null ? paras.get("selcorpId") : "";
        String selaccountNo = paras.get("selaccountNo") != null ? paras.get("selaccountNo") : "";
        String regDate = paras.get("regDate") != null ? paras.get("regDate") : "";
        String merCertID = paras.get("merCertID") != null ? paras.get("merCertID") : "";
        String merURL = paras.get("merURL") != null ? paras.get("merURL") : "";
        String notifyData = "interfaceName=" + interfaceName + "&interfaceVersion=" + interfaceVersion + "&selserialNo=" + selserialNo +
                "&payNo=" + payNo + "&selpayType=" + selpayType + "&selcorpId=" + selcorpId + "&corporname=&selaccountNo=" + selaccountNo +
                "&regDate=" + regDate + "&merURL=" + merURL + "&merCertID=" + merCertID +
                "&bankName=icbc&Area_code=&regCardNo=&customerAccountType=&userName=&receiveTime=&sendTime=值&signState="+retCode+"&TranErrorCode="
                + retCode + "&TranErrorMsg=" + retMsg;
        log.info("Send the notifyData is: ");
        log.info(notifyData);
        return notifyData;
    }


    private Map<String, String> getParas(String queryString) {
        String[] split = queryString.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String str : split) {
            String[] split1 = str.split("=");
            if (split1.length > 1) {
                if (split1[0].contains("interfaceName")) {
                    map.put("interfaceName", split1[1]);
                } else {
                    map.put(split1[0], split1[1]);
                }

            } else {
                map.put(split1[0], "");
            }
        }
        return map;
    }
}
