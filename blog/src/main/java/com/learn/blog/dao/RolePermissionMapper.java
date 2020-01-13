package com.learn.blog.dao;

import com.learn.blog.pojo.RolePermission;

public interface RolePermissionMapper extends BaseMapper<RolePermission>{

    void delete(RolePermission rp);
}