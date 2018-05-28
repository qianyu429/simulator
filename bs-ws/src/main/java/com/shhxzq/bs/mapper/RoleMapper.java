package com.shhxzq.bs.mapper;

import com.shhxzq.bs.model.Role;
import com.shhxzq.bs.util.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleMapper extends MyMapper<Role> {
    /**
     * 查找用户的所有角色
     *
     * @param userId
     * @return
     */
    List<Role> selectRolesByUserId(Long userId);

    void insertRoleMenus(Map<String, Object> params);

    void deleteRoleMenus(Map<String, Object> params);
}