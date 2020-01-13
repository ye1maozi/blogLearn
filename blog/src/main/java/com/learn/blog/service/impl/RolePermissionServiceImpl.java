package com.learn.blog.service.impl;

import com.learn.blog.dao.RolePermissionMapper;
import com.learn.blog.pojo.RolePermission;
import com.learn.blog.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermission> implements RolePermissionService {
    @Autowired
    RolePermissionMapper rolePermissionMapper;
    @Override
    public void delete(RolePermission rp) {
        rolePermissionMapper.delete(rp);
    }
}
