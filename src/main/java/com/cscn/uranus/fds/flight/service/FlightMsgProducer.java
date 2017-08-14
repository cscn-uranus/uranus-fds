package com.cscn.uranus.fds.flight.service;

import com.cscn.uranus.fds.activemq.service.AmqManager;
import com.cscn.uranus.fds.config.service.FdsConfigManager;
import com.cscn.uranus.fds.flight.repo.FlightRawMsgRepo;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.transaction.Transactional;

@Service
public class FlightMsgProducer {

    private final FdsConfigManager fdsConfigManager;
    private final FlightRawMsgRepo flightRawMsgRepo;
    private static final String QUEUE_NAME = "CENTER2REGION.TEST.FLIGHTS.QUEUE";

    private final AmqManager amqManager;
    private long flightRawMsgLength;
    private long flightRawMsgIndex;


    private Destination destination = new ActiveMQQueue(QUEUE_NAME);

    @Autowired
    public FlightMsgProducer(FdsConfigManager fdsConfigManager, AmqManager amqManager, FlightRawMsgRepo flightRawMsgRepo) {
        this.fdsConfigManager = fdsConfigManager;
        this.amqManager = amqManager;
        this.flightRawMsgRepo = flightRawMsgRepo;

        this.init();
    }

    @Transactional
    public String getNextFlightRawMsg() {
        String flightRawMsg = this.flightRawMsgRepo.findOne(this.flightRawMsgIndex).getXmlContent();
        this.flightRawMsgIndex += 1;
        this.fdsConfigManager.setFlightRawMsgIndex(this.flightRawMsgIndex);
        return flightRawMsg;
    }

    @Scheduled(initialDelay = 600000 ,fixedRate = 300000)
    public void produceMessage() {
        if (this.flightRawMsgIndex > this.flightRawMsgLength) {
            this.init();
        }

        String flightRawMsg = this.getNextFlightRawMsg();
        amqManager.sendXmlMsg(this.destination, flightRawMsg);

    }

    void init() {
        this.initLengthConfig();
        this.initIndexConfig();
        this.amqManager.deleteQueue(QUEUE_NAME);
    }

    private void initLengthConfig() {
        this.flightRawMsgLength = 350;
        this.fdsConfigManager.setFlightRawMsgLength(this.flightRawMsgLength);
    }

    private void initIndexConfig() {
        this.flightRawMsgIndex = 1;
        this.fdsConfigManager.setFlightRawMsgIndex(this.flightRawMsgIndex);
    }
}
