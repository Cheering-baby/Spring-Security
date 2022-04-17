package com.example.springsecurity.service;

public interface RedisService {
    /**
     * 存储数据
     */
    void set(String key, String value);

    /**
     * 获取数据
     */

    String get(String key);

    /**
     * 设置过期时间
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     */
    void remove(String key);

    /**
     * 自增操作
     */
    Long increment(String key, long delta);
}
