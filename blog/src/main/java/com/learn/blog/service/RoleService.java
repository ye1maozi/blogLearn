package com.learn.blog.service;

import com.learn.blog.pojo.Role;

import java.util.List;
import java.util.Set;

public interface RoleService extends BaseService<Role> {
    Set<String> listRoleNames(String userName);
    Role selectByName(String name);

    List<Role> listRoleByUsername(String name);
}
