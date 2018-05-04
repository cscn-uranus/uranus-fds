package com.cscn.uranus.fds.asx.domain.controller;

public class ResultUtil {

  public static Result success(Object o) {
    Result result = new Result();
    result.setCode(ResultType.SUCCESS.getCode());
    result.setMessage(ResultType.SUCCESS.getMessage());
    result.setData(o);

    return result;
  }

}
