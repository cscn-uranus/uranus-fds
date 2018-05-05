package com.cscn.uranus.fds.asx.endpoint.component;

import com.cscn.uranus.fds.asx.endpoint.component.IAsxOutEndpoint;
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;
import org.springframework.integration.support.MessageBuilder;

public class UdpOutEndpoint implements IAsxOutEndpoint {

  private String name;

  private UnicastSendingMessageHandler udpSendingAdapter;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public UdpOutEndpoint(String name, String host, int port) {
    this.udpSendingAdapter = new UnicastSendingMessageHandler(host, port);
    this.name = name;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public void send(String text) {
    this.udpSendingAdapter.handleMessageInternal(MessageBuilder.withPayload(text).build());
  }
}
