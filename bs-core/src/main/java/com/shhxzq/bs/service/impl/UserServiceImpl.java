package com.shhxzq.bs.service.impl;

import com.github.pagehelper.PageHelper;
import com.shhxzq.bs.constants.RoleEnum;
import com.shhxzq.bs.constants.ShiroConstants;
import com.shhxzq.bs.constants.StatusEnum;
import com.shhxzq.bs.mapper.UserMapper;
import com.shhxzq.bs.model.Config;
import com.shhxzq.bs.model.ShiroUser;
import com.shhxzq.bs.model.User;
import com.shhxzq.bs.service.ConfigService;
import com.shhxzq.bs.service.UserService;
import com.shhxzq.bs.util.Digests;
import com.shhxzq.bs.util.Encodes;
import com.shhxzq.bs.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author kangyonggan
 * @since 2016/05/24
 */
@Service
@Transactional
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ConfigService configService;

    @Override
    public List<User> searchUsers(int pageNow, int pageSize, String status, String username, String realname, String mobile, String email) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtil.isNotEmpty(status)) {
            criteria.andEqualTo("status", status);
        }
        if (StringUtil.isNotEmpty(username)) {
            criteria.andLike("username", "%" + username + "%");
        }
        if (StringUtil.isNotEmpty(realname)) {
            criteria.andLike("realname", "%" + realname + "%");
        }
        if (StringUtil.isNotEmpty(mobile)) {
            criteria.andLike("mobile", "%" + mobile + "%");
        }
        if (StringUtil.isNotEmpty(email)) {
            criteria.andLike("email", "%" + email + "%");
        }
        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNow, pageSize);
        return super.selectByExample(example);
    }


    @Override
    public int saveUser(User user) {
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        return super.insertSelective(user);
    }

    @Override
    public int saveUserAndRoleAndConfig(User user) {
        entryptPassword(user);
        user.setStatus(StatusEnum.ABLE.getStatus());
        if (StringUtil.isEmpty(user.getRealname())) {
            user.setRealname(user.getUsername());
        }
        int count = saveUser(user);
        saveUserRole(user);
        saveUserConfig(user);
        return count;
    }

    @Override
    public int updateUser(User user) {
        return super.updateByPrimaryKeySelective(user);
    }

    @Override
    public int deleteUser(Long id) {
        return super.deleteByPrimaryKey(id);
    }

    @Override
    public void updateUserRoles(Long userId, String roleIds) {
        Map<String, Object> params = new HashMap();
        params.put("userId", userId);
        params.put("roleIds", Arrays.asList(roleIds.split(",")));

        userMapper.deleteUserAllRoles(userId);

        saveUserRoles(userId, roleIds);
    }

    private void saveUserRoles(Long userId, String roleIds) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("roleIds", Arrays.asList(roleIds.split(",")));
        userMapper.insertUserRoles(params);
    }

    @Override
    public int updateUserPassword(User user) {
        User u = getUser(user.getId());
        u.setPassword(user.getPassword());
        entryptPassword(user);
        return updateUser(user);
    }

    @Override
    public List<User> findAllUsersByPage(int pageNum) {
        Example example = new Example(User.class);
        example.setOrderByClause("id desc");
        return super.selectByExample4Page(pageNum, example);
    }

    @Override
    public List<User> findAllUsers() {
        User user = new User();
        user.setStatus(StatusEnum.ABLE.getStatus());

        return super.select(user);
    }

    @Override
    public User getUser(Long id) {
        return super.selectByPrimaryKey(id);
    }

    @Override
    public User findUserByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        return super.selectOne(user);
    }

    @Override
    public ShiroUser getShiroUser() {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return shiroUser;
    }

    private void saveUserRole(User user) {
        userMapper.insertUserRole(user.getId(), RoleEnum.ROLE_USER.getId());
    }

    private void saveUserConfig(User user) {
        Config config = new Config();
        config.setUserId(user.getId());
        config.setGrp("skin");
        config.setK("no-skin");
        config.setVal("#438EB9");

        configService.saveConfig(config);

        config.setGrp("sys");
        config.setK("navbar");
        config.setVal("0");
        configService.saveConfig(config);

        config.setK("sidebar");
        configService.saveConfig(config);

        config.setK("breadcrumbs");
        configService.saveConfig(config);

        config.setK("container");
        configService.saveConfig(config);

        config.setK("hover");
        configService.saveConfig(config);

        config.setK("compact");
        configService.saveConfig(config);

        config.setK("highlight");
        configService.saveConfig(config);
    }

    /**
     * 设定安全的密码，生成随机的salt并经过N次 sha-1 hash
     *
     * @param user
     */
    public void entryptPassword(User user) {
        byte[] salt = Digests.generateSalt(ShiroConstants.SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, ShiroConstants.HASH_INTERATIONS);
        user.setPassword(Encodes.encodeHex(hashPassword));
    }
}
