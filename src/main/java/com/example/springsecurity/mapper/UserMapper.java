package com.example.springsecurity.mapper;

import com.example.springsecurity.entity.User;
import com.example.springsecurity.entity.UserPermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    @Select("select * from ums_admin where username=#{username}")
    User queryUserByName(@Param("username") String username);

    @Select("select * from ums_permission p where p.id in (select permission_id from ums_admin_permission_relation r where r.admin_id = #{id})")
    List<UserPermission> queryUserPermissionById(@Param("id") Integer id);
}
