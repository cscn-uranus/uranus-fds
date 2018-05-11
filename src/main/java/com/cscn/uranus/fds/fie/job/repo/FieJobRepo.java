package com.cscn.uranus.fds.fie.job.repo;

import com.cscn.uranus.fds.fie.job.entity.FieJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieJobRepo extends JpaRepository<FieJob, String> {

}
