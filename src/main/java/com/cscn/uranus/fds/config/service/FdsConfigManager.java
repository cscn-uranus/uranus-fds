package com.cscn.uranus.fds.config.service;

import com.cscn.uranus.fds.config.FdsConfigEnum;
import com.cscn.uranus.fds.config.entity.FdsConfig;
import com.cscn.uranus.fds.config.repo.FdsConfigRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FdsConfigManager {

  private final FdsConfigRepo fdsConfigRepo;

  public FdsConfigManager(FdsConfigRepo fdsConfigRepo) {
    this.fdsConfigRepo = fdsConfigRepo;
    this.initDefaultConfig();
  }

  public void initDefaultConfig() {
    this.initDefaultFlightmsgConfig();
    this.initDefaultFlowcontrolmsgConfig();
  }

  public void initDefaultFlightmsgConfig() {
    long length = this.fdsConfigRepo.findByCode(FdsConfigEnum.FLIGHTMSG_LENGTH.getCode()).size();
    if (length < 1) {
      FdsConfig lengthConfig = new FdsConfig();
      lengthConfig.setCode(FdsConfigEnum.FLIGHTMSG_LENGTH.getCode());
      lengthConfig.setValue(FdsConfigEnum.FLIGHTMSG_LENGTH.getValue());
      this.fdsConfigRepo.save(lengthConfig);
    }
    length = this.fdsConfigRepo.findByCode(FdsConfigEnum.FLIGHTMSG_INDEX.getCode()).size();
    if (length < 1) {
      FdsConfig indexConfig = new FdsConfig();
      indexConfig.setCode(FdsConfigEnum.FLIGHTMSG_INDEX.getCode());
      indexConfig.setValue(FdsConfigEnum.FLIGHTMSG_INDEX.getValue());
      this.fdsConfigRepo.save(indexConfig);
    }
  }

  public void initDefaultFlowcontrolmsgConfig() {
    long length =
        this.fdsConfigRepo.findByCode(FdsConfigEnum.FLOWCONTROLMSG_LENGTH.getCode()).size();
    if (length < 1) {
      FdsConfig lengthConfig = new FdsConfig();
      lengthConfig.setCode(FdsConfigEnum.FLOWCONTROLMSG_LENGTH.getCode());
      lengthConfig.setValue(FdsConfigEnum.FLOWCONTROLMSG_LENGTH.getValue());
      this.fdsConfigRepo.save(lengthConfig);
    }
    length = this.fdsConfigRepo.findByCode(FdsConfigEnum.FLOWCONTROLMSG_INDEX.getCode()).size();
    if (length < 1) {
      FdsConfig indexConfig = new FdsConfig();
      indexConfig.setCode(FdsConfigEnum.FLOWCONTROLMSG_INDEX.getCode());
      indexConfig.setValue(FdsConfigEnum.FLOWCONTROLMSG_INDEX.getValue());
      this.fdsConfigRepo.save(indexConfig);
    }
  }

  public void setFlightmsgLength(long length) {
    FdsConfig config = this.fdsConfigRepo.findByCode(FdsConfigEnum.FLIGHTMSG_LENGTH.getCode()).get(0);
    config.setValue(String.valueOf(length));
    this.fdsConfigRepo.save(config);
  }

  public void setFlightmsgIndex(long index) {
    FdsConfig config = this.fdsConfigRepo.findByCode(FdsConfigEnum.FLIGHTMSG_INDEX.getCode()).get(0);
    config.setValue(String.valueOf(index));
    this.fdsConfigRepo.save(config);
  }
  public void setFlowcontrolmsgLength(long length) {
    FdsConfig config = this.fdsConfigRepo.findByCode(FdsConfigEnum.FLOWCONTROLMSG_LENGTH.getCode()).get(0);
    config.setValue(String.valueOf(length));
    this.fdsConfigRepo.save(config);
  }
  public void setFlowcontrolmsgIndex(long index) {
    FdsConfig config = this.fdsConfigRepo.findByCode(FdsConfigEnum.FLOWCONTROLMSG_INDEX.getCode()).get(0);
    config.setValue(String.valueOf(index));
    this.fdsConfigRepo.save(config);
  }
}
