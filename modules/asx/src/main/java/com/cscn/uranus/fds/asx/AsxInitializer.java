package com.cscn.uranus.fds.asx;


import com.cscn.uranus.fds.asx.endpoint.AsxEndpointType;
import com.cscn.uranus.fds.asx.endpoint.UdpOutEndpoint;
import com.cscn.uranus.fds.asx.endpoint.component.AsxOutEndpointContainer;
import com.cscn.uranus.fds.asx.endpoint.entity.AsxEndpoint;
import com.cscn.uranus.fds.asx.endpoint.service.AsxEndpointManager;
import com.cscn.uranus.fds.asx.job.entity.AsxJob;
import com.cscn.uranus.fds.asx.job.service.AsxJobManager;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AsxInitializer {

  private final Logger _logger = LoggerFactory.getLogger(AsxInitializer.class);
  private final AsxEndpointManager asxEndpointManager;
  private final AsxJobManager asxJobManager;
  private final AsxOutEndpointContainer asxOutEndpointContainer;

  public AsxInitializer(
      AsxEndpointManager asxEndpointManager,
      AsxJobManager asxJobManager,
      AsxOutEndpointContainer asxOutEndpointContainer) {
    this.asxEndpointManager = asxEndpointManager;
    this.asxJobManager = asxJobManager;
    this.asxOutEndpointContainer = asxOutEndpointContainer;
  }

  public void doInitialize() {
    this.initializeEndpointConfig();
    this.initializeJobConfig();

    this.initializeEndpointInstance();
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
    Set<AsxJob> asxJobsPersisted = this.asxJobManager.findAll();
    AsxJob defaultAsxJob = new AsxJob();
    defaultAsxJob.setName("定时发送报文");
    defaultAsxJob.setCronExpression("0/1 * * * * ? ");
    if (!asxJobsPersisted.contains(defaultAsxJob)) {
      this.asxJobManager.add(defaultAsxJob);
    }
  }

  private void initializeEndpointInstance() {
    Set<AsxEndpoint> asxEndpointsPersisted = this.asxEndpointManager.findAll();
    for (AsxEndpoint asxEndpoint: asxEndpointsPersisted) {
      if (asxEndpoint.getType() == AsxEndpointType.UDP_OUT) {
        String host = this.getHostFromUri(asxEndpoint.getUri());
        int port = this.getPortFromUri(asxEndpoint.getUri());
        UdpOutEndpoint udpOutEndpoint = new UdpOutEndpoint(host, port);
        this.asxOutEndpointContainer.add(udpOutEndpoint);
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

  }
}
