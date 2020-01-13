package com.learn.blog.service;

import com.learn.blog.pojo.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionService extends BaseService<Permission> {
    boolean needInterceptor(String requestURI);

    Set<String> listPermissionURLs(String userName);


    Set<String> listPermissions(String userName);
    List<Permission> listPermissionByUserName(String name);

    List<Permission> listPermissionByRole(String name);
}
