package com.cscn.uranus.fds.asx.endpoint.service;

import com.cscn.uranus.fds.asx.endpoint.entity.AsxEndpoint;
import com.cscn.uranus.fds.asx.endpoint.repo.AsxEndpointRepo;
import java.util.HashSet;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AsxEndpointManager {
  private final AsxEndpointRepo asxEndpointRepo;

  public AsxEndpointManager(AsxEndpointRepo asxEndpointRepo) {
    this.asxEndpointRepo = asxEndpointRepo;
  }

  public AsxEndpoint add(AsxEndpoint asxEndpoint) {
    return this.asxEndpointRepo.save(asxEndpoint);
  }

  public Set<AsxEndpoint> findAll() {
    return new HashSet<>(this.asxEndpointRepo.findAll());
  }
}
