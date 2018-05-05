package com.cscn.uranus.fds.asx.job.component;

import com.cscn.uranus.fds.asx.domain.component.AsxGramsGenerator;
import com.cscn.uranus.fds.asx.domain.entity.AsxGram;
import com.cscn.uranus.fds.asx.endpoint.component.IAsxOutEndpoint;
import java.util.HashSet;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class AsxGramsOutJob implements Job {

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
    Object generator = dataMap.get("GENERATOR");
    Object endpoint = dataMap.get("ENDPOINT");

    AsxGramsGenerator asxGramsGenerator = null;
    IAsxOutEndpoint asxOutEndpoint = null;
    if (generator instanceof AsxGramsGenerator) {
      asxGramsGenerator = (AsxGramsGenerator) generator;
    }

    if (endpoint instanceof IAsxOutEndpoint) {
      asxOutEndpoint = (IAsxOutEndpoint) endpoint;
    }

    if (asxGramsGenerator != null && asxOutEndpoint != null) {
      HashSet<AsxGram> randomAsxGrams = asxGramsGenerator.generateRandomAsxGrams();

      if (randomAsxGrams != null) {
        String udpMessagePayload;
        StringBuilder sb = new StringBuilder();
        for (AsxGram gram : randomAsxGrams) {
          sb.append(gram.getHeader()).append(gram.getContent()).append(gram.getTail());
        }
        udpMessagePayload = sb.toString();
        asxOutEndpoint.send(udpMessagePayload);
      }
    }
  }
}
