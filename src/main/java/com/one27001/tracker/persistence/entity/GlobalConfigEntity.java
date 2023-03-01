package com.one27001.tracker.persistence.entity;

import com.one27001.tracker.model.GlobalConfig;
import com.one27001.util.storage.BaseJsonMCEntity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GlobalConfigEntity extends BaseJsonMCEntity {
  public static final String DEFAULT_PROFILE_ID = "default";
  private GlobalConfig config;

  @Builder
  public GlobalConfigEntity(String id, GlobalConfig config) {
    this.setId(id);
    this.setConfig(config);
  }
}
