package com.one27001.tracker.service;

import java.util.Objects;

import org.apache.logging.log4j.Logger;

import com.one27001.tracker.model.GlobalConfig;
import com.one27001.util.MyLogger;
import com.one27001.util.Registerable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConfigService implements Registerable {
  private static final Logger log = MyLogger.get();

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
        .activeChecklistID(null)
        .build();
      log.warn("Config not found; persisting new config {}", defaultConfig);
      instance = this.persistenceService.saveGlobalConfig(defaultConfig);
    }

    log.info("Loaded config: {}", instance);
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
