package com.one27001.tracker.service;

import java.util.Objects;

import com.one27001.tracker.model.GlobalConfig;
import com.one27001.util.Registerable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConfigService implements Registerable {
  private final PersistenceService persistenceService;

  private GlobalConfig instance = null;

  @Override
  public void init() throws Exception {
    Registerable.super.init();

    if (Objects.isNull(instance)) {
      instance = this.persistenceService.fetchGlobalConfig();
    }

    if (Objects.isNull(instance)) {
      GlobalConfig defaultConfig = GlobalConfig.builder()
        .activeChecklistID("test")
        .build();
      instance = this.persistenceService.saveGlobalConfig(defaultConfig);
    }
  }

  public GlobalConfig get() {
    if (Objects.isNull(instance)) {
      instance = this.persistenceService.fetchGlobalConfig();
    }

    return instance;
  }

  public GlobalConfig update(GlobalConfig toPersist) {
    instance = this.persistenceService.saveGlobalConfig(toPersist);
    return instance;
  }
}
