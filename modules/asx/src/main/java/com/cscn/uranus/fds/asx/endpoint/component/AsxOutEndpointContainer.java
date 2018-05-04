package com.cscn.uranus.fds.asx.endpoint.component;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class AsxOutEndpointContainer {
  private Set<IAsxOutEndpoint> asxOutEndpoints;

  public AsxOutEndpointContainer() {
    this.asxOutEndpoints = new HashSet<>();
  }

  public void add(IAsxOutEndpoint asxOutEndpoint) {
    this.asxOutEndpoints.add(asxOutEndpoint);
  }
}
