package com.cscn.uranus.fds.asx.endpoint.component;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class AsxOutEndpointStore {
  private Set<IAsxOutEndpoint> asxOutEndpoints;

  public AsxOutEndpointStore() {
    this.asxOutEndpoints = new HashSet<>();
  }

  public void add(IAsxOutEndpoint asxOutEndpoint) {
    this.asxOutEndpoints.add(asxOutEndpoint);
  }

  public IAsxOutEndpoint findByName(String name) {
    for(IAsxOutEndpoint iAsxOutEndpoint : this.asxOutEndpoints) {
      if (iAsxOutEndpoint.name().equals(name)) {
        return iAsxOutEndpoint;
      }
    }
    return null;
  }
}
