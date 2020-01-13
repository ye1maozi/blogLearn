package com.learn.blog.service.impl;

import com.learn.blog.dao.UserRoleMapper;
import com.learn.blog.pojo.UserRole;
import com.learn.blog.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService {

    @Autowired
    UserRoleMapper userRoleMapper;
    @Override
    public void delete(UserRole rp) {
        userRoleMapper.delete(rp);
    }
}
