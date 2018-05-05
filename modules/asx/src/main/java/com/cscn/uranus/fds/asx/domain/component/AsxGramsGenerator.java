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

  public HashSet<AsxGram> generateRandomAsxGrams() {
    List<AsxGram> allAsxGrams = new ArrayList<>(this.asxGramManager.findAll());
    if (allAsxGrams.size()>5) {
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
    } else {
      return null;
    }
  }

  private int rangeRandom(int min, int max) {
    Random random = new Random();
    return (random.nextInt(max) % ((max - min) + 1)) + min;
  }

  public void startProduceGramsViaUdp() {


  }
}
