package com.cscn.uranus.fds.config.service;

import com.cscn.uranus.fds.config.entity.FdsConfig;
import com.cscn.uranus.fds.config.repo.FdsConfigRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FdsConfigManager {

  private final FdsConfigRepo fdsConfigRepo;

  private final String DEFAULT_FLIGHTMSG_LENGTH_CODE = "FLIGHTMSG_LENGTH";
  private final String DEFAULT_FLIGHTMSG_INDEX_CODE = "FLIGHTMSG_INDEX";
  private final long DEFAULT_FLIGHTMSG_LENGTH_VALUE = 350;
  private final long DEFAULT_FLIGHTMSG_INDEX_VALUE = 1;

  private final String DEFAULT_FLOWCONTROLMSG_LENGTH_CODE = "FLOWCONTROLMSG_LENGTH";
  private final String DEFAULT_FLOWCONTROLMSG_INDEX_CODE = "FLOWCONTROLMSG_INDEX";
  private final long DEFAULT_FLOWCONTROLMSG_LENGTH_VALUE = 350;
  private final long DEFAULT_FLOWCONTROLMSG_INDEX_VALUE = 1;

  public FdsConfigManager(FdsConfigRepo fdsConfigRepo) {
    this.fdsConfigRepo = fdsConfigRepo;
    this.initDefaultConfig();
  }

  public void initDefaultConfig() {
    this.initDefaultFlightmsgConfig();
    this.initDefaultFlowcontrolmsgConfig();
  }

  public void initDefaultFlightmsgConfig() {
    long length = this.fdsConfigRepo.findByCode(this.DEFAULT_FLIGHTMSG_LENGTH_CODE).size();
    if (length < 1) {
      FdsConfig lengthConfig = new FdsConfig();
      lengthConfig.setCode(this.DEFAULT_FLIGHTMSG_LENGTH_CODE);
      lengthConfig.setValue(String.valueOf(this.DEFAULT_FLIGHTMSG_LENGTH_VALUE));
      this.fdsConfigRepo.save(lengthConfig);
    } else {
      this.setFlightmsgLength(this.DEFAULT_FLIGHTMSG_LENGTH_VALUE);
    }
    length = this.fdsConfigRepo.findByCode(this.DEFAULT_FLIGHTMSG_INDEX_CODE).size();
    if (length < 1) {
      FdsConfig indexConfig = new FdsConfig();
      indexConfig.setCode(this.DEFAULT_FLIGHTMSG_INDEX_CODE);
      indexConfig.setValue(String.valueOf(this.DEFAULT_FLIGHTMSG_INDEX_VALUE));
      this.fdsConfigRepo.save(indexConfig);
    } else {
      this.setFlowcontrolmsgIndex(this.DEFAULT_FLOWCONTROLMSG_INDEX_VALUE);
    }
  }

  public void initDefaultFlowcontrolmsgConfig() {
    long length = this.fdsConfigRepo.findByCode(this.DEFAULT_FLOWCONTROLMSG_LENGTH_CODE).size();
    if (length < 1) {
      FdsConfig lengthConfig = new FdsConfig();
      lengthConfig.setCode(this.DEFAULT_FLOWCONTROLMSG_LENGTH_CODE);
      lengthConfig.setValue(String.valueOf(this.DEFAULT_FLOWCONTROLMSG_LENGTH_VALUE));
      this.fdsConfigRepo.save(lengthConfig);
    } else {
      this.setFlowcontrolmsgLength(this.DEFAULT_FLOWCONTROLMSG_LENGTH_VALUE);

    }
    length = this.fdsConfigRepo.findByCode(this.DEFAULT_FLOWCONTROLMSG_INDEX_CODE).size();
    if (length < 1) {
      FdsConfig indexConfig = new FdsConfig();
      indexConfig.setCode(this.DEFAULT_FLOWCONTROLMSG_INDEX_CODE);
      indexConfig.setValue(String.valueOf(this.DEFAULT_FLOWCONTROLMSG_INDEX_VALUE));
      this.fdsConfigRepo.save(indexConfig);
    } else {
      this.setFlowcontrolmsgIndex(this.DEFAULT_FLOWCONTROLMSG_INDEX_VALUE);
    }
  }

  public void setFlightmsgLength(long length) {
    FdsConfig config = this.fdsConfigRepo.findByCode(this.DEFAULT_FLIGHTMSG_LENGTH_CODE).get(0);
    config.setValue(String.valueOf(length));
    this.fdsConfigRepo.save(config);
  }
  public void setFlightmsgIndex(long index) {
    FdsConfig config = this.fdsConfigRepo.findByCode(this.DEFAULT_FLIGHTMSG_INDEX_CODE).get(0);
    config.setValue(String.valueOf(index));
    this.fdsConfigRepo.save(config);
  }

  public Long getFlightmsgLength() {
    FdsConfig config = this.fdsConfigRepo.findByCode(this.DEFAULT_FLIGHTMSG_LENGTH_CODE).get(0);
    return Long.parseLong(config.getValue());
  }

  public void setFlowcontrolmsgLength(long length) {
    FdsConfig config = this.fdsConfigRepo.findByCode(this.DEFAULT_FLOWCONTROLMSG_LENGTH_CODE).get(0);
    config.setValue(String.valueOf(length));
    this.fdsConfigRepo.save(config);
  }
  public Long getFlightmsgIndex() {
    FdsConfig config = this.fdsConfigRepo.findByCode(this.DEFAULT_FLIGHTMSG_INDEX_CODE).get(0);
    return Long.parseLong(config.getValue());
  }

  public void setFlowcontrolmsgIndex(long index) {
    FdsConfig config = this.fdsConfigRepo.findByCode(this.DEFAULT_FLOWCONTROLMSG_INDEX_CODE).get(0);
    config.setValue(String.valueOf(index));
    this.fdsConfigRepo.save(config);
  }

  public Long getFlowcontrolmsgLength() {
    FdsConfig config =
        this.fdsConfigRepo.findByCode(this.DEFAULT_FLOWCONTROLMSG_LENGTH_CODE).get(0);
    return Long.parseLong(config.getValue());
  }

  public Long getFlowcontrolmsgIndex() {
    FdsConfig config = this.fdsConfigRepo.findByCode(this.DEFAULT_FLOWCONTROLMSG_INDEX_CODE).get(0);
    return Long.parseLong(config.getValue());
  }
}
