package com.learn.blog.service.impl;

import com.learn.blog.dao.RoleMapper;
import com.learn.blog.pojo.Role;
import com.learn.blog.service.RoleService;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Override
    public Set<String> listRoleNames(String userName) {
        List<Role> list = this.listRoleByUsername(userName);
        Set<String> setroles = new HashSet<>();
        for (Role r:list
             ) {
            if (null != r)
            {
                setroles.add(r.getName());
            }
        }
       if ( setroles.isEmpty())
       {
           setroles.add("guest");
       }
        return setroles;
    }

    @Override
    public Role selectByName(String name) {
        return roleMapper.selectByName(name);
    }

    @Override
    public List<Role> listRoleByUsername(String name) {
        return roleMapper.listRoleByUsername(name);
    }

   }
