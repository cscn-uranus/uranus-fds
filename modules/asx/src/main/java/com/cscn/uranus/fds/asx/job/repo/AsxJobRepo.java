package com.cscn.uranus.fds.asx.job.repo;

import com.cscn.uranus.fds.asx.job.entity.AsxJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsxJobRepo extends JpaRepository<AsxJob, String> {

}
