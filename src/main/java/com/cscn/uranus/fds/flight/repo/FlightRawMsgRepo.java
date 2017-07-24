package com.cscn.uranus.fds.flight.repo;

import com.cscn.uranus.fds.flight.entity.FlightRawMsg;
import org.springframework.data.repository.CrudRepository;

public interface FlightRawMsgRepo extends CrudRepository<FlightRawMsg, Long> {
}
