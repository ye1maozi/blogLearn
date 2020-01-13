package com.learn.blog.dao;

import com.learn.blog.pojo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission>{

    List<Permission> listPermissionByUserName(String name);

    List<Permission> listPermissionByRole(String name);
}