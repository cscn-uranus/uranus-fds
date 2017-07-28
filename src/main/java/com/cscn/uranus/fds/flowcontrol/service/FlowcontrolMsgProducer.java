package com.cscn.uranus.fds.flowcontrol.service;

import com.cscn.uranus.fds.config.entity.FdsConfig;
import com.cscn.uranus.fds.config.repo.FdsConfigRepo;
import com.cscn.uranus.fds.flowcontrol.repo.FlowcontrolRawMsgRepo;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.util.List;

@Service
public class FlowcontrolMsgProducer {

    private final FdsConfigRepo fdsConfigRepo;
    private final FlowcontrolRawMsgRepo flowcontrolRawMsgRepo;
    private long flowcontrolRawMsgLength;
    private final JmsTemplate jmsTemplate;
    Destination destination = new ActiveMQQueue("CENTER2REGION.TEST.FLIGHTS.QUEUE");


    @Autowired
    public FlowcontrolMsgProducer(FdsConfigRepo fdsConfigRepo, FlowcontrolRawMsgRepo flowcontrolRawMsgRepo, JmsTemplate jmsTemplate) {
        this.fdsConfigRepo = fdsConfigRepo;
        this.flowcontrolRawMsgRepo = flowcontrolRawMsgRepo;
        this.jmsTemplate = jmsTemplate;

        List<FdsConfig> fdsConfig = this.fdsConfigRepo.findByCode("FlowcontrolMsgLength");
    }
    // 发送消息，destination是发送到的队列，message是待发送的消息
    private void sendMessage(Destination destination, final String message) {
        jmsTemplate.convertAndSend(destination, message);
    }

    private void getNextFlowcontrolRawMsg() {
//        FdsConfig fdsConfig = this.fdsConfigRepo.findByCode("");
    }

    @Scheduled(fixedRate = 60000)
    public void produceMessage() {

    }
}

