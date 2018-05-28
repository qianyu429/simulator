package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.StatusEnum;
import com.shhxzq.bs.mapper.MenuMapper;
import com.shhxzq.bs.model.Menu;
import com.shhxzq.bs.service.MenuService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/05/24
 */
@Service
@Transactional
public class MenuServiceImpl extends BaseService<Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public int saveMenu(Menu menu) {
        menu.setCreatedTime(new Date());
        menu.setUpdatedTime(new Date());
        menu.setStatus(StatusEnum.ABLE.getStatus());

        return super.insertSelective(menu);
    }

    @Override
    public int updateMenu(Menu menu) {
        menu.setUpdatedTime(new Date());
        return super.updateByPrimaryKeySelective(menu);
    }

    @Override
    public int deleteMenu(Long id) {
        return super.deleteByPrimaryKey(id);
    }

    @Override
    public List<Menu> findAllMenusByPage(int pageNum) {
        Example example = new Example(Menu.class);
        example.setOrderByClause("id desc");
        return super.selectByExample4Page(pageNum, example);
    }

    @Override
    public List<Menu> findMenusByUserId(Long userId) {
        return menuMapper.selectMenusByUserId(userId);
    }

    @Override
    public List<Menu> findDashboardMenusByUserId(Long userId) {
        List<Menu> menus = menuMapper.selectMenusByUserId(userId);
        List<Menu> wrapList = new ArrayList();
        return recursionLeafList(menus, wrapList, AppConstants.MENU_DASHBOARD);
    }

    @Override
    public List<Menu> findAdminMenusByUserId(Long userId) {
        List<Menu> menus = menuMapper.selectMenusByUserId(userId);
        List<Menu> wrapList = new ArrayList();
        return recursionLeafList(menus, wrapList, AppConstants.MENU_ADMIN);
    }

    @Override
    public List<Menu> findDashboardMenusByRoleId(Long roleId) {
        List<Menu> menus = this.findMenusByRoleId(roleId);
        List<Menu> wrapList = new ArrayList();
        return recursionList(menus, wrapList, AppConstants.MENU_DASHBOARD);
    }

    @Override
    public List<Menu> findAdminMenusByRoleId(Long roleId) {
        List<Menu> menus = this.findMenusByRoleId(roleId);
        List<Menu> wrapList = new ArrayList();
        return recursionList(menus, wrapList, AppConstants.MENU_ADMIN);
    }

    private List<Menu> findMenusByRoleId(Long roleId) {
        return menuMapper.selectMenusByRoleId(roleId);
    }

    @Override
    public List<Menu> findDashboardMenus() {
        List<Menu> menus = findAllMenus();
        List<Menu> wrapList = new ArrayList();
        return recursionList(menus, wrapList, AppConstants.MENU_DASHBOARD);
    }

    @Override
    public List<Menu> findAdminMenus() {
        List<Menu> menus = findAllMenus();
        List<Menu> wrapList = new ArrayList();
        return recursionList(menus, wrapList, AppConstants.MENU_ADMIN);
    }

    private List<Menu> findAllMenus() {
        Example example = new Example(Menu.class);
        example.setOrderByClause("sort asc");
        return super.selectByExample(example);
    }

    @Override
    public Menu getMenu(Long id) {
        return super.selectByPrimaryKey(id);
    }

    @Override
    public Menu findMenuByName(String name) {
        Menu menu = new Menu();
        menu.setName(name);
        return super.selectOne(menu);
    }

    /**
     * 递归构造叶子节点
     *
     * @param from
     * @param toList
     * @param parentId
     * @return
     */
    private List<Menu> recursionLeafList(List<Menu> from, List<Menu> toList, Long parentId) {
        if (CollectionUtils.isEmpty(from)) {
            return null;
        }

        for (int i = 0; i < from.size(); i++) {
            Menu menu = from.get(i);
            if (parentId.equals(menu.getPid())) {
                List<Menu> leaf = new ArrayList<Menu>();
                menu.setLeaf(leaf);
                toList.add(menu);
                recursionLeafList(from, leaf, menu.getId());
            }
        }
        return toList;
    }

    /**
     * 递归找出 parentId 下边的所有子节点
     *
     * @param from
     * @param toList
     * @param parentId
     * @return
     */
    private List<Menu> recursionList(List<Menu> from, List<Menu> toList, Long parentId) {

        if (CollectionUtils.isEmpty(from)) {
            return null;
        }

        for (int i = 0; i < from.size(); i++) {
            Menu menu = from.get(i);
            if (parentId.equals(menu.getPid())) {
                toList.add(menu);
                recursionList(from, toList, menu.getId());
            }
        }
        return toList;
    }
}
