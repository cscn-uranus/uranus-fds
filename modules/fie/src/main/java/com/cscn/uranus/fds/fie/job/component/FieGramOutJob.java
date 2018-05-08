package com.cscn.uranus.fds.fie.job.component;

import com.cscn.uranus.fds.fie.domain.component.FieGramGenerator;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieGramOutJob implements Job {

  private final Logger _logger = LoggerFactory.getLogger(FieGramOutJob.class);

  @Override
  public void execute(JobExecutionContext context) {
    _logger.info("FieGramOutJob！");
    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
    Object generator = dataMap.get("GENERATOR");
    Object endpoint = dataMap.get("ENDPOINT");

    FieGramGenerator fieGramGenerator = null;
    IFieOutEndpoint fieOutEndpoint = null;
    if (generator instanceof FieGramGenerator) {
      fieGramGenerator = (FieGramGenerator) generator;
    }

    if (endpoint instanceof IFieOutEndpoint) {
      fieOutEndpoint = (IFieOutEndpoint) endpoint;
    }

    if (fieGramGenerator != null && fieOutEndpoint != null) {
      String gramsText = fieGramGenerator.generateRandomFieGramsText();
      if (gramsText != null) {
        fieOutEndpoint.send(gramsText);
      }
    }
  }
}
