package com.learn.blog.Job.util;

import com.learn.blog.Job.pojo.ScheduleJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ScheduleRunJob extends QuartzJobBean {
    private ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        ScheduleJob scheduleJob = (ScheduleJob) jobExecutionContext.getMergedJobDataMap()
                .get(ScheduleUtil.JOB_PARAM_KEY);
        //任务开始时间
        long startTime = System.currentTimeMillis();
        System.out.println("任务准备执行");
        ScheduleRunnable task = null;

        try {
            task = new ScheduleRunnable( scheduleJob);
            Future<?> future = service.submit(task);
            future.get();

            long times = System.currentTimeMillis() - startTime;
            System.out.println("任务执行完成:" + times);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
