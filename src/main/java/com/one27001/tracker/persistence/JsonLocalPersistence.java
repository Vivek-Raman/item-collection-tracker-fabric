package com.one27001.tracker.persistence;

import java.util.Optional;

import org.apache.logging.log4j.Logger;

import com.google.gson.GsonBuilder;
import com.one27001.tracker.model.GlobalConfig;
import com.one27001.tracker.persistence.entity.GlobalConfigEntity;
import com.one27001.tracker.service.PersistenceService;
import com.one27001.util.MyLogger;
import com.one27001.util.storage.JsonMCConfigurer;
import com.one27001.util.storage.JsonMCTemplate;

import net.fabricmc.loader.api.FabricLoader;

public class JsonLocalPersistence extends PersistenceService {
  private static final Logger log = MyLogger.get();
  private JsonMCTemplate jsonMCTemplate;
  private JsonMCTemplate configJsonMCTemplate;

  @Override
  public void init() throws Exception {
    super.init();

    this.jsonMCTemplate = new JsonMCTemplate(JsonMCConfigurer.builder()
      .basePath(FabricLoader.getInstance().getGameDir().resolve("item-collection-tracker").toString())
      .logger(log)
      .gson(new GsonBuilder()
        .setPrettyPrinting()
        .create())
      .build());

    this.configJsonMCTemplate = new JsonMCTemplate(JsonMCConfigurer.builder()
      .basePath(FabricLoader.getInstance().getConfigDir().resolve("item-collection-tracker").toString())
      .logger(log)
      .gson(new GsonBuilder()
        .setPrettyPrinting()
        .create())
      .build());
  }

  @Override
  public GlobalConfig fetchGlobalConfig() {
    String id = GlobalConfigEntity.DEFAULT_PROFILE_ID;
    try {
      GlobalConfigEntity entity = this.configJsonMCTemplate.findByID(id, GlobalConfigEntity.class);
      log.info("Fetched config {} from file", entity);
      return Optional.ofNullable(entity).map(GlobalConfigEntity::getConfig).orElse(null);
    } catch (Exception e) {
      log.error("Failed to fetch GlobalConfigEntity with id: {}", id, e);
      return null;
    }
  }

  @Override
  public GlobalConfig saveGlobalConfig(GlobalConfig toPersist) {
    GlobalConfigEntity entity = GlobalConfigEntity.builder()
      .id(GlobalConfigEntity.DEFAULT_PROFILE_ID)
      .config(toPersist)
      .build();
    try {
      return this.configJsonMCTemplate.save(entity, GlobalConfigEntity.class).getConfig();
    } catch (Exception e) {
      log.error("Failed to saveGlobalConfig {}", entity, e);
      return null;
    }
  }

}
