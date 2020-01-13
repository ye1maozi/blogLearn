package com.learn.blog.service;

import com.learn.blog.dao.BaseMapper;
import com.learn.blog.pojo.User;

public interface UserService extends BaseMapper<User> {

    User selectByUsername(String name);
}
