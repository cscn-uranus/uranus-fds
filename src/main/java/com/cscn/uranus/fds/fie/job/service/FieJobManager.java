package com.cscn.uranus.fds.fie.job.service;

import com.cscn.uranus.fds.fie.job.repo.FieJobRepo;
import com.cscn.uranus.fds.fie.job.entity.FieJob;
import java.util.HashSet;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FieJobManager {

  private final FieJobRepo fieJobRepo;

  public FieJobManager(FieJobRepo fieJobRepo) {
    this.fieJobRepo = fieJobRepo;
  }

  public FieJob add(FieJob fieJob) {
    return this.fieJobRepo.save(fieJob);
  }

  public Set<FieJob> findAll() {
    return new HashSet<>(this.fieJobRepo.findAll());
  }
}
