package com.shhxzq.bs.service;

import com.shhxzq.bs.model.Code;
import com.shhxzq.bs.model.Config;

import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2016/05/24
 */
public interface ConfigService {

    List<Config> searchConfigs(int pageNow, String status, String grp, String k, String val);

    Map<String, Object> findConfigsMap(String grp);

    List<Config> findAllConfigsByPage(int pageNum);

    List<Config> findConfigsByGrp(String grp);

    Config getConfig(Long id);

    Config findConfigByGrp(String grp);

    Config findConfigsByGrpAndK(String grp, String k);

    Config findConfigByGrpAndK(String grp, String k);

    Config findConfigByK(String k);

    Config findBankSelectConfig(String bankCode, String type);

    int saveConfig(Config config);

    int updateConfig(Config config);

    int deleteConfig(Long id);

    int importConfig(String grp, String filePath) throws Exception;

    void deleteZXVerifyConfig(String grp);

    String getSerNo();

    void updateConfigs(String bankCode, Code code) throws Exception;

    void updateSelectConfigs(String bankCode, Code code) throws Exception;
}