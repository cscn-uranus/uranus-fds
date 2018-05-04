package com.cscn.uranus.fds.asx.endpoint;

public enum AsxEndpointType {
  UDP_OUT("UdpOut"),
  ACTIVE_MQ_OUT("ActiveMQOUt");

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  AsxEndpointType(String name) {
    this.name = name;
  }
}
