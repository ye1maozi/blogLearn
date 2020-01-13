package com.learn.blog.Job.util;

import com.learn.blog.Job.pojo.ScheduleJob;
import com.learn.blog.util.SpringContextUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

public class ScheduleRunnable implements Runnable {

    private Object target;
    private Method method;
    private String params;
    public ScheduleRunnable(ScheduleJob scheduleJob) throws NoSuchMethodException {
        this.target = SpringContextUtils.getBean(scheduleJob.getBeanName());
        this.params = scheduleJob.getParams();

        if (params != null && params != "") {
            this.method = target.getClass().getDeclaredMethod(scheduleJob.getMethodName(), String.class);
        } else {
            this.method = target.getClass().getDeclaredMethod(scheduleJob.getMethodName());
        }
    }

    @Override
    public void run() {
        System.out.println("run test");
        try {
            ReflectionUtils.makeAccessible(method);
            if (params != null && params != "") {
                method.invoke(target, params);
            } else {
                method.invoke(target);
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
