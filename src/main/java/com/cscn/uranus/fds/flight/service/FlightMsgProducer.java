package com.cscn.uranus.fds.flight.service;

import com.cscn.uranus.fds.config.entity.FdsConfig;
import com.cscn.uranus.fds.config.repo.FdsConfigRepo;
import org.springframework.stereotype.Service;

@Service
public class FlightMsgProducer {
    private final FdsConfigRepo fdsConfigRepo;

    public FlightMsgProducer(FdsConfigRepo fdsConfigRepo) {
        this.fdsConfigRepo = fdsConfigRepo;
    }
}
