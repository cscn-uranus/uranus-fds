package com.cscn.uranus.fds.fie.job.entity;

import com.cscn.uranus.fds.fie.job.entity.type.FieJobStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.hibernate.annotations.NaturalId;


@Entity
public class FieJob implements Serializable {

  @Id
  private String id;

  @OneToMany(
      mappedBy = "fieJob",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private Set<FieJobEndpoint> fieJobEndpoints = new HashSet<>();

  @NaturalId
  private String name;

  private String parentGroup;

  private String className;

  private String cronExpression;

  private FieJobStatus status;

  private LocalDateTime creationTime;

  private LocalDateTime updateTime;

  public FieJob() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getParentGroup() {
    return parentGroup;
  }

  public void setParentGroup(String parentGroup) {
    this.parentGroup = parentGroup;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getCronExpression() {
    return cronExpression;
  }

  public void setCronExpression(String cronExpression) {
    this.cronExpression = cronExpression;
  }

  public FieJobStatus getStatus() {
    return status;
  }

  public void setStatus(FieJobStatus status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FieJob fieJob = (FieJob) o;
    return Objects.equals(name, fieJob.name);
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

  public void addFieEndpoint(FieEndpoint fieEndpoint) {
    FieJobEndpoint fieJobEndpoint = new FieJobEndpoint(this,fieEndpoint);
    this.fieJobEndpoints.add(fieJobEndpoint);
  }
  public void removeFieEndpoint(FieEndpoint fieEndpoint) {
    for (Iterator<FieJobEndpoint> iterator = this.fieJobEndpoints.iterator();
        iterator.hasNext(); ) {
      FieJobEndpoint fieJobEndpoint = iterator.next();
      if (fieJobEndpoint.getFieJob().equals(this)
          && fieJobEndpoint.getFieEndpoint().equals(fieEndpoint)) {
        iterator.remove();
        fieJobEndpoint.setFieJob(null);
        fieJobEndpoint.setFieEndpoint(null);
      }
    }
  }
}
