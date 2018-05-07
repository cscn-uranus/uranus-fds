package com.cscn.uranus.fds.fie.eip.endpoint.component;

public interface IFieOutEndpoint {


  String getKey();

  void send(String text);
}
