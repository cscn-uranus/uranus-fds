package com.cscn.uranus.fds.generator.service;

import com.cscn.uranus.fds.annotation.ExcludeFromTest;
import com.cscn.uranus.fds.config.service.FdsConfigManager;
import com.cscn.uranus.fds.generator.repo.FlowcontrolmsgRepo;
import javax.jms.Destination;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@ExcludeFromTest
@Service
@Transactional
public class ScheduledFlowcontrolmsgProducer {

  private final FdsConfigManager fdsConfigManager;
  private final FlowcontrolmsgRepo flowcontrolmsgRepo;
  private long flowcontrolmsgLength;
  private long flowcontrolmsgIndex;

  private final AmqManager amqManager;

  private static final String QUEUE_NAME = "CENTER2REGION.TEST.FLOWCONTROLS.QUEUE";

  private Destination destination = new ActiveMQQueue(QUEUE_NAME);

  public ScheduledFlowcontrolmsgProducer(
      FdsConfigManager fdsConfigManager,
      AmqManager amqManager,
      FlowcontrolmsgRepo flowcontrolmsgRepo) {
    this.fdsConfigManager = fdsConfigManager;
    this.amqManager = amqManager;
    this.flowcontrolmsgRepo = flowcontrolmsgRepo;

    this.init();
  }

  public String getNextFlowcontrolmsg() {
    String flowcontrolmsg =
        this.flowcontrolmsgRepo.findOne(this.flowcontrolmsgIndex).getXmlContent();
    this.flowcontrolmsgIndex += 1;
    this.fdsConfigManager.setFlowcontrolmsgIndex(this.flowcontrolmsgIndex);
    return flowcontrolmsg;
  }

  @Scheduled(fixedRate = 180000)
  public void produceScheduledMessage() {
    if (this.flowcontrolmsgIndex > this.flowcontrolmsgLength) {
      this.init();
    }
    String flowcontrolmsg = this.getNextFlowcontrolmsg();
    amqManager.sendXmlMsg(this.destination, flowcontrolmsg);
  }

  void init() {
    this.fdsConfigManager.initDefaultFlowcontrolmsgConfig();
    this.flowcontrolmsgLength = this.fdsConfigManager.getFlowcontrolmsgLength();
    this.flowcontrolmsgIndex = this.fdsConfigManager.getFlowcontrolmsgIndex();
    this.amqManager.deleteQueue(QUEUE_NAME);
  }
}
