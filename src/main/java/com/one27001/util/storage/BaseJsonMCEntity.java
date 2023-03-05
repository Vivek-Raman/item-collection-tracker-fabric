package com.one27001.util.storage;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public abstract class BaseJsonMCEntity implements Serializable {
  private String id;
  private long version = 0;
  private LocalDateTime created;
  private LocalDateTime lastUpdated;

  public long incrementVersion() {
    return ++this.version;
  }
}
