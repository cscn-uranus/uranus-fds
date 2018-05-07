package com.cscn.uranus.fds.fie.eip.endpoint.service;

import com.cscn.uranus.fds.fie.eip.endpoint.entity.FieEndpoint;
import com.cscn.uranus.fds.fie.eip.endpoint.repo.FieEndpointRepo;
import java.util.HashSet;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FieEndpointManager {
  private final FieEndpointRepo fieEndpointRepo;

  public FieEndpointManager(FieEndpointRepo fieEndpointRepo) {
    this.fieEndpointRepo = fieEndpointRepo;
  }

  public FieEndpoint add(FieEndpoint fieEndpoint) {
    return this.fieEndpointRepo.save(fieEndpoint);
  }

  public Set<FieEndpoint> findAll() {
    return new HashSet<>(this.fieEndpointRepo.findAll());
  }
}
