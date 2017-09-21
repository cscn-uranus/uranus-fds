package com.cscn.uranus.fds.generator.service;

import com.cscn.uranus.fds.FdsTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FdsTest.class)
public class FlightmsgProducerTest {

  private static final String QUEUE_NAME = "CENTER2REGION.TEST.FLIGHTS.QUEUE";
  @Autowired private FlightmsgProducer flightmsgProducer;

  @Autowired private AmqManager amqManager;

  @After
  public void destroy() throws Exception {
    this.flightmsgProducer.init();
  }

  @Test
  public void singleMessageProduced() throws Exception {
    this.flightmsgProducer.produceMessage();
    Assert.assertEquals("Queue length should be 1", 1, this.amqManager.getQueueSize(QUEUE_NAME));
  }

  @Test
  public void multipleMessageProduced() throws Exception {
    for (int i = 0; i < 120; i++) {
      this.flightmsgProducer.produceMessage();
    }
    Assert.assertEquals(
        "Queue length should be 120", 120, this.amqManager.getQueueSize(QUEUE_NAME));
  }

  @Test
  public void thresholdMessageProduced() throws Exception {
    for (int i = 0; i < 350; i++) {
      this.flightmsgProducer.produceMessage();
    }
    Assert.assertEquals(
        "Queue length should be 350", 350, this.amqManager.getQueueSize(QUEUE_NAME));
  }

  @Test
  public void overflowMessageProduced() throws Exception {
    for (int i = 0; i < 400; i++) {
      this.flightmsgProducer.produceMessage();
    }
    Assert.assertEquals("Queue length should be 50", 50, this.amqManager.getQueueSize(QUEUE_NAME));
  }
}
