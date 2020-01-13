package com.learn.blog.realm;

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
        //通过验证
        String userName = (String) principalCollection.getPrimaryPrincipal();
        //通过service获取角色和权限
        Set<String> permissions = permissionService.listPermissions(userName);
        Set<String> roles = roleServuce.listRoleNames(userName);
        //授权对象
        SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
        //把通过service获取到的角色和权限放进去
        s.setStringPermissions(permissions);
        s.setRoles(roles);
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
