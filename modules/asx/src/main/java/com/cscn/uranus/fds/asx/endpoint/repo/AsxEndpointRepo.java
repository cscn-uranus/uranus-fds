package com.cscn.uranus.fds.asx.endpoint.repo;

import com.cscn.uranus.fds.asx.endpoint.entity.AsxEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsxEndpointRepo extends JpaRepository<AsxEndpoint,String> {

}
