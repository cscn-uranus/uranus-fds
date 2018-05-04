package com.cscn.uranus.fds.asx.endpoint;

import com.cscn.uranus.fds.asx.endpoint.component.IAsxOutEndpoint;
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;
import org.springframework.integration.support.MessageBuilder;

public class UdpOutEndpoint implements IAsxOutEndpoint {

  private UnicastSendingMessageHandler udpSendingAdapter;

  public UdpOutEndpoint(String host, int port) {
    this.udpSendingAdapter = new UnicastSendingMessageHandler(host, port);
  }

  @Override
  public void send(String text) {
    this.udpSendingAdapter.handleMessageInternal(MessageBuilder.withPayload(text).build());
  }
}
