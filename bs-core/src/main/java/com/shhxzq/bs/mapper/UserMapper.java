package com.shhxzq.bs.mapper;

import com.shhxzq.bs.model.User;
import com.shhxzq.bs.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserMapper extends MyMapper<User> {

    void insertUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    void deleteUserAllRoles(@Param("userId") Long userId);

    void insertUserRoles(Map<String, Object> params);
}