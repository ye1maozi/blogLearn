package com.learn.blog.shiro;

import com.learn.blog.util.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShiroController {

    @RequestMapping(value = "unauth")
    public Object unauth() {

        return Result.fail("权限不足");
    }

}
