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
  public void init() {
    Registerable.super.init();

    if (Objects.isNull(instance)) {
      instance = this.persistenceService.fetchGlobalConfig();
    }

    if (Objects.isNull(instance)) {
      GlobalConfig defaultConfig = GlobalConfig.builder()
        .activeChecklistID(null)
        .build();
      this.update(defaultConfig);
    }
  }

  public GlobalConfig get() {
    if (Objects.isNull(instance)) {
      instance = this.persistenceService.fetchGlobalConfig();
    }

    return instance;
  }

  public void update(GlobalConfig toPersist) {
    this.persistenceService.saveGlobalConfig(toPersist);
    instance = toPersist;
  }
}
