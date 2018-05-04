package com.cscn.uranus.fds.asx.endpoint.entity;

import com.cscn.uranus.fds.asx.endpoint.AsxEndpointType;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.hibernate.annotations.NaturalId;

@Entity
public class AsxEndpoint {
  @Id
  private String id;

  @NaturalId
  private String name;

  private AsxEndpointType type;

  private String uri;

  private LocalDateTime creationTime;

  private LocalDateTime updateTime;

  public AsxEndpoint() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public AsxEndpointType getType() {
    return type;
  }

  public void setType(AsxEndpointType type) {
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
    if (!(o instanceof AsxEndpoint)) {
      return false;
    }
    AsxEndpoint that = (AsxEndpoint) o;
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
