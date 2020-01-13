package com.learn.blog.service.impl;

import com.learn.blog.dao.UserMapper;
import com.learn.blog.pojo.Role;
import com.learn.blog.pojo.User;
import com.learn.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional //事物管理
public class UserServiceImpl  extends BaseServiceImpl<User> implements UserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public User selectByUsername(String name) {
        return userMapper.selectByUsername(name);
    }

}
