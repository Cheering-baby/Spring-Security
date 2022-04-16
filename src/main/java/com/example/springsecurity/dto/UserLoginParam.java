package com.example.springsecurity.dto;


import lombok.Data;

@Data
public class UserLoginParam {
    private String username;
    private String password;
}
