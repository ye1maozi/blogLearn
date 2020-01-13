package com.learn.blog.dao;

import com.learn.blog.pojo.Role;
import com.learn.blog.pojo.User;

import java.util.List;

public interface UserMapper  extends BaseMapper<User>{
    User selectByUsername(String name);

}