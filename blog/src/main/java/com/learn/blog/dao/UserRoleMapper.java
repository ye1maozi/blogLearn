package com.learn.blog.dao;

import com.learn.blog.pojo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole>{

    void delete(UserRole rp);
}