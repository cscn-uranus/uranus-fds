package com.cscn.uranus.fds.flight.service;

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
public class FlightMsgProducerTest {

    public static final String QUEUE_NAME = "CENTER2REGION.TEST.FLIGHTS.QUEUE";
    @Autowired
    private FlightMsgProducer flightMsgProducer;

    @Autowired
    private AmqManager amqManager;

    @Before
    public void init() throws Exception {
        this.flightMsgProducer.init();
    }

    @After
    public void destroy() throws Exception {
        this.flightMsgProducer.init();
    }

    @Test
    public void singleMessageProduced() throws Exception {
        this.flightMsgProducer.produceMessage();
        assertEquals("Queue length should be 1",
                1,
                this.amqManager.getQueueSize(QUEUE_NAME));
    }

    @Test
    public void multipleMessageProduced() throws Exception {
        for (int i = 0; i < 350; i++) {
            this.flightMsgProducer.produceMessage();
        }
        assertEquals("Queue length should be 350",
                350,
                this.amqManager.getQueueSize(QUEUE_NAME));
    }

    @Test
    public void overflowMessageProduced() throws Exception {
        for (int i = 0; i < 400; i++) {
            this.flightMsgProducer.produceMessage();
        }
        assertEquals("Queue length should be 50",
                50,
                this.amqManager.getQueueSize(QUEUE_NAME));
    }

}