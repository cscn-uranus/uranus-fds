package com.cscn.uranus.fds.fie.eip.endpoint.component;

import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;
import org.springframework.integration.support.MessageBuilder;

public class FieUdpOutEndpoint implements IFieOutEndpoint {

  private String name;

  private UnicastSendingMessageHandler udpSendingAdapter;

  public FieUdpOutEndpoint(String name, String host, int port) {
    this.udpSendingAdapter = new UnicastSendingMessageHandler(host, port);
    this.name = name;
  }

  @Override
  public String getKey() {
    return this.name;
  }

  @Override
  public void send(String text) {
    this.udpSendingAdapter.handleMessageInternal(MessageBuilder.withPayload(text).build());
  }
}
