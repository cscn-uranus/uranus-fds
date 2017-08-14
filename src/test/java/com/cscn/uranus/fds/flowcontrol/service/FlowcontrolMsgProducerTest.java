package com.cscn.uranus.fds.flowcontrol.service;

import com.cscn.uranus.fds.Application;
import com.cscn.uranus.fds.activemq.service.AmqManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FlowcontrolMsgProducerTest {

    private static final String QUEUE_NAME = "CENTER2REGION.TEST.FLOWCONTROLS.QUEUE";

    @Autowired
    private FlowcontrolMsgProducer flowcontrolMsgProducer;

    @Autowired
    private AmqManager amqManager;

    @After
    public void destroy() throws Exception {
        this.flowcontrolMsgProducer.init();
    }

    @Test
    public void singleMessageProduced() throws Exception {
        this.flowcontrolMsgProducer.produceMessage();
        assertEquals("Queue length should be 1",
                1,
                this.amqManager.getQueueSize(QUEUE_NAME));
    }

    @Test
    public void multipleMessageProduced() throws Exception {
        for (int i = 0; i < 120; i++) {
            this.flowcontrolMsgProducer.produceMessage();
        }
        assertEquals("Queue length should be 120",
                120,
                this.amqManager.getQueueSize(QUEUE_NAME));
    }

    @Test
    public void thresholdMessageProduced() throws Exception {
        for (int i = 0; i < 350; i++) {
            this.flowcontrolMsgProducer.produceMessage();
        }
        assertEquals("Queue length should be 350",
                350,
                this.amqManager.getQueueSize(QUEUE_NAME));
    }

    @Test
    public void overflowMessageProduced() throws Exception {
        for (int i = 0; i < 400; i++) {
            this.flowcontrolMsgProducer.produceMessage();
        }
        assertEquals("Queue length should be 50",
                50,
                this.amqManager.getQueueSize(QUEUE_NAME));
    }
}
