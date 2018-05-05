package com.cscn.uranus.fds.asx;


import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import com.cscn.uranus.fds.asx.domain.component.AsxGramsGenerator;
import com.cscn.uranus.fds.asx.endpoint.AsxEndpointType;
import com.cscn.uranus.fds.asx.endpoint.component.UdpOutEndpoint;
import com.cscn.uranus.fds.asx.endpoint.component.AsxOutEndpointStore;
import com.cscn.uranus.fds.asx.endpoint.entity.AsxEndpoint;
import com.cscn.uranus.fds.asx.endpoint.service.AsxEndpointManager;
import com.cscn.uranus.fds.asx.job.component.AsxGramsOutJob;
import com.cscn.uranus.fds.asx.job.component.AsxScheduler;
import com.cscn.uranus.fds.asx.job.config.AsxJobStatus;
import com.cscn.uranus.fds.asx.job.entity.AsxJobConfig;
import com.cscn.uranus.fds.asx.job.service.AsxJobConfigManager;
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
public class AsxInitializer {

  private final Logger _logger = LoggerFactory.getLogger(AsxInitializer.class);
  private final AsxEndpointManager asxEndpointManager;
  private final AsxJobConfigManager asxJobConfigManager;
  private final AsxOutEndpointStore asxOutEndpointStore;
  private final AsxGramsGenerator asxGramsGenerator;
  private final AsxScheduler asxScheduler;

  public AsxInitializer(
      AsxEndpointManager asxEndpointManager,
      AsxJobConfigManager asxJobConfigManager,
      AsxOutEndpointStore asxOutEndpointStore,
      AsxGramsGenerator asxGramsGenerator,
      AsxScheduler asxScheduler) {
    this.asxEndpointManager = asxEndpointManager;
    this.asxJobConfigManager = asxJobConfigManager;
    this.asxOutEndpointStore = asxOutEndpointStore;
    this.asxGramsGenerator = asxGramsGenerator;
    this.asxScheduler = asxScheduler;
  }

  public void doInitialize() {
    this.initializeEndpointConfig();
    this.initializeJobConfig();

    this.initializeEndpointInstance();

    this.initializeJobInstance();
  }

  private void initializeEndpointConfig() {
    Set<AsxEndpoint> asxEndpointsPersisted = this.asxEndpointManager.findAll();
    AsxEndpoint defaultAsxEndpoint = new AsxEndpoint();
    defaultAsxEndpoint.setName("报文UDP输出");
    defaultAsxEndpoint.setType(AsxEndpointType.UDP_OUT);
    defaultAsxEndpoint.setUri("udp://localhost:10000");

    if (!asxEndpointsPersisted.contains(defaultAsxEndpoint)) {
      this.asxEndpointManager.add(defaultAsxEndpoint);
    }
  }

  private void initializeJobConfig() {
    Set<AsxJobConfig> asxJobsPersistedConfig = this.asxJobConfigManager.findAll();
    AsxJobConfig defaultAsxJobConfig = new AsxJobConfig();
    defaultAsxJobConfig.setName("定时发送报文");
    defaultAsxJobConfig.setParentGroup("defaultGroup");
    defaultAsxJobConfig.setClassName("com.cscn.uranus.fds.asx.job.component.AsxOutEndpointJob");
    defaultAsxJobConfig.setStatus(AsxJobStatus.RUNNING);
    defaultAsxJobConfig.setCronExpression("0/1 * * * * ? ");
    if (!asxJobsPersistedConfig.contains(defaultAsxJobConfig)) {
      this.asxJobConfigManager.add(defaultAsxJobConfig);
    }
  }

  private void initializeEndpointInstance() {
    Set<AsxEndpoint> asxEndpointsPersisted = this.asxEndpointManager.findAll();
    for (AsxEndpoint asxEndpoint : asxEndpointsPersisted) {
      if (asxEndpoint.getType() == AsxEndpointType.UDP_OUT) {
        String host = this.getHostFromUri(asxEndpoint.getUri());
        int port = this.getPortFromUri(asxEndpoint.getUri());
        UdpOutEndpoint udpOutEndpoint = new UdpOutEndpoint(asxEndpoint.getName(),host, port);
        this.asxOutEndpointStore.add(udpOutEndpoint);
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
    Set<AsxJobConfig> asxJobsPersistedConfig = this.asxJobConfigManager.findAll();
    for (AsxJobConfig jobConfig : asxJobsPersistedConfig) {
      JobDetail jobDetail = JobBuilder
          .newJob(AsxGramsOutJob.class)
          .withIdentity(jobConfig.getName(), jobConfig.getParentGroup())
          .build();
      Trigger trigger = newTrigger().withIdentity(jobConfig.getName()+"-Trigger", jobConfig.getParentGroup())
          .withSchedule(cronSchedule(jobConfig.getCronExpression())).build();
      jobDetail.getJobDataMap().put("GENERATOR",this.asxGramsGenerator);
      jobDetail.getJobDataMap().put("ENDPOINT",this.asxOutEndpointStore.findByName("报文UDP输出"));
      this.asxScheduler.scheduleJob(jobDetail, trigger);
    }

    this.asxScheduler.start();
  }
}
