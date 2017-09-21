package com.cscn.uranus.fds.generator.service;

import com.cscn.uranus.fds.annotation.ExcludeFromTest;
import com.cscn.uranus.fds.config.service.FdsConfigManager;
import com.cscn.uranus.fds.generator.repo.FlightmsgRepo;
import javax.jms.Destination;
import javax.transaction.Transactional;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@ExcludeFromTest
@Service
@Transactional
public class FlightmsgProducer {

  private final FdsConfigManager fdsConfigManager;
  private final FlightmsgRepo flightmsgRepo;
  private static final String QUEUE_NAME = "CENTER2REGION.TEST.FLIGHTS.QUEUE";

  private final AmqManager amqManager;
  private long flightRawMsgLength;
  private long flightRawMsgIndex;

  private Destination destination = new ActiveMQQueue(QUEUE_NAME);

  @Autowired
  public FlightmsgProducer(
      FdsConfigManager fdsConfigManager, AmqManager amqManager, FlightmsgRepo flightmsgRepo) {
    this.fdsConfigManager = fdsConfigManager;
    this.amqManager = amqManager;
    this.flightmsgRepo = flightmsgRepo;

    this.init();
  }

  public String getNextFlightRawMsg() {
    String flightRawMsg = this.flightmsgRepo.findOne(this.flightRawMsgIndex).getXmlContent();
    this.flightRawMsgIndex += 1;
    this.fdsConfigManager.setFlightmsgIndex(this.flightRawMsgIndex);
    return flightRawMsg;
  }

  @Scheduled(fixedRate = 180000)
  public void produceMessage() {
    if (this.flightRawMsgIndex > this.flightRawMsgLength) {
      this.init();
    }

    String flightRawMsg = this.getNextFlightRawMsg();
    amqManager.sendXmlMsg(this.destination, flightRawMsg);
  }

  void init() {
    this.fdsConfigManager.initDefaultFlightmsgConfig();
    this.amqManager.deleteQueue(QUEUE_NAME);
  }

}
