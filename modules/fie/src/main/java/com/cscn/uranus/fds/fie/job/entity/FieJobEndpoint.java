package com.cscn.uranus.fds.fie.job.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class FieJobEndpoint {

  @Id
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "job", foreignKey = @ForeignKey(name = "fie_job_endpoint_job_fk"))
  private FieJob fieJob;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "endpoint", foreignKey = @ForeignKey(name = "fie_job_endpoint_endpoint_fk"))
  private FieEndpoint fieEndpoint;

  @Column(updatable = false)
  private LocalDateTime creationTime;

  private LocalDateTime updateTime;

  public FieJobEndpoint(FieJob fieJob, FieEndpoint fieEndpoint) {
    this.fieJob = fieJob;
    this.fieEndpoint = fieEndpoint;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public FieJob getFieJob() {
    return fieJob;
  }

  public void setFieJob(FieJob fieJob) {
    this.fieJob = fieJob;
  }

  public FieEndpoint getFieEndpoint() {
    return fieEndpoint;
  }

  public void setFieEndpoint(FieEndpoint fieEndpoint) {
    this.fieEndpoint = fieEndpoint;
  }

  public LocalDateTime getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(LocalDateTime creationTime) {
    this.creationTime = creationTime;
  }

  public LocalDateTime getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(LocalDateTime updateTime) {
    this.updateTime = updateTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FieJobEndpoint that = (FieJobEndpoint) o;
    return Objects.equals(fieJob, that.fieJob) &&
        Objects.equals(fieEndpoint, that.fieEndpoint);
  }

  @Override
  public int hashCode() {

    return Objects.hash(fieJob, fieEndpoint);
  }

  /**
   * JPA持久化前钩子
   *
   * <p>在创建记录前生成 creationTime和updateTime
   */
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
