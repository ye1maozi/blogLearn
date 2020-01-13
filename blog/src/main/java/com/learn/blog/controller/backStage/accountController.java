package com.learn.blog.controller.backStage;

import com.learn.blog.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account/")
public class accountController {

    @PostMapping("sms")
    public Object getMsm(){
        //获得消息数量
        return Result.success(1);
    }
}
