package com.learn.blog.controller.backStage;

import com.learn.blog.pojo.User;
import com.learn.blog.service.UserService;
import com.learn.blog.util.Result;
import com.learn.blog.util.Stage.UserEnitity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

@RestController
@RequestMapping("/api/auth/")
public class autoController {

    @Autowired
    UserService userService;
    @PostMapping("login")
    public Object userLoginBack(@RequestBody UserEnitity user)
    {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            subject.logout();
        }

        String name = user.getUsername();
        name = HtmlUtils.htmlEscape(name);
        String password = user.getPassword();
        System.out.printf("login %s %s \n" ,name ,password);

        UsernamePasswordToken token = new UsernamePasswordToken(name,password);
        try{
            subject.login(token);
        }catch (AuthenticationException e){
//            e.printStackTrace();
            return Result.fail("用户名或密码错误");
        }
        user.setToken(subject.getSession().getId().toString());
//        user.setLastlogintime(new Date());
        return Result.success(user);
    }

    @PostMapping("2step-code")
    public Object step2code(){
        return Result.success(0.2);
    }

    @RequestMapping(value = "login",method = RequestMethod.OPTIONS)
    public Object loginOptions(){
        //cors
        return "";
    }
    @PostMapping("register")
    public Object register(@RequestBody User user)
    {
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        User u = userService.selectByUsername(name);
        if (null != u ){
            return Result.fail("用户名已存在");
        }

        String password = user.getPassword();
        System.out.printf("register %s %s\n" ,name ,password);
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
