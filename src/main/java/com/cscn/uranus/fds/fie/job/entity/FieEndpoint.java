package com.cscn.uranus.fds.fie.job.entity;

import com.cscn.uranus.fds.fie.job.entity.type.FieEndpointType;
import java.time.LocalDateTime;
import java.util.HashSet;
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
public class FieEndpoint {
  @Id
  private String id;

  @OneToMany(
      mappedBy = "fieEndpoint",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private Set<FieJobEndpoint> jobEndpoints = new HashSet<>();

  @NaturalId
  private String name;

  @NaturalId
  private String uri;

  private FieEndpointType type;

  private LocalDateTime creationTime;

  private LocalDateTime updateTime;

  public FieEndpoint() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public FieEndpointType getType() {
    return type;
  }

  public void setType(FieEndpointType type) {
    this.type = type;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FieEndpoint)) {
      return false;
    }
    FieEndpoint that = (FieEndpoint) o;
    return Objects.equals(name, that.name);
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
