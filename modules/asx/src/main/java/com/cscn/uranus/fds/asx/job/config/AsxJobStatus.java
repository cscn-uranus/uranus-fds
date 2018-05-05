package com.cscn.uranus.fds.asx.job.config;

public enum AsxJobStatus {
  RUNNING("Running"),
  PAUSE("Pause");

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  AsxJobStatus(String name) {
    this.name = name;
  }
}
