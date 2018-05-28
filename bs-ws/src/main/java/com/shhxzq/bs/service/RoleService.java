package com.shhxzq.bs.service;

import com.shhxzq.bs.model.Menu;
import com.shhxzq.bs.model.Role;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/05/24
 */
public interface RoleService {

    List<Role> findAllRolesByPage(int pageNum);

    List<Role> findRolesByUserId(Long userId);

    List<Role> findAllRoles();

    Role getRole(Long id);

    Role findRoleByName(String name);

    int saveRole(Role role);

    int updateRole(Role role);

    int deleteRole(Long id);

    void updateRoleMenus(Long roleId, String menuIds, List<Menu> menus);
}