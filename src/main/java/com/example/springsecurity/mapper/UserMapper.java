package com.example.springsecurity.mapper;

import com.example.springsecurity.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    @Select("select * from ums_admin where username=#{username}")
    User queryUserByName(@Param("username") String username);
}
