package com.cscn.uranus.fds.fie.job.component;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FieScheduler {

  private final Logger _logger = LoggerFactory.getLogger(FieScheduler.class);
  private Scheduler scheduler;

  public FieScheduler()  {
    SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    try {
      this.scheduler = schedulerFactory.getScheduler();
    } catch (SchedulerException e) {
      e.printStackTrace();
      _logger.error("AsxScheduler初始化错误!");
    }
  }

  public void scheduleJob(JobDetail job, Trigger trigger) {
    try {
      this.scheduler.scheduleJob(job,trigger);
    } catch (SchedulerException e) {
      e.printStackTrace();
      _logger.error("AsxScheduler添加任务错误！");
    }
  }

  public void start() {
    try {
      this.scheduler.start();
    } catch (SchedulerException e) {
      e.printStackTrace();
      _logger.error("AsxScheduler启动错误!");
    }
  }

  public void shutdown() {
    try {
      this.scheduler.shutdown(true);
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }
}
