package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.FaceIdentifyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhenzhen on 17/7/11.
 */
@Component
@Log4j2
public class FaceIdentifyServiceImpl implements FaceIdentifyService {

    @Autowired
    private ConfigService configService;


    @Autowired
    private BankService bankService;


    @Override
    public String handle() {
        log.info("———————人脸识别模拟器开始———————");
        Bank bank = bankService.findBankByBankNo(AppConstants.BANK_NO_FACEIDENTIFY);
        Config configScore = configService.findBankSelectConfig(bank.getCode(), TransTypeEnum.SCORE.getType());
        Config configCitizen = configService.findBankSelectConfig(bank.getCode(), TransTypeEnum.CITIZENRESULT.getType());
        Config configFace = configService.findBankSelectConfig(bank.getCode(), TransTypeEnum.FACERESULT.getType());
        String score = "45";
        String citizenResult = "1";
        String citizenResultText = "一致";
        String faceResult = "1";
        String faceResultText = "系统判断为同一人";
        if (configScore != null) {//判断分数
            score = configScore.getK();
        }

        if (configCitizen != null) {//判断身份核查结果
            citizenResult = configCitizen.getK();
            citizenResultText = configCitizen.getVal();
        }

        if (configFace != null) {//判断人像比对结果
            faceResult = configFace.getK();
            faceResultText = configFace.getVal();
        }

        String response = null;
        response = "{\"FaceCheckResult\":{\"Score\":" +
                score +
                ",\n" +
                "\"FaceResult\":" +
                faceResult +
                ",\n" +
                "\"FaceResultText\":\"" +
                faceResultText +
                "\",\n" +
                "\"CitizenResult\":" +
                citizenResult +
                ",\n" +
                "\"CitizenResultText\":\"" +
                citizenResultText +
                "\",\n" +
                "\"Photo\":\"\"\n" +
                "},\n" +
                "\"ResponseCode\":100,\n" +
                "\"ResponseText\":\"接口调用成功\",\n" +
                "\"Result\":1,\n" +
                "\"ResultText\":\"查询成功\"\n" +
                "}";
        log.info("返回内容为: {}", response);
        log.info("———————人脸识别模拟器结束———————");
        return response;
    }
}
