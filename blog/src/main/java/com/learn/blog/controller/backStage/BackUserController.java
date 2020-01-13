package com.learn.blog.controller.backStage;

import com.learn.blog.pojo.Permission;
import com.learn.blog.pojo.Role;
import com.learn.blog.pojo.User;
import com.learn.blog.pojo.UserRole;
import com.learn.blog.service.PermissionService;
import com.learn.blog.service.RoleService;
import com.learn.blog.service.UserRoleService;
import com.learn.blog.service.UserService;
import com.learn.blog.util.Parse2ChildUtil;
import com.learn.blog.util.Result;
import com.learn.blog.util.Stage.*;
import com.learn.blog.util.UserRoleVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.*;

@RestController
@RequestMapping("/api/user")
public class BackUserController {

    @Autowired
    UserService userService;
    @Autowired
    PermissionService permissionService;

    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RoleService roleService;
    @GetMapping("/info")
    public Object getInfo(){
        UserEnitity userEnitity = new UserEnitity();
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            String userName = subject.getPrincipal().toString();
            userEnitity.setUsername(userName);
            userEnitity.setAvatar("/avatar2.jpg");
            userEnitity.setToken(subject.getSession().getId().toString());

            Set<String> roles = roleService.listRoleNames(userName);
            Set<String> permissions = null;
            if (roles.contains("admin")){
                List<Permission> list =permissionService.selectAll();
                permissions = new HashSet<>();
                Set<String> finalPermissions = permissions;
                list.forEach(p -> {
                    finalPermissions.add(p.getApiurl());
                });

            }else{
                permissions =permissionService.listPermissionURLs(userName);
            }

            RoleEntity role = new RoleEntity();
            role.setRolename("admin");
            role.setRoleDesc("superRoot");
            userEnitity.setRole(role);

            role.setPermissions(permissions);
        }else{

         return Result.fail("没有登录");
        }






        return Result.success(userEnitity);
    }


    @GetMapping("/nav")
    public Object getNav(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            String userName = subject.getPrincipal().toString();
            Set<String> roles = roleService.listRoleNames(userName);
            List<Permission> list = null;
            if (roles.contains("admin")){
               list = permissionService.selectAll();
            }else{
                list = permissionService.listPermissionByUserName(userName);
            }
            return Result.success(list);
        }else{

            List<Permission> perm =  permissionService.selectAll();
            return Result.success(perm);
//            return Result.fail("login");
        }
    }

    @GetMapping("/permission/{name}")
    public Object getPermission(@PathVariable("name")String userName){
        List<Permission> perm = permissionService.listPermissionByUserName(userName);
        return Result.success(Parse2ChildUtil.ParseToChild(perm));
    }

    @GetMapping("/role")
    public Object getAllRole(){
        List<Role> list = roleService.selectAll();
        List<Role> resultList =  Parse2ChildUtil.ParseToChild(list);

        return Result.success(resultList);
    }
    @GetMapping("/role/{name}")
    public Object getUserRole(@PathVariable("name") String name){
        name = HtmlUtils.htmlEscape(name);
        User u = userService.selectByUsername(name);
        if (null == u){
            return Result.fail("用户不存在");
        }
        List<Role> list = roleService.listRoleByUsername(name);
        List<Role> resultList =  Parse2ChildUtil.ParseToChild(list);

        return Result.success(resultList);
    }
    @PostMapping("/role")
    public Object changeUserRole(@RequestBody UserRoleVO vo){
        User role = userService.selectByPrimaryKey(vo.getId());
        if (role == null){
            return Result.fail("null role");
        }
        List<UserRole> list = vo.getChangeList();
        for (UserRole rp:list
        ) {
            rp.setUserid(role.getId());
            if (rp.getNewFlag()){
                userRoleService.insert(rp);
            }else{
                userRoleService.delete(rp);
            }
        }
        //插入
        return Result.success();
    }
    @GetMapping("")
    public Object GetUsers(){
        List<User> user = userService.selectAll();
        return Result.success(user);
    }

    @PostMapping("")
    public Object RegisterUser(@RequestBody User user){
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        User u = userService.selectByUsername(name);
        if (null != u){
            return Result.fail("用户名已存在");
        }

        String password = user.getPassword();
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String algorithnames = "md5";
        String encodedPassword = new SimpleHash(algorithnames,password,salt,times).toString();

        user.setName(name);
        user.setSalt(salt);
        user.setPassword(encodedPassword);

        user.setCreatetime(new Date());
        user.setLastlogintime(new Date());
        userService.insert(user);
        return Result.success(user);
    }
}
