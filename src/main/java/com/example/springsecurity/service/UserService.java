package com.example.springsecurity.service;

import com.example.springsecurity.entity.User;
import com.example.springsecurity.entity.UserPermission;

import java.util.List;

public interface UserService {
    /**
     * 根据username查询用户
     * @param username
     * @return
     */
    User queryUserByUsername(String username);

    /**
     * 登录功能
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);

    List<UserPermission> queryUserPermissionById(Integer id);
}
