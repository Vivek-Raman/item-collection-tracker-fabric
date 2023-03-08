package com.one27001.util.storage;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public abstract class BaseJsonMCEntity implements Serializable {
  private String id;
  private long version = 0;
  private Date created;
  private Date lastUpdated;

  public long incrementVersion() {
    return ++this.version;
  }
}
