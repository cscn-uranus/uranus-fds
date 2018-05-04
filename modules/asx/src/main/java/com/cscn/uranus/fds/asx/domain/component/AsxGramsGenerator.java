package com.cscn.uranus.fds.asx.domain.component;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import com.cscn.uranus.fds.asx.domain.entity.AsxGram;
import com.cscn.uranus.fds.asx.domain.service.AsxGramManager;
import com.cscn.uranus.fds.asx.job.component.AsxScheduler;
import com.cscn.uranus.fds.asx.job.component.AsxOutEndpointJob;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AsxGramsGenerator {

  private final AsxGramManager asxGramManager;
  private final AsxScheduler asxScheduler;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public AsxGramsGenerator(AsxGramManager asxGramManager,
      AsxScheduler asxScheduler) {
    this.asxGramManager = asxGramManager;
    this.asxScheduler = asxScheduler;
  }

  private HashSet<AsxGram> generateRandomAsxGrams() {
    List<AsxGram> allAsxGrams = new ArrayList<>(this.asxGramManager.findAll());
    HashSet<AsxGram> randomAsxGrams = new HashSet<>();
    int minCount = 1;
    int maxCount = 5;
    int randomCount = rangeRandom(minCount, maxCount);

    int minIndex = 0;
    int maxIndex = allAsxGrams.size();

    if (maxIndex == 0) {
      return null;
    } else {
      for (int i = 0; i < randomCount; i++) {
        int randomIndex = rangeRandom(minIndex, maxIndex);
        randomAsxGrams.add(allAsxGrams.get(randomIndex));
      }
      return randomAsxGrams;
    }
  }

  private int rangeRandom(int min, int max) {
    Random random = new Random();
    return (random.nextInt(max) % ((max - min) + 1)) + min;
  }

  public void startProduceGramsViaUdp() {
    JobDetail job = newJob(AsxOutEndpointJob.class).withIdentity("UdpOutJob", "group1").build();

    HashSet<AsxGram> randomAsxGrams = this.generateRandomAsxGrams();

    String udpMessagePayload;
    StringBuilder sb = new StringBuilder();
    for (AsxGram gram : randomAsxGrams) {
      sb.append(gram.getHeader()).append(gram.getContent()).append(gram.getTail());
    }
    udpMessagePayload = sb.toString();

    job.getJobDataMap().put("MESSAGE", udpMessagePayload);

    Trigger trigger = newTrigger().withIdentity("UdpOutTrigger", "group1")
        .withSchedule(simpleSchedule().withIntervalInSeconds(1).repeatForever()).startNow().build();

    this.asxScheduler.scheduleJob(job, trigger);
    this.asxScheduler.start();

  }
}
