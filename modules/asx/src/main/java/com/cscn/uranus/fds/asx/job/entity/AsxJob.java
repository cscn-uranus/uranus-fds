package com.cscn.uranus.fds.asx.job.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.hibernate.annotations.NaturalId;


@Entity
public class AsxJob implements Serializable {

  @Id
  private String id;

  @NaturalId
  private String name;

  private String cronExpression;

  private LocalDateTime creationTime;

  private LocalDateTime updateTime;

  public AsxJob() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCronExpression() {
    return cronExpression;
  }

  public void setCronExpression(String cronExpression) {
    this.cronExpression = cronExpression;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AsxJob asxJob = (AsxJob) o;
    return Objects.equals(name, asxJob.name);
  }

  @Override
  public int hashCode() {

    return Objects.hash(name);
  }

  @PrePersist
  public void onCreate() {
    this.id = UUID.randomUUID().toString();
    this.creationTime = LocalDateTime.now();
    this.updateTime = LocalDateTime.now();
  }

  /**
   * JPA更新前钩子
   *
   * <p>在更新记录前生成updateTime
   */
  @PreUpdate
  public void onUpdate() {
    this.updateTime = LocalDateTime.now();
  }
}
