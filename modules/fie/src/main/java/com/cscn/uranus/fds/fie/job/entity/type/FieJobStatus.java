package com.cscn.uranus.fds.fie.job.entity.type;

public enum FieJobStatus {
  RUNNING("Running"),
  PAUSE("Pause");

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  FieJobStatus(String name) {
    this.name = name;
  }
}
