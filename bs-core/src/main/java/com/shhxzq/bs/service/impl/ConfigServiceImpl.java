package com.shhxzq.bs.service.impl;

import com.github.pagehelper.PageHelper;
import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.StatusEnum;
import com.shhxzq.bs.mapper.ConfigMapper;
import com.shhxzq.bs.model.Code;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.ShiroUser;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.UserService;
import com.shhxzq.bs.util.DateUtil;
import com.shhxzq.bs.util.Excel;
import com.shhxzq.bs.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2016/05/24
 */
@Service
@Transactional
public class ConfigServiceImpl extends BaseService<Config> implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private UserService userService;

    @Override
    public int saveConfig(Config config) {
        config.setCreatedTime(new Date());
        config.setUpdatedTime(new Date());
        config.setStatus(StatusEnum.ABLE.getStatus());
        return super.insertSelective(config);
    }

    @Override
    public int updateConfig(Config config) {
        config.setUpdatedTime(new Date());
        return super.updateByPrimaryKeySelective(config);
    }

    @Override
    public int deleteConfig(Long id) {
        return super.deleteByPrimaryKey(id);
    }

    @Override
    public int importConfig(String grp, String filePath) throws Exception {
        // 将excel转为集合
        List<String[]> excelRows = null;
        try {
            excelRows = Excel.excelToList(AppConstants.APP_ROOT + filePath, 2, false);
        } catch (Exception e) {
            throw e;
        }
        if (excelRows.size() > 1) {
            deleteConfigsLikeGrp(grp);
            return createConfigsFromExcel(grp, excelRows);
        } else {
            throw new RuntimeException("模板内容不能为空！");
        }
    }

    private int createConfigsFromExcel(String grp, List<String[]> excelRows) throws Exception {
        int count = 0;
        for (int i = 0; i < excelRows.size(); i++) {
            count++;
            Config config = new Config();
            config.setUserId(0L);
            config.setGrp(grp);
            config.setK(excelRows.get(i)[0].trim());
            if (StringUtil.isEmpty(excelRows.get(i)[1])) {
                throw new RuntimeException("第" + (i + 2) + "行的错误码描述不能为空!!");
            }
            config.setVal(excelRows.get(i)[1].trim().replaceAll(",", "，"));

            if (findConfigByGrpAndK(grp, config.getK()) != null) {
                throw new RuntimeException("第" + (i + 2) + "行的错误码" + config.getK() + "重复!!");
            }

            saveConfig(config);
        }
        return count;
    }

//    @Override
//    public String getSerNo() {
//        Config config = new Config();
//        config.setK("serNo");
//        config.setStatus(StatusEnum.ABLE.getStatus());
//        config = selectOne(config);
//        String serNo = config.getVal();
//        int val = Integer.parseInt(serNo) + 1;
//        // 银联序列号6位
//        if (val > 999999) {
//            val = 100000;
//        }
//        config.setVal(val + "");
//        updateConfig(config);
//        return serNo;
//    }

    @Override
    public String getSerNo() {
        return DateUtil.genSerialNo();
    }

    @Override
    public void updateConfigs(String bankCode, Code code) throws Exception {
        deleteConfigsLikeGrp(bankCode + "-code");

        Class clazz = Code.class;
        Method methods[] = clazz.getDeclaredMethods();

        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get")) {
                String fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
                String val = (String) method.invoke(code);
                if (StringUtil.isNotEmpty(val)) {
                    val = val.replaceAll("：", ":");// 把中文的冒号替换为英文的
                    saveConfigs(bankCode + "-code-" + fieldName, val.trim().split(","));
                }
            }
        }
    }

    @Override
    public void deleteZXVerifyConfig(String bankCode){
        String grp = bankCode + "-verify";
        deleteConfigsLikeGrp(grp);
    }

    @Override
    public void updateSelectConfigs(String bankCode, Code code) throws Exception {
        deleteConfigsLikeGrp(bankCode + "-select");

        Class clazz = Code.class;
        Method methods[] = clazz.getDeclaredMethods();

        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get")) {
                String fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
                String val = (String) method.invoke(code);
                if (!"默认".equals(val)) {
                    Config config = new Config();
                    config.setGrp(bankCode + "-code-" + fieldName);
                    config.setK(val);
                    config.setStatus(StatusEnum.ABLE.getStatus());
                    config = super.selectOne(config);
                    if (config == null) {
                        continue;
                    }

                    config.setGrp(bankCode + "-select-" + fieldName);
                    config.setCreatedTime(new Date());
                    config.setUpdatedTime(new Date());
                    super.save(config);
                }
            }
        }
    }

    private void deleteConfigsLikeGrp(String grp) {
        Example example = new Example(Config.class);
        example.createCriteria().andLike("grp", "%" + grp + "%");

        configMapper.deleteByExample(example);
    }

    /**
     * 批量保存配置
     *
     * @param grp
     * @param keyVals
     */
    private void saveConfigs(String grp, String keyVals[]) {
        for (int i = 0; i < keyVals.length; i++) {
            String key = keyVals[i].trim().split(":")[0].trim();
            String val = keyVals[i].trim().split(":")[1].trim();
            Config config = new Config();
            config.setUserId(0L);
            config.setGrp(grp);
            config.setK(key);
            config.setVal(val);
            config.setStatus(StatusEnum.ABLE.getStatus());
            config.setCreatedTime(new Date());
            config.setUpdatedTime(new Date());

            super.save(config);
        }
    }

    @Override
    public List<Config> searchConfigs(int pageNow, String status, String grp, String k, String val) {
        Example example = new Example(Config.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(status)) {
            criteria.andEqualTo("status", status);
        }
        if (StringUtil.isNotEmpty(grp)) {
            criteria.andLike("grp", "%" + grp + "%");
        }
        if (StringUtil.isNotEmpty(k)) {
            criteria.andLike("k", "%" + k + "%");
        }
        if (StringUtil.isNotEmpty(val)) {
            criteria.andLike("val", "%" + val + "%");
        }
        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNow, AppConstants.PAGE_SIZE);
        return super.selectByExample(example);
    }

    @Override
    public Map<String, Object> findConfigsMap(String grp) {
        Map<String, Object> map = new HashMap();

        Class clazz = Code.class;
        Field[] fields = clazz.getDeclaredFields();

        Config config = new Config();
        for (Field field : fields) {
            config.setGrp(grp + "-" + field.getName());
            List<Config> configs = select(config);
            map.put(field.getName(), configs);
        }
        return map;
    }

    @Override
    public List<Config> findAllConfigsByPage(int pageNum) {
        Example example = new Example(Config.class);
        example.setOrderByClause("id desc");
        return super.selectByExample4Page(pageNum, example);
    }

    @Override
    public List<Config> findConfigsByGrp(String grp) {
        Config config = new Config();
        config.setStatus(StatusEnum.ABLE.getStatus());
        config.setGrp(grp);

        return super.select(config);
    }

    @Override
    public Config getConfig(Long id) {
        return super.selectByPrimaryKey(id);
    }

    @Override
    public Config findConfigByGrp(String grp) {
        Config config = findConfigsByGrpAndK(grp, null);
        if (config == null) {
            config.setUserId(0L);
            config.setGrp("skin");
            config.setStatus(StatusEnum.ABLE.getStatus());
            config = super.selectOne(config);
        }
        return config;
    }

    @Override
    public Config findConfigsByGrpAndK(String grp, String k) {
        ShiroUser user = userService.getShiroUser();
        Long userId = 0L;
        if (user != null) {
            userId = user.getId();
        }

        Config config = new Config();
        config.setUserId(userId);
        config.setStatus(StatusEnum.ABLE.getStatus());
        config.setGrp(grp);
        if (StringUtil.isNotEmpty(k)) {
            config.setK(k);
        }
        return super.selectOne(config);
    }

    @Override
    public Config findConfigByGrpAndK(String grp, String k) {
        Config config = new Config();
        config.setStatus(StatusEnum.ABLE.getStatus());
        config.setGrp(grp);
        config.setK(k);

        return super.selectOne(config);
    }

    @Override
    public Config findConfigByK(String k) {
        Config config = new Config();
        config.setK(k);
        config.setStatus(StatusEnum.ABLE.getStatus());

        return super.selectOne(config);
    }

    @Override
    public Config findBankSelectConfig(String bankCode, String type){
        Config config = new Config();
        config.setGrp(bankCode + "-select-" + type);
        config.setStatus(StatusEnum.ABLE.getStatus());
        return super.selectOne(config);
    }
}
