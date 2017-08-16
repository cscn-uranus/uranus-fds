package com.cscn.uranus.fds.config.repo;

import com.cscn.uranus.fds.config.entity.FdsConfig;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface FdsConfigRepo extends CrudRepository<FdsConfig, Long> {
    List<FdsConfig> findByCode(String code);
}
