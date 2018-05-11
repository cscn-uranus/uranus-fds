package com.cscn.uranus.fds.fie.job.entity.type;

public enum FieEndpointType {
  UDP_CLIENT("UdpOut"),
  ACTIVE_MQ_CLIENT("ActiveMQOUt");

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  FieEndpointType(String name) {
    this.name = name;
  }
}
