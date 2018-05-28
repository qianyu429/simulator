package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.StatusEnum;
import com.shhxzq.bs.mapper.RoleMapper;
import com.shhxzq.bs.model.Menu;
import com.shhxzq.bs.model.Role;
import com.shhxzq.bs.service.RoleService;
import com.shhxzq.bs.util.Collections3;
import org.apache.commons.lang3.StringUtils;
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
public class RoleServiceImpl extends BaseService<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public int saveRole(Role role) {
        role.setCreatedTime(new Date());
        role.setUpdatedTime(new Date());
        role.setStatus(StatusEnum.ABLE.getStatus());

        return super.insertSelective(role);
    }

    @Override
    public int updateRole(Role role) {
        return super.updateByPrimaryKeySelective(role);
    }

    @Override
    public int deleteRole(Long id) {
        return super.deleteByPrimaryKey(id);
    }

    @Override
    public void updateRoleMenus(Long roleId, String menuIds, List<Menu> menus) {
        if (menus != null && menus.size() > 0) {
            this.deleteRoleMenus(roleId, menus);
        }
        if (StringUtils.isNotBlank(menuIds)) {
            Map<String, Object> params = new HashMap();
            params.put("roleId", roleId);
            params.put("menus", Arrays.asList(menuIds.split(",")));
            roleMapper.insertRoleMenus(params);
        }
    }

    private void deleteRoleMenus(Long roleId, List<Menu> menus) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("roleId", roleId);
        params.put("menuIds", Collections3.extractToList(menus, "id"));
        roleMapper.deleteRoleMenus(params);
    }

    @Override
    public List<Role> findAllRolesByPage(int pageNum) {
        Example example = new Example(Role.class);
        example.setOrderByClause("id desc");
        return super.selectByExample4Page(pageNum, example);
    }

    @Override
    public List<Role> findRolesByUserId(Long userId) {
        return roleMapper.selectRolesByUserId(userId);
    }

    @Override
    public List<Role> findAllRoles() {
        return super.select(new Role());
    }

    @Override
    public Role getRole(Long id) {
        return super.selectByPrimaryKey(id);
    }

    @Override
    public Role findRoleByName(String name) {
        Role role = new Role();
        role.setName(name);
        return super.selectOne(role);
    }
}
