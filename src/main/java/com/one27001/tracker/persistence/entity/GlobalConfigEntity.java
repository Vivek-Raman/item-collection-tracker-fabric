package com.one27001.tracker.persistence.entity;

import com.one27001.tracker.model.GlobalConfig;
import com.one27001.util.storage.BaseJsonMCEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GlobalConfigEntity extends BaseJsonMCEntity {
  @Builder.Default private String profileID = "default";
  private GlobalConfig config;
}
