package com.learn.blog.controller;

import com.learn.blog.pojo.User;
import com.learn.blog.service.PermissionService;
import com.learn.blog.service.RoleService;
import com.learn.blog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
public class HelloController {

    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;

    @Autowired
    PermissionService permissionService;

    @GetMapping(value = "hello")
    public Object hello()
    {

        HashMap<String,Set<String>> list = new HashMap<>();
//        Set<String> roles = roleService.listRoleNames("rootName");
        List<User> users = userService.selectAll();
        for (User u: users
             ) {
            Set<String> roles = permissionService.listPermissions(u.getName());
            list.put(u.getName(),roles);
        }
        System.out.println();

        return list;
    }


    @GetMapping(value="mypermission")
    public Object getMyPermission(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            String userName = subject.getPrincipal().toString();
            Set<String> permissions = permissionService.listPermissions(userName);
//            Set<String> roles = roleService.listRoleNames(userName);

            return permissions;
        }
        return "";
    }

    @RequiresRoles(value = {"guest"},logical = Logical.OR)
    @GetMapping(value = "guest")
    public Object guestReq(){

        return "guest";
    }

    @RequiresPermissions(value = "update",logical = Logical.OR)
    @GetMapping(value = "update")
    public Object update(){
        return "update";
    }

    //错误页面展示
    @GetMapping("/unauthorized")
    public String error(){
        return "error ok!";
    }


}
