package com.one27001.util.storage;

import java.io.Serializable;

import lombok.Data;

@Data
public abstract class BaseJsonMCEntity implements Serializable {
  private String id;
  private long version = 0;
}
