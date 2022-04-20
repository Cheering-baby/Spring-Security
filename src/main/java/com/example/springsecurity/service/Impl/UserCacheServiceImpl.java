package com.example.springsecurity.service.Impl;

import cn.hutool.json.JSONUtil;
import com.example.springsecurity.common.exception.CacheException;
import com.example.springsecurity.entity.User;
import com.example.springsecurity.service.RedisService;
import com.example.springsecurity.service.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserCacheServiceImpl implements UserCacheService {

    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    @Autowired
    private RedisService redisService;

//    @CacheException
    @Override
    public User getUser(String username) {
        String key = REDIS_KEY_PREFIX_AUTH_CODE + username;
        String userStr = redisService.get(key);
        User user = null;
        if(userStr != null) {
            user = JSONUtil.toBean(userStr, User.class);
        }
        return user;
    }

//    @CacheException
    @Override
    public void setUser(User user) {
        String key = REDIS_KEY_PREFIX_AUTH_CODE + user.getUsername();
        redisService.set(key, JSONUtil.toJsonStr(user));
        redisService.expire(key, AUTH_CODE_EXPIRE_SECONDS);
    }
}
