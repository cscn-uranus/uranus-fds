package com.cscn.uranus.fds.fie.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class FieGram implements Serializable {

  @Id
  private String id;

  private String header;

  @Lob
  private String content;

  private String tail;

  @Column(updatable = false)
  private LocalDateTime creationTime;

  private LocalDateTime updateTime;

  public FieGram() {
  }

  public FieGram(String content) {
    this.content = content;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getHeader() {
    return header;
  }

  public String getTail() {
    return tail;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
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
    if (!(o instanceof FieGram)) {
      return false;
    }
    FieGram that = (FieGram) o;
    return Objects.equals(content, that.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(content);
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
