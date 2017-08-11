package com.cscn.uranus.fds.flowcontrol.service;

import com.cscn.uranus.fds.activemq.service.AmqManager;
import com.cscn.uranus.fds.config.service.FdsConfigManager;
import com.cscn.uranus.fds.flowcontrol.repo.FlowcontrolRawMsgRepo;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Destination;

@Service
public class FlowcontrolMsgProducer {

    private final FdsConfigManager fdsConfigManager;
    private final FlowcontrolRawMsgRepo flowcontrolRawMsgRepo;
    private long flowcontrolRawMsgLength;
    private long flowcontrolRawMsgIndex;

    private final AmqManager amqManager;

    private static final String QUEUE_NAME = "CENTER2REGION.TEST.FLOWCONTROLS.QUEUE";

    private Destination destination = new ActiveMQQueue(QUEUE_NAME);


    @Autowired
    public FlowcontrolMsgProducer(FdsConfigManager fdsConfigManager, AmqManager amqManager, FlowcontrolRawMsgRepo flowcontrolRawMsgRepo) {
        this.fdsConfigManager = fdsConfigManager;
        this.amqManager = amqManager;
        this.flowcontrolRawMsgRepo = flowcontrolRawMsgRepo;

        this.init();
    }

    @Transactional
    public String getNextFlowcontrolRawMsg() {
        String flowcontrolRawMsg = this.flowcontrolRawMsgRepo.findOne(this.flowcontrolRawMsgIndex).getXmlContent();
        this.flowcontrolRawMsgIndex += 1;
        this.fdsConfigManager.setFlowcontrolRawMsgIndex(this.flowcontrolRawMsgIndex);
        return flowcontrolRawMsg;
    }

    @Scheduled(fixedRate = 300000)
    public void produceMessage() {
        if (this.flowcontrolRawMsgIndex > this.flowcontrolRawMsgLength) {
            this.init();
        }
        String flowcontrolRawMsg = this.getNextFlowcontrolRawMsg();
        amqManager.sendXmlMsg(this.destination, flowcontrolRawMsg);
    }

    void init() {
        this.initLengthConfig();
        this.initIndexConfig();
        this.amqManager.deleteQueue(QUEUE_NAME);
    }

    private void initLengthConfig() {
        this.flowcontrolRawMsgLength = 350;
        this.fdsConfigManager.setFlowcontrolRawMsgLength(this.flowcontrolRawMsgLength);
    }

    private void initIndexConfig() {
        this.flowcontrolRawMsgIndex = 1;
        this.fdsConfigManager.setFlowcontrolRawMsgIndex(this.flowcontrolRawMsgIndex);

    }
}

