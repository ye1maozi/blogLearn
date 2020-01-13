package com.learn.blog.Job.test;

import org.springframework.stereotype.Component;

@Component("testBean")
public class TestBean {

    public void testFun(String param){
        System.out.println("定时任务执行开始 ： " + param);

    }
}
