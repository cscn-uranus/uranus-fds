package com.cscn.uranus.fds.fie.domain.controller;

public enum ResultType {
  UNKNOWN_ERROR(-1, "未知错误"),
  SUCCESS(0, "成功"),;

  private Integer code;
  private String message;


  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  ResultType(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

}
