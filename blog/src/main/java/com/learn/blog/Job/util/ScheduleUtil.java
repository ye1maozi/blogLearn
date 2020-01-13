package com.learn.blog.Job.util;

import com.learn.blog.Job.pojo.ScheduleJob;
import org.quartz.*;

public class ScheduleUtil {
    public final static String JOB_NAME = "TASK_";
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY" ;

    public static void createScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) {
        JobDetail jobDetail = JobBuilder.newJob(ScheduleRunJob.class).withIdentity(getJobKey(scheduleJob.getJobId())).build();

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
                .withMisfireHandlingInstructionDoNothing();

        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.getJobId()))
                .withSchedule(cronScheduleBuilder).build();

        jobDetail.getJobDataMap().put(JOB_PARAM_KEY, scheduleJob);

        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);

            //暂停任务
//            if (scheduleJob.getStatus() == Constant.ScheduleStatus.PAUSE.getValue()) {
////                pauseJob(scheduler, scheduleJob.getJobId());
////            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    private static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }

    private static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(JOB_NAME + jobId);
    }

    public static CronTrigger getCronTigger(Scheduler scheduler, Long jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
//            e.printStackTrace();
        }
        return null;
    }
}
