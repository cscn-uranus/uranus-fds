package com.cscn.uranus.fds.fie.job.component;

public interface IFieOutEndpoint {


  String getKey();

  void send(String text);
}
