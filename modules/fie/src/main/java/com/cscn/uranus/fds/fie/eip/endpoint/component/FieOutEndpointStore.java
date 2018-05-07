package com.cscn.uranus.fds.fie.eip.endpoint.component;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class FieOutEndpointStore {
  private Map<String, IFieOutEndpoint> asxOutEndpoints;

  public FieOutEndpointStore() {
    this.asxOutEndpoints = new HashMap<>();
  }

  public void register(IFieOutEndpoint asxOutEndpoint) {
    this.asxOutEndpoints.put(asxOutEndpoint.getKey(), asxOutEndpoint);
  }

  public IFieOutEndpoint findByName(String name) {
    return this.asxOutEndpoints.get(name);
  }
}
