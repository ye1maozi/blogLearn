package com.learn.blog.Job.jobs;

import com.learn.blog.Email.Service.EmailService;
import com.learn.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("CheckCommentJob")
public class CheckCommentJob implements JobBase {


    @Autowired
    CommentService commentService;

    @Autowired
    EmailService emailService;
    @Override
    public void run(String param) {
        int nowCount = commentService.Count();

//        emailService.sendMail(nowCount);
    }

}
