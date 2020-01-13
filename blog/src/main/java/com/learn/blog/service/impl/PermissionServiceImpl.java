package com.learn.blog.service.impl;

import com.learn.blog.dao.PermissionMapper;
import com.learn.blog.pojo.Permission;
import com.learn.blog.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;
    @Override
    public boolean needInterceptor(String requestURI) {

        List<Permission> ps = selectAll();
        for (Permission p : ps
             ) {
            if (p.getApiurl().equals(requestURI) ){
                return true;
            }
        }

        return false;
    }

    @Override
    public Set<String> listPermissionURLs(String userName) {
        List<Permission> list = this.listPermissionByUserName(userName);
//        System.out.println(list.toString());
        if (list.isEmpty()){
            return null;
        }
        Set<String> sets = new HashSet<>();
        for (Permission p: list
        ) {
            if (p != null)
            {
                sets.add(p.getApiurl());
            }
        }
        return sets;
    }

    @Override
    public Set<String> listPermissions(String userName) {
        List<Permission> list = this.listPermissionByUserName(userName);
//        System.out.println(list.toString());
        if (list.isEmpty()){
            return null;
        }
        Set<String> sets = new HashSet<>();
        for (Permission p: list
             ) {
            if (p != null)
            {
                sets.add(p.getName());
            }
        }
        return sets;
    }

    public List<Permission> listPermissionByUserName(String name){
        return permissionMapper.listPermissionByUserName(name);
    }

    @Override
    public List<Permission> listPermissionByRole(String name) {
        return permissionMapper.listPermissionByRole(name);
    }
}
