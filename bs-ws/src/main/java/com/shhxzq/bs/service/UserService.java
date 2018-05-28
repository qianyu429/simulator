package com.shhxzq.bs.service;

import com.shhxzq.bs.model.ShiroUser;
import com.shhxzq.bs.model.User;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/05/24
 */
public interface UserService {

    List<User> searchUsers(int pageNow, int pageSize, String status, String username, String realname, String mobile, String email);

    List<User> findAllUsersByPage(int pageNum);

    List<User> findAllUsers();

    ShiroUser getShiroUser();

    User getUser(Long id);

    User findUserByUsername(String username);

    int saveUser(User user);

    int saveUserAndRoleAndConfig(User user);

    int updateUser(User user);

    int updateUserPassword(User user);

    int deleteUser(Long userId);

    void updateUserRoles(Long userId, String roles);

}