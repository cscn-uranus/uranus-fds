package com.cscn.uranus.fds.fie.job.component;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class FieOutEndpointStore {
  private Map<String, IFieOutEndpoint> fieOutEndpoints;

  public FieOutEndpointStore() {
    this.fieOutEndpoints = new HashMap<>();
  }

  public void register(IFieOutEndpoint fieOutEndpoint) {
    this.fieOutEndpoints.put(fieOutEndpoint.getKey(), fieOutEndpoint);
  }

  public IFieOutEndpoint findByName(String name) {
    return this.fieOutEndpoints.get(name);
  }
}
