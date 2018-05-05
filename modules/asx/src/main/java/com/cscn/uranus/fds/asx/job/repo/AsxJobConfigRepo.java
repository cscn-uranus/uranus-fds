package com.cscn.uranus.fds.asx.job.repo;

import com.cscn.uranus.fds.asx.job.entity.AsxJobConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsxJobConfigRepo extends JpaRepository<AsxJobConfig, String> {

}
