package com.one27001.tracker.persistence;

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

  @Override
  public void init() throws Exception {
    super.init();
    this.jsonMCTemplate = new JsonMCTemplate(JsonMCConfigurer.builder()
      .basePath(FabricLoader.getInstance().getGameDir().resolve("item-collection-tracker").toString())
      .logger(MyLogger.get())
      .gson(new GsonBuilder()
        .setPrettyPrinting()
        .create())
      .build());
  }

  @Override
  public GlobalConfig fetchGlobalConfig() {
    // TODO: continue implementation here
    return null;
  }

  @Override
  public GlobalConfig saveGlobalConfig(GlobalConfig toPersist) {
    try {
      return this.jsonMCTemplate.save(GlobalConfigEntity.builder().config(toPersist).build()).getConfig();
    } catch (Exception e) {
      log.error("Failed to saveGlobalConfig {}", toPersist, e);
      return null;
    }
  }

}
