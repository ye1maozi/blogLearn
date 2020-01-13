package com.learn.blog.service;

import com.learn.blog.pojo.RolePermission;

public interface RolePermissionService extends BaseService<RolePermission> {
    void delete(RolePermission rp);
}
