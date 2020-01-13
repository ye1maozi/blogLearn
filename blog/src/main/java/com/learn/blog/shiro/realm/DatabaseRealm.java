package com.learn.blog.shiro.realm;

import com.learn.blog.pojo.Permission;
import com.learn.blog.pojo.User;
import com.learn.blog.service.PermissionService;
import com.learn.blog.service.RoleService;
import com.learn.blog.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DatabaseRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RoleService roleServuce;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("permission doGetAuthorizationInfo");
        //授权对象
        SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
        //通过验证
        String userName = (String) principalCollection.getPrimaryPrincipal();
        Set<String> roles = roleServuce.listRoleNames(userName);
        s.setRoles(roles);
        //通过service获取角色和权限
        Set<String> permissions = null;
        if (roles.contains("admin")){
                List<Permission> list =permissionService.selectAll();
                permissions = new HashSet<>();
                Set<String> finalPermissions = permissions;
                list.forEach(p -> {
                    finalPermissions.add(p.getName());
                });

        }else{
            permissions =permissionService.listPermissions(userName);
        }


        //把通过service获取到的角色和权限放进去
        s.setStringPermissions(permissions);

        return s;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("login doGetAuthenticationInfo");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getPrincipal().toString();
        String password= new String( token.getPassword());
        User user = userService.selectByUsername(userName);
        String passwordDB = user.getPassword();
        String salt = user.getSalt();
///如果为空就是账号不存在，如果不相同就是密码错误，但是都抛出AuthenticationException，而不是抛出具体错误原因，免得给破解者提供帮助信息
//        if(null==passwordDB || !passwordDB.equals(password))
//            throw new AuthenticationException();
        //
        SimpleAuthenticationInfo a = new SimpleAuthenticationInfo(userName, passwordDB, ByteSource.Util.bytes(salt), getName());
        return a;
    }
}
