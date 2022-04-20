package com.example.springsecurity.service;

import com.example.springsecurity.entity.User;

public interface UserCacheService {
    User getUser(String username);

    void setUser(User user);
}
