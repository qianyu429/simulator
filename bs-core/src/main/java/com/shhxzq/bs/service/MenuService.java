package com.shhxzq.bs.service;

import com.shhxzq.bs.model.Menu;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/05/24
 */
public interface MenuService {

    List<Menu> findAllMenusByPage(int pageNum);

    List<Menu> findMenusByUserId(Long userId);

    List<Menu> findDashboardMenusByUserId(Long userId);

    List<Menu> findAdminMenusByUserId(Long userId);

    List<Menu> findDashboardMenusByRoleId(Long roleId);

    List<Menu> findAdminMenusByRoleId(Long roleId);

    List<Menu> findDashboardMenus();

    List<Menu> findAdminMenus();

    Menu getMenu(Long id);

    Menu findMenuByName(String name);

    int saveMenu(Menu menu);

    int updateMenu(Menu menu);

    int deleteMenu(Long id);
}