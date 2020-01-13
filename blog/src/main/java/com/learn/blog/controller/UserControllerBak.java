package com.learn.blog.controller;

import com.learn.blog.pojo.User;
import com.learn.blog.service.UserService;
import com.learn.blog.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerBak {

    @Autowired
    UserService userService;
    @GetMapping("user")
    public Object user(){
        User u = userService.selectByUsername("user");
        return Result.success(u);
    }

}
