package com.cscn.uranus.fds.fie;


import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import com.cscn.uranus.fds.fie.eip.endpoint.entity.type.FieEndpointType;
import com.cscn.uranus.fds.fie.eip.endpoint.component.FieOutEndpointStore;
import com.cscn.uranus.fds.fie.eip.endpoint.component.FieUdpOutEndpoint;
import com.cscn.uranus.fds.fie.eip.endpoint.entity.FieEndpoint;
import com.cscn.uranus.fds.fie.eip.endpoint.service.FieEndpointManager;
import com.cscn.uranus.fds.fie.job.component.FieGramGenerator;
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
    defaultFieEndpoint.setName("报文UDP输出");
    defaultFieEndpoint.setType(FieEndpointType.UDP_CLIENT);
    defaultFieEndpoint.setUri("udp://localhost:10000");

    if (!fieEndpointsPersisted.contains(defaultFieEndpoint)) {
      this.fieEndpointManager.add(defaultFieEndpoint);
    }
  }

  private void initializeJobConfig() {
    Set<FieJob> asxJobsPersistedConfig = this.fieJobManager.findAll();
    FieJob defaultFieJob = new FieJob();
    defaultFieJob.setName("定时发送报文");
    defaultFieJob.setParentGroup("defaultGroup");
    defaultFieJob.setClassName("com.cscn.uranus.fds.fie.job.component.AsxOutEndpointJob");
    defaultFieJob.setStatus(FieJobStatus.RUNNING);
    defaultFieJob.setCronExpression("0/1 * * * * ? ");
    if (!asxJobsPersistedConfig.contains(defaultFieJob)) {
      this.fieJobManager.add(defaultFieJob);
    }
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
          .put("ENDPOINT", this.fieOutEndpointStore.findByName(jobConfig.getName()));
      this.fieScheduler.scheduleJob(jobDetail, trigger);
    }

    this.fieScheduler.start();
  }
}
