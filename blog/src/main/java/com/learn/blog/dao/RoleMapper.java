package com.learn.blog.dao;

import com.learn.blog.pojo.Role;

import java.util.List;
import java.util.Set;

public interface RoleMapper extends BaseMapper<Role>{

    Role selectByName(String name);

    List<Role> listRoleByUsername(String name);
}