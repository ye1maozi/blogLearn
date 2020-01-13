package com.learn.blog.Job.controller;

import com.learn.blog.Job.pojo.ScheduleJob;
import com.learn.blog.Job.service.ScheduleJobService;
import com.learn.blog.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleJobController {

    @Autowired
    ScheduleJobService scheduleJobService;

    @GetMapping("")
    public Object getAll(){
        List<ScheduleJob> list = scheduleJobService.selectAll();
        return Result.success(list);
    }


}
