package com.cscn.uranus.fds.fie.job.component;

import com.cscn.uranus.fds.fie.domain.entity.FieGram;
import com.cscn.uranus.fds.fie.eip.endpoint.component.IFieOutEndpoint;
import java.util.HashSet;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

public class FieGramOutJob implements Job {

  @Override
  public void execute(JobExecutionContext context) {
    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
    Object generator = dataMap.get("GENERATOR");
    Object endpoint = dataMap.get("ENDPOINT");

    FieGramGenerator fieGramGenerator = null;
    IFieOutEndpoint asxOutEndpoint = null;
    if (generator instanceof FieGramGenerator) {
      fieGramGenerator = (FieGramGenerator) generator;
    }

    if (endpoint instanceof IFieOutEndpoint) {
      asxOutEndpoint = (IFieOutEndpoint) endpoint;
    }

    if (fieGramGenerator != null && asxOutEndpoint != null) {
      HashSet<FieGram> randomFieGrams = fieGramGenerator.generateRandomAsxGrams();

      if (randomFieGrams != null) {
        String udpMessagePayload;
        StringBuilder sb = new StringBuilder();
        for (FieGram gram : randomFieGrams) {
          sb.append(gram.getHeader()).append(gram.getContent()).append(gram.getTail());
        }
        udpMessagePayload = sb.toString();
        asxOutEndpoint.send(udpMessagePayload);
      }
    }
  }
}
