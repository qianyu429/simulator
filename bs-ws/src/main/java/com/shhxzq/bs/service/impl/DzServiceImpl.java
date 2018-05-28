package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.StatusEnum;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.Dz;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.DzService;
import com.github.pagehelper.PageHelper;
import com.shhxzq.bs.util.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/05/29
 */
@Service
@Transactional
@Log4j2
public class DzServiceImpl extends BaseService<Dz> implements DzService {

    @Autowired
    private BankService bankService;

    @Autowired
    private ConfigService configService;

    @Override
    public int saveDz(Dz dz) {
        dz.setCreatedTime(new Date());
        dz.setUpdatedTime(new Date());
        dz.setStatus(StatusEnum.ABLE.getStatus());
        return super.insertSelective(dz);
    }

    @Override
    public int updateDz(Dz dz) {
        dz.setUpdatedTime(new Date());
        return super.updateByPrimaryKeySelective(dz);
    }

    @Override
    public int deleteDz(Long id) {
        Dz dz = getDz(id);
        FileUtil.deleteFile(dz.getPath());
        return super.deleteByPrimaryKey(id);
    }

    /**
     * 推送银联对账文件
     *
     * @param transType
     * @param bank
     * @param filePath
     * @return
     */
    @Override
    public void pushCpDz(String transType, Bank bank, String filePath) {
        Config ipConfig = configService.findConfigByK("ip");
        Config portConfig = configService.findConfigByK("port");
        String path = "http://" + ipConfig.getVal() + ":" + portConfig.getVal() + "/" + filePath;
        log.info("把对账文件路径{}推给be...", path);
        String url = bank.getPayUrl();
        if (transType.equals(TransTypeEnum.REDEEM.getType())) {
            url = bank.getRedeemUrl();
        }
        String param = "download=" + path + "?filename=" + filePath.substring(filePath.lastIndexOf("/") + 1);
        log.info("推送对账参数url  : {}", url);
        log.info("推送对账参数param: {}", param);
        final String finalUrl = url;
        new Thread() {
            public void run() {
                HttpUtil.sendPost(finalUrl, param);
            }
        }.start();

    }

    @Override
    public void pushCMBCT0Dz(String path) {
        Shell.exec("sh /srv/admin/push-cmbct0-dz.sh");
    }

    @Override
    public List<Dz> searchDzs(int pageNum, Long userId, String bankNo, String transType, String startDate, String endDate) {
        Example example = new Example(Dz.class);
        Example.Criteria criteria = example.createCriteria();
        if (userId != 0) {
            Bank bank = bankService.findBankByAdminUserId(userId);
            if (bank == null) {
                return new ArrayList();
            }
            criteria.andEqualTo("bankNo", bank.getBankNo());
        }
        if (StringUtil.isNotEmpty(bankNo)) {
            criteria.andEqualTo("bankNo", bankNo);
        }
        if (StringUtil.isNotEmpty(transType)) {
            criteria.andEqualTo("transType", transType);
        }
        if (StringUtil.isNotEmpty(startDate)) {
            criteria.andGreaterThanOrEqualTo("createdTime", DateUtil.parasDateFromTemplate8(startDate));
        }
        if (StringUtil.isNotEmpty(endDate)) {
            criteria.andLessThanOrEqualTo("createdTime", DateUtil.parasDateFromTemplate8(endDate));
        }
        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNum, AppConstants.PAGE_SIZE);
        return super.selectByExample(example);
    }

    @Override
    public List<Dz> findAllDzsByPage(int pageNum) {
        Example example = new Example(Dz.class);
        example.setOrderByClause("id desc");
        return super.selectByExample4Page(pageNum, example);
    }

    @Override
    public Dz getDz(Long id) {
        return super.selectByPrimaryKey(id);
    }
}
