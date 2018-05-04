package com.cscn.uranus.fds.asx.job.component;

import com.cscn.uranus.fds.asx.endpoint.component.IAsxOutEndpoint;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

public class AsxOutEndpointJob implements Job {


  @Override
  public void execute(JobExecutionContext context) {
    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
    String message = dataMap.getString("MESSAGE");
    Object o = dataMap.get("ENDPOINT");
    if (o instanceof IAsxOutEndpoint) {
      IAsxOutEndpoint asxOutEndpoint = (IAsxOutEndpoint)o;
      asxOutEndpoint.send(message);
    }
  }
}
