package com.example.springsecurity.entity;

import lombok.Data;

import java.util.List;

@Data // 简化代码的get ,set, toString
public class User {
    private Integer id;
    private String username;
    private String password;
    private String status;

    private List<UserPermission> permissionList;
}
