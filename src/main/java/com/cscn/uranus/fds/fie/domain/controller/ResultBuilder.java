package com.cscn.uranus.fds.fie.domain.controller;

public class ResultBuilder {
  public static <T> Result<T> success(T t) {
    Result<T> result = new Result<>();
    result.setCode(ResultType.SUCCESS.getCode());
    result.setMessage(ResultType.SUCCESS.getMessage());
    result.setData(t);
    return result;
  }
}
