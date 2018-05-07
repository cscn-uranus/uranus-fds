package com.cscn.uranus.fds.fie.job.component;

import com.cscn.uranus.fds.fie.domain.entity.FieGram;
import com.cscn.uranus.fds.fie.domain.service.FieGramManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FieGramGenerator {

  private final FieGramManager fieGramManager;
  private final FieScheduler fieScheduler;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public FieGramGenerator(FieGramManager fieGramManager,
      FieScheduler fieScheduler) {
    this.fieGramManager = fieGramManager;
    this.fieScheduler = fieScheduler;
  }

  public HashSet<FieGram> generateRandomAsxGrams() {
    List<FieGram> allFieGrams = new ArrayList<>(this.fieGramManager.findAll());
    if (allFieGrams.size()>5) {
      HashSet<FieGram> randomFieGrams = new HashSet<>();
      int minCount = 1;
      int maxCount = 5;
      int randomCount = rangeRandom(minCount, maxCount);

      int minIndex = 0;
      int maxIndex = allFieGrams.size();

      if (maxIndex == 0) {
        return null;
      } else {
        for (int i = 0; i < randomCount; i++) {
          int randomIndex = rangeRandom(minIndex, maxIndex);
          randomFieGrams.add(allFieGrams.get(randomIndex));
        }
        return randomFieGrams;
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
