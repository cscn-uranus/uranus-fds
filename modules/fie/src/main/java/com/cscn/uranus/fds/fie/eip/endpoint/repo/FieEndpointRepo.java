package com.cscn.uranus.fds.fie.eip.endpoint.repo;

import com.cscn.uranus.fds.fie.eip.endpoint.entity.FieEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieEndpointRepo extends JpaRepository<FieEndpoint,String> {

}
