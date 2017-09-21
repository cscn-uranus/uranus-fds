package com.cscn.uranus.fds.generator.service;

import com.cscn.uranus.fds.annotation.ExcludeFromTest;
import com.cscn.uranus.fds.config.service.FdsConfigManager;
import com.cscn.uranus.fds.generator.repo.FlowcontrolmsgRepo;
import javax.jms.Destination;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@ExcludeFromTest
@Service
public class FlowcontrolmsgProducer {

  private final FdsConfigManager fdsConfigManager;
  private final FlowcontrolmsgRepo flowcontrolmsgRepo;
  private long flowcontrolRawMsgLength;
  private long flowcontrolRawMsgIndex;

  private final AmqManager amqManager;

  private static final String QUEUE_NAME = "CENTER2REGION.TEST.FLOWCONTROLS.QUEUE";

  private Destination destination = new ActiveMQQueue(QUEUE_NAME);

  @Autowired
  public FlowcontrolmsgProducer(
      FdsConfigManager fdsConfigManager,
      AmqManager amqManager,
      FlowcontrolmsgRepo flowcontrolmsgRepo) {
    this.fdsConfigManager = fdsConfigManager;
    this.amqManager = amqManager;
    this.flowcontrolmsgRepo = flowcontrolmsgRepo;

    this.init();
  }

  @Transactional
  public String getNextFlowcontrolRawMsg() {
    String flowcontrolRawMsg =
        this.flowcontrolmsgRepo.findOne(this.flowcontrolRawMsgIndex).getXmlContent();
    this.flowcontrolRawMsgIndex += 1;
    this.fdsConfigManager.setFlowcontrolmsgIndex(this.flowcontrolRawMsgIndex);
    return flowcontrolRawMsg;
  }

  @Scheduled(fixedRate = 180000)
  public void produceMessage() {
    if (this.flowcontrolRawMsgIndex > this.flowcontrolRawMsgLength) {
      this.init();
    }
    String flowcontrolRawMsg = this.getNextFlowcontrolRawMsg();
    amqManager.sendXmlMsg(this.destination, flowcontrolRawMsg);
  }

  void init() {
    this.fdsConfigManager.initDefaultFlowcontrolmsgConfig();
    this.amqManager.deleteQueue(QUEUE_NAME);
  }

}
