package com.one27001.tracker.persistence.repository;

import java.nio.file.Path;

import com.one27001.tracker.model.GlobalConfig;
import com.one27001.tracker.persistence.entity.GlobalConfigEntity;
import com.one27001.tracker.service.PersistenceService;

import io.jsondb.JsonDBTemplate;
import net.fabricmc.loader.api.FabricLoader;

public class JsonDBRepository extends PersistenceService {
  private static final String LOCAL_DB_FILE_PATH = "item-tracker";
  private JsonDBTemplate jsonDBTemplate = null;

  @Override
  public void init() {
    super.init();

    Path persistenceFilePath = FabricLoader.getInstance().getGameDir().resolve(LOCAL_DB_FILE_PATH);
    this.jsonDBTemplate = new JsonDBTemplate(persistenceFilePath.toString(),
      "com.one27001.tracker.persistence.entity");
  }

  @Override
  public GlobalConfig fetchGlobalConfig() {
    GlobalConfigEntity config = this.jsonDBTemplate.findOne("", GlobalConfigEntity.class);
    return config;
  }

  @Override
  public void saveGlobalConfig(GlobalConfig toPersist) {
    toPersist.setVersion(toPersist.getVersion() + 1);
    this.jsonDBTemplate.save(GlobalConfigEntity.fromGlobalConfig(toPersist),
      GlobalConfigEntity.class);
  }
}
