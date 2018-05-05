package com.cscn.uranus.fds.asx.job.service;

import com.cscn.uranus.fds.asx.job.entity.AsxJobConfig;
import com.cscn.uranus.fds.asx.job.repo.AsxJobConfigRepo;
import java.util.HashSet;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AsxJobConfigManager {

  private final AsxJobConfigRepo asxJobConfigRepo;

  public AsxJobConfigManager(AsxJobConfigRepo asxJobConfigRepo) {
    this.asxJobConfigRepo = asxJobConfigRepo;
  }

  public AsxJobConfig add(AsxJobConfig asxJobConfig) {
    return this.asxJobConfigRepo.save(asxJobConfig);
  }

  public Set<AsxJobConfig> findAll() {
    return new HashSet<>(this.asxJobConfigRepo.findAll());
  }
}
