package com.example.springsecurity.service.Impl;

import cn.hutool.json.JSONUtil;
import com.example.springsecurity.common.api.UserResultCode;
import com.example.springsecurity.common.exception.ApiException;
import com.example.springsecurity.common.exception.ApiExceptionAsserts;
import com.example.springsecurity.common.utils.JwtTokenUtil;
import com.example.springsecurity.dto.AdminUserDetails;
import com.example.springsecurity.entity.User;
import com.example.springsecurity.entity.UserPermission;
import com.example.springsecurity.mapper.UserMapper;
import com.example.springsecurity.service.RedisService;
import com.example.springsecurity.service.UserCacheService;
import com.example.springsecurity.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserCacheService userCacheService;

    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    @Override
    public User queryUserByUsername(String username) {
        User user = null;
        user = userCacheService.getUser(username);
        if(user == null) {
            user = userMapper.queryUserByName(username);
            userCacheService.setUser(user);
        }
        return user;
    }

    @Override
    public String login(String username, String password) {

        ApiExceptionAsserts.hasText(username, UserResultCode.USERNAME_REQUIRED);
        ApiExceptionAsserts.hasText(password, UserResultCode.PASSWORD_REQUIRED);

        String token = null;
        String status = null;

        try {
            AdminUserDetails userDetails = (AdminUserDetails) userDetailsService.loadUserByUsername(username);
            status = userDetails.getStatus();

            if(!passwordEncoder.matches(password, userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        }
        catch (Exception e) {
            e.printStackTrace();
            LOGGER.warn("登录异常: {}", e.getMessage());
        }

        if(status.equals("0")) {
            throw new ApiException(UserResultCode.STATUS_LOCKED);
        }

        return token;
    }

    @Override
    public List<UserPermission> queryUserPermissionById(Integer id) {
        return userMapper.queryUserPermissionById(id);
    }
}
