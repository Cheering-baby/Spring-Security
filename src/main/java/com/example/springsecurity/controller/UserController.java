package com.example.springsecurity.controller;

import com.example.springsecurity.common.api.CommonResult;
import com.example.springsecurity.dto.UserLoginParam;
import com.example.springsecurity.entity.User;
import com.example.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @RequestMapping(value = "/queryUserByUsername", method = RequestMethod.GET)
    public CommonResult queryUserByUsername(@RequestParam String username) {
        User user = userService.queryUserByUsername(username);
        return CommonResult.success(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult login(@RequestBody UserLoginParam userLoginParam, HttpServletResponse response) {
        String token = userService.login(userLoginParam.getUsername(), userLoginParam.getPassword());
        if(token == null) {
            return CommonResult.validateFailed("用户名或者密码错误");
        }

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("tokenHeader", tokenHeader);
        tokenMap.put("token", token);
        response.setHeader(tokenHeader, token);
        return CommonResult.success(tokenMap);
    }

}
