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
import javax.transaction.Transactional;

@Service
public class FlowcontrolMsgProducer {

    private final FdsConfigRepo fdsConfigRepo;
    private final FlowcontrolRawMsgRepo flowcontrolRawMsgRepo;
    private FdsConfig flowcontrolRawMsgLengthConfig;
    private FdsConfig flowcontrolRawMsgIndexConfig;
    private long flowcontrolRawMsgLength;
    private long flowcontrolRawMsgIndex;
    private final JmsTemplate jmsTemplate;
    Destination destination = new ActiveMQQueue("CENTER2REGION.TEST.FLOWCONTROLS.QUEUE");



    @Autowired
    public FlowcontrolMsgProducer(FdsConfigRepo fdsConfigRepo, FlowcontrolRawMsgRepo flowcontrolRawMsgRepo, JmsTemplate jmsTemplate) {
        this.fdsConfigRepo = fdsConfigRepo;
        this.flowcontrolRawMsgRepo = flowcontrolRawMsgRepo;
        this.jmsTemplate = jmsTemplate;

        this.flowcontrolRawMsgLengthConfig = this.fdsConfigRepo.findByCode("FlowcontrolRawMsgLength").get(0);
        this.flowcontrolRawMsgLength = Long.parseLong(flowcontrolRawMsgLengthConfig.getValue());

        this.flowcontrolRawMsgIndexConfig = this.fdsConfigRepo.findByCode("FlowcontrolRawMsgIndex").get(0);
        this.flowcontrolRawMsgIndex = Long.parseLong(flowcontrolRawMsgIndexConfig.getValue());
    }
    // 发送消息，destination是发送到的队列，message是待发送的消息
    private void sendMessage(Destination destination, final String message) {
        jmsTemplate.convertAndSend(destination, message);
    }

    @Transactional
    public String getNextFlowcontrolRawMsg() {
        String flowcontrolRawMsg = this.flowcontrolRawMsgRepo.findOne(this.flowcontrolRawMsgIndex).getXmlContent();
        if (this.flowcontrolRawMsgIndex < this.flowcontrolRawMsgLength) {
            this.flowcontrolRawMsgIndex += 1;
        } else {
            this.flowcontrolRawMsgIndex = 1;
        }
        this.flowcontrolRawMsgIndexConfig.setValue(String.valueOf(this.flowcontrolRawMsgIndex));
        this.fdsConfigRepo.save(flowcontrolRawMsgIndexConfig);
        return flowcontrolRawMsg;
    }

    @Scheduled(fixedRate = 300000)
    public void produceMessage() {
        String flowcontrolRawMsg = this.getNextFlowcontrolRawMsg();
        this.sendMessage(this.destination, flowcontrolRawMsg);
    }
}

