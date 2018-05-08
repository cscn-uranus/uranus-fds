package com.cscn.uranus.fds.fie;


import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import com.cscn.uranus.fds.fie.job.entity.type.FieEndpointType;
import com.cscn.uranus.fds.fie.job.component.FieOutEndpointStore;
import com.cscn.uranus.fds.fie.job.component.FieUdpOutEndpoint;
import com.cscn.uranus.fds.fie.job.entity.FieEndpoint;
import com.cscn.uranus.fds.fie.job.service.FieEndpointManager;
import com.cscn.uranus.fds.fie.domain.component.FieGramGenerator;
import com.cscn.uranus.fds.fie.job.component.FieGramOutJob;
import com.cscn.uranus.fds.fie.job.component.FieScheduler;
import com.cscn.uranus.fds.fie.job.entity.FieJob;
import com.cscn.uranus.fds.fie.job.entity.type.FieJobStatus;
import com.cscn.uranus.fds.fie.job.service.FieJobManager;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FieInitializer {

  private final Logger _logger = LoggerFactory.getLogger(FieInitializer.class);
  private final FieEndpointManager fieEndpointManager;
  private final FieJobManager fieJobManager;
  private final FieOutEndpointStore fieOutEndpointStore;
  private final FieGramGenerator fieGramGenerator;
  private final FieScheduler fieScheduler;

  public FieInitializer(
      FieEndpointManager fieEndpointManager,
      FieJobManager fieJobManager,
      FieOutEndpointStore fieOutEndpointStore,
      FieGramGenerator fieGramGenerator,
      FieScheduler fieScheduler) {
    this.fieEndpointManager = fieEndpointManager;
    this.fieJobManager = fieJobManager;
    this.fieOutEndpointStore = fieOutEndpointStore;
    this.fieGramGenerator = fieGramGenerator;
    this.fieScheduler = fieScheduler;
  }

  public void doInitialize() {
    this.initializeEndpointConfig();
    this.initializeJobConfig();

    this.initializeEndpointInstance();

    this.initializeJobInstance();
  }

  private void initializeEndpointConfig() {
    Set<FieEndpoint> fieEndpointsPersisted = this.fieEndpointManager.findAll();
    FieEndpoint defaultFieEndpoint = new FieEndpoint();
    defaultFieEndpoint.setName("测试Udp输出终结点");
    defaultFieEndpoint.setType(FieEndpointType.UDP_CLIENT);
    defaultFieEndpoint.setUri("udp://localhost:10000");

    if (!fieEndpointsPersisted.contains(defaultFieEndpoint)) {
      this.fieEndpointManager.add(defaultFieEndpoint);
    }
    _logger.info("服务终结点配置初始化完成！");
  }

  private void initializeJobConfig() {
    Set<FieJob> asxJobsPersistedConfig = this.fieJobManager.findAll();
    FieJob defaultFieJob = new FieJob();
    defaultFieJob.setName("定时发送报文");
    defaultFieJob.setParentGroup("defaultGroup");
    defaultFieJob.setClassName("com.cscn.uranus.fds.fie.job.component.AsxOutEndpointJob");
    defaultFieJob.setStatus(FieJobStatus.RUNNING);
    defaultFieJob.setCronExpression("0/1 * * * * ?");
    if (!asxJobsPersistedConfig.contains(defaultFieJob)) {
      this.fieJobManager.add(defaultFieJob);
    }
    _logger.info("任务配置初始化完成！");
  }

  private void initializeEndpointInstance() {
    Set<FieEndpoint> fieEndpointsPersisted = this.fieEndpointManager.findAll();
    for (FieEndpoint fieEndpoint : fieEndpointsPersisted) {
      if (fieEndpoint.getType() == FieEndpointType.UDP_CLIENT) {
        String host = this.getHostFromUri(fieEndpoint.getUri());
        int port = this.getPortFromUri(fieEndpoint.getUri());
        FieUdpOutEndpoint fieUdpOutEndpoint = new FieUdpOutEndpoint(fieEndpoint.getName(), host, port);
        this.fieOutEndpointStore.register(fieUdpOutEndpoint);
      }
    }
    _logger.info("服务终结点实例化完成！");
  }

  private String getHostFromUri(String uri) {
    Pattern pattern = Pattern.compile("(udp)+\\:+\\/+\\/+(\\w+)+\\:+(\\d+)");
    Matcher matcher = pattern.matcher(uri);
    if (matcher.find()) {
      return matcher.group(2);
    } else {
      return "";
    }
  }

  private int getPortFromUri(String uri) {
    Pattern pattern = Pattern.compile("(udp)+\\:+\\/+\\/+(\\w+)+\\:+(\\d+)");
    Matcher matcher = pattern.matcher(uri);
    if (matcher.find()) {
      return Integer.valueOf(matcher.group(3));
    } else {
      return 0;
    }
  }

  private void initializeJobInstance() {
    Set<FieJob> asxJobsPersistedConfig = this.fieJobManager.findAll();
    for (FieJob jobConfig : asxJobsPersistedConfig) {
      JobDetail jobDetail = JobBuilder
          .newJob(FieGramOutJob.class)
          .withIdentity(jobConfig.getName(), jobConfig.getParentGroup())
          .build();
      Trigger trigger = newTrigger()
          .withIdentity(jobConfig.getName() + "-Trigger", jobConfig.getParentGroup())
          .withSchedule(cronSchedule(jobConfig.getCronExpression())).build();
      jobDetail.getJobDataMap().put("GENERATOR", this.fieGramGenerator);
      jobDetail.getJobDataMap()
          .put("ENDPOINT", this.fieOutEndpointStore.findByName("测试Udp输出终结点"));
      this.fieScheduler.scheduleJob(jobDetail, trigger);
    }
    _logger.info("任务实例初始化完成！");
    this.fieScheduler.start();
    _logger.info("启动调度器完成！");
  }
}
