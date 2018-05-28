package com.shhxzq.bs.mapper;

import com.shhxzq.bs.model.Menu;
import com.shhxzq.bs.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper extends MyMapper<Menu> {
    /**
     * 查找用户所有菜单
     *
     * @param userId
     * @return
     */
    List<Menu> selectMenusByUserId(@Param("userId") Long userId);

    List<Menu> selectMenusByRoleId(@Param("roleId") Long roleId);
}