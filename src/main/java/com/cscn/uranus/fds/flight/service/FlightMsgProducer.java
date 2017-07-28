package com.cscn.uranus.fds.flight.service;

import com.cscn.uranus.fds.config.entity.FdsConfig;
import com.cscn.uranus.fds.config.repo.FdsConfigRepo;
import com.cscn.uranus.fds.flight.entity.FlightRawMsg;
import com.cscn.uranus.fds.flight.repo.FlightRawMsgRepo;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.transaction.Transactional;

@Service
public class FlightMsgProducer {

    private final FdsConfigRepo fdsConfigRepo;
    private final FlightRawMsgRepo flightRawMsgRepo;
    private FdsConfig flightRawMsgLengthConfig;
    private FdsConfig flightRawMsgIndexConfig;
    private long flightRawMsgLength;
    private long flightRawMsgIndex;
    private final JmsTemplate jmsTemplate;
    Destination destination = new ActiveMQQueue("CENTER2REGION.TEST.FLIGHTS.QUEUE");

    @Autowired
    public FlightMsgProducer(FdsConfigRepo fdsConfigRepo, FlightRawMsgRepo flightRawMsgRepo, JmsTemplate jmsTemplate) {
        this.fdsConfigRepo = fdsConfigRepo;
        this.flightRawMsgRepo = flightRawMsgRepo;
        this.jmsTemplate = jmsTemplate;

        this.flightRawMsgLengthConfig = this.fdsConfigRepo.findByCode("FlightRawMsgLength").get(0);
        this.flightRawMsgLength = Long.parseLong(flightRawMsgLengthConfig.getValue());


        this.flightRawMsgIndexConfig = this.fdsConfigRepo.findByCode("FlightRawMsgIndex").get(0);
        this.flightRawMsgIndex = Long.parseLong(flightRawMsgIndexConfig.getValue());
    }

    private void sendMessage(Destination destination, final String message) {
        jmsTemplate.convertAndSend(destination, message);
    }

    @Transactional
    public String getNextFlightRawMsg() {
        String flightRawMsg = this.flightRawMsgRepo.findOne(this.flightRawMsgIndex).getXmlContent();
        if (this.flightRawMsgIndex < this.flightRawMsgLength) {
            this.flightRawMsgIndex += 1;
        } else {
            this.flightRawMsgIndex = 1;
        }
        this.flightRawMsgIndexConfig.setValue(String.valueOf(this.flightRawMsgIndex));
        this.fdsConfigRepo.save(flightRawMsgIndexConfig);
        return  flightRawMsg;
    }

    @Scheduled(fixedRate = 300000)
    public void produceMessage() {
        String flightRawMsg = this.getNextFlightRawMsg();
        this.sendMessage(this.destination, flightRawMsg);
    }
}
