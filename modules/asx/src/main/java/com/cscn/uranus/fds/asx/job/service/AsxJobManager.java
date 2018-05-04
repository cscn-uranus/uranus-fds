package com.cscn.uranus.fds.asx.job.service;

import com.cscn.uranus.fds.asx.job.entity.AsxJob;
import com.cscn.uranus.fds.asx.job.repo.AsxJobRepo;
import java.util.HashSet;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AsxJobManager {

  private final AsxJobRepo asxJobRepo;

  public AsxJobManager(AsxJobRepo asxJobRepo) {
    this.asxJobRepo = asxJobRepo;
  }

  public AsxJob add(AsxJob asxJob) {
    return this.asxJobRepo.save(asxJob);
  }

  public Set<AsxJob> findAll() {
    return new HashSet<>(this.asxJobRepo.findAll());
  }
}
