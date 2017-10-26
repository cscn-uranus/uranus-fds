package com.cscn.uranus.fds.generator.service;

import com.cscn.uranus.fds.annotation.ExcludeFromTest;
import com.cscn.uranus.fds.config.service.FdsConfigManager;
import com.cscn.uranus.fds.generator.repo.FlightmsgRepo;
import javax.jms.Destination;
import javax.transaction.Transactional;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@ExcludeFromTest
@Service
@Transactional
public class ScheduledFlightmsgProducer {

  private final FdsConfigManager fdsConfigManager;
  private final FlightmsgRepo flightmsgRepo;
  private static final String QUEUE_NAME = "CENTER2REGION.TEST.FLIGHTS.QUEUE";

  private final AmqManager amqManager;
  private long flightmsgLength;
  private long flightmsgIndex;

  private Destination destination = new ActiveMQQueue(QUEUE_NAME);

  public ScheduledFlightmsgProducer(
      FdsConfigManager fdsConfigManager, AmqManager amqManager, FlightmsgRepo flightmsgRepo) {
    this.fdsConfigManager = fdsConfigManager;
    this.amqManager = amqManager;
    this.flightmsgRepo = flightmsgRepo;

    this.init();
  }

  public String getNextFlightmsg() {
    String flightmsg = this.flightmsgRepo.findOne(this.flightmsgIndex).getXmlContent();
    this.flightmsgIndex += 1;
    this.fdsConfigManager.setFlightmsgIndex(this.flightmsgIndex);
    return flightmsg;
  }

  @Scheduled(fixedRate = 180000)
  public void produceScheduledMessage() {
    if (this.flightmsgIndex > this.flightmsgLength) {
      this.init();
    }

    String flightmsg = this.getNextFlightmsg();
    amqManager.sendXmlMsg(this.destination, flightmsg);
  }

  void init() {
    this.fdsConfigManager.initDefaultFlightmsgConfig();
    this.flightmsgLength = this.fdsConfigManager.getFlightmsgLength();
    this.flightmsgIndex = this.fdsConfigManager.getFlightmsgIndex();
    this.amqManager.deleteQueue(QUEUE_NAME);
  }
}
