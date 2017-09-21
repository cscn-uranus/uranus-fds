package com.cscn.uranus.fds.generator.repo;

import com.cscn.uranus.fds.generator.entity.Flightmsg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightmsgRepo extends JpaRepository<Flightmsg, Long> {}
