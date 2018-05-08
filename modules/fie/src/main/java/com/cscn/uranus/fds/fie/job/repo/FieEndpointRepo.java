package com.cscn.uranus.fds.fie.job.repo;

import com.cscn.uranus.fds.fie.job.entity.FieEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieEndpointRepo extends JpaRepository<FieEndpoint,String> {

}
