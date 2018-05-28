package com.shhxzq.bs.util;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Dz;
import com.shhxzq.bs.service.DzService;
import lombok.extern.log4j.Log4j2;

/**
 * Created by xz on 17/3/7.
 * 推送对账文件
 */
@Log4j2
public class PushDzUtil {

    public static void pushDz(Bank bank, String fileName, DzService dzService,Dz dz) {
        // 银联需要推送对账文件
        if (bank.getBankNo().equals(AppConstants.BANK_NO_CP)) {
            String filePath = AppConstants.PATH_DOWNLOAD_DZ + bank.getCode() + "/" + dz.getWorkDay().replaceAll("-", "") + "/" + fileName;
            dzService.pushCpDz(dz.getTransType(), bank, filePath);
        } else if (AppConstants.BANK_NO_CMBCT0.equals(bank.getBankNo())) {
            // 民生T+0
            String path = AppConstants.PATH_DOWNLOAD_DZ + bank.getCode() + "/" + dz.getWorkDay() + "/";
            dzService.pushCMBCT0Dz(path + fileName);
            log.info("民生T+0对账文件已推送");
        } else if (AppConstants.BANK_NO_CITICB.equals(bank.getBankNo())) {
            // 中信信用卡
            String path = AppConstants.PATH_DOWNLOAD_DZ + bank.getCode() + "/" + dz.getWorkDay() + "/";
            dzService.pushCiticbDz(path);
        }
    }
}
