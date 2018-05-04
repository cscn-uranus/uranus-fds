package com.cscn.uranus.fds.asx.domain.controller;

public class Result<T> {

  private Integer code;

  private String message;

  private String description;

  private T data;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public Result() {

  }
  public Result(Integer code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }
}
