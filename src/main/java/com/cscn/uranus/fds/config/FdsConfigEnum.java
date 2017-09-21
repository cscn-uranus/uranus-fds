package com.cscn.uranus.fds.config;

public enum FdsConfigEnum {
  FLIGHTMSG_LENGTH("FlightmsgLength", "350"),
  FLIGHTMSG_INDEX("FlightmsgIndex", "1"),
  FLOWCONTROLMSG_LENGTH("FlowcontrolmsgLength", "350"),
  FLOWCONTROLMSG_INDEX("FlowcontrolmsgIndex", "1");

  private String code;
  private String value;

  FdsConfigEnum(String code, String value) {
    this.code = code;
    this.value = value;
  }

  public String getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }
}
