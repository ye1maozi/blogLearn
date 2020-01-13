package com.learn.blog.Job.service.impl;

import com.learn.blog.Email.Service.EmailService;
import com.learn.blog.Job.dao.ScheduleJobMapper;
import com.learn.blog.Job.pojo.ScheduleJob;
import com.learn.blog.Job.service.ScheduleJobService;
import com.learn.blog.Job.util.ScheduleUtil;
import com.learn.blog.service.impl.BaseServiceImpl;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ScheduleJobServiceImpl extends BaseServiceImpl<ScheduleJob> implements ScheduleJobService {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    ScheduleJobMapper scheduleJobMapper;

//    @Autowired
//    EmailService emailService;
    @PostConstruct
    public void init() {
        System.out.println("inti schedule");
        List<ScheduleJob> scheduleJobs = scheduleJobMapper.selectAll();
        for (ScheduleJob s:scheduleJobs
             ) {
            CronTrigger cronTrigger = ScheduleUtil.getCronTigger(scheduler, s.getJobId());
            if (cronTrigger == null){
                ScheduleUtil.createScheduleJob(scheduler,s);
            }
        }

//        emailService.sendMail();
    }
}
