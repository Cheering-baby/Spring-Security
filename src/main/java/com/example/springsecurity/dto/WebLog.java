package com.example.springsecurity.dto;

import lombok.Data;

/**
 * @Author Ning Meng
 * @Date 2022/4/21 19:19
 **/

@Data
public class WebLog {
    private String description;
    private String username;
    private Long startTime;
    private Long spendTime;
    private String basePath;
    private String uri;
    private String url;
    private String method;
    private String ip;
    private Object parameter;
    private Object result;
}
