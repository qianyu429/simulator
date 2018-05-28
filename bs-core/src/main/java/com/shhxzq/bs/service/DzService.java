package com.shhxzq.bs.service;

import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Dz;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/05/29
 */
public interface DzService {

    List<Dz> searchDzs(int pageNum, Long userId, String bankNo, String transType, String startDate, String endDate);

    List<Dz> findAllDzsByPage(int pageNum);

    Dz getDz(Long id);

    int saveDz(Dz dz);

    int updateDz(Dz dz);

    int deleteDz(Long id);

    void pushCpDz(String transType, Bank bank, String filePath);

    /**
     * 推送民生T+0对账文件
     *
     * @param path
     */
    void pushCMBCT0Dz(String path);

    void pushCiticbDz(String path);
}