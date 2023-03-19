package com.one27001.tracker.persistence;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.Logger;

import com.google.gson.GsonBuilder;
import com.one27001.tracker.model.Checklist;
import com.one27001.tracker.model.GlobalConfig;
import com.one27001.tracker.persistence.entity.*;
import com.one27001.tracker.service.PersistenceService;
import com.one27001.util.MyLogger;
import com.one27001.util.storage.BaseJsonMCEntity;
import com.one27001.util.storage.JsonMCConfigurer;
import com.one27001.util.storage.JsonMCTemplate;

import net.fabricmc.loader.api.FabricLoader;

public class JsonLocalPersistence implements PersistenceService {
  private static final Logger log = MyLogger.get();
  private static boolean DISALLOW_UPSERT_ON_SAVE = false;

  private JsonMCTemplate jsonMCTemplate;

  @Override
  public void init() throws Exception {
    PersistenceService.super.init();

    this.jsonMCTemplate = new JsonMCTemplate(JsonMCConfigurer.builder()
      .basePath(FabricLoader.getInstance().getGameDir().resolve("item-collection-tracker").toString())
      .configPath(FabricLoader.getInstance().getConfigDir().resolve("item-collection-tracker").toString())
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
      GlobalConfigEntity entity = this.jsonMCTemplate.getConfigTemplate().findByID(id, GlobalConfigEntity.class);
      return Optional.ofNullable(entity).map(GlobalConfigEntity::getConfig).orElse(null);
    } catch (Exception e) {
      log.error("Failed to fetch GlobalConfigEntity with id: {}", id, e);
      return null;
    }
  }

  @Override
  public GlobalConfig saveGlobalConfig(GlobalConfig toPersist) {
    String id = GlobalConfigEntity.DEFAULT_PROFILE_ID;
    try {
      GlobalConfigEntity entity = this.jsonMCTemplate.getConfigTemplate().findByID(id, GlobalConfigEntity.class);
      if (Objects.isNull(entity)) {
        entity = GlobalConfigEntity.builder()
          .id(id)
          .build();
      }
      entity.setConfig(toPersist);
      return this.jsonMCTemplate.getConfigTemplate().save(entity, GlobalConfigEntity.class).getConfig();
    } catch (Exception e) {
      log.error("Failed to saveGlobalConfig with id: {}", id, e);
      return null;
    }
  }

  @Override
  public List<String> listChecklists() {
    try {
      return this.jsonMCTemplate.findAll(ChecklistEntity.class).stream()
        .sorted((a, b) -> a.getLastUpdated().compareTo(b.getLastUpdated()))
        .map(BaseJsonMCEntity::getId)
        .toList();
    } catch (Exception e) {
      log.error("Failed to listChecklists", e);
      return null;
    }
  }

  @Override
  public Checklist saveNewChecklist(Checklist checklist) {
    try {
      ChecklistEntity toPersist = ChecklistEntity.builder()
        .id(checklist.getChecklistID())
        .checklist(Objects.requireNonNull(checklist))
        .build();
      return this.jsonMCTemplate.save(toPersist, DISALLOW_UPSERT_ON_SAVE, ChecklistEntity.class)
        .getChecklist();
    } catch (Exception e) {
      log.error("Failed to persistChecklist with id: {}", checklist.getChecklistID(), e);
      return null;
    }
  }

  @Override
  public Checklist findChecklistByID(String checklistID) {
    try {
    ChecklistEntity entity = this.jsonMCTemplate.findByID(checklistID, ChecklistEntity.class);
    return Optional.ofNullable(entity).map(ChecklistEntity::getChecklist).orElse(null);
    } catch (Exception e) {
      log.error("Failed to findChecklistByID with id: {}", checklistID, e);
      return null;
    }
  }

  @Override
  public Checklist persistChecklist(Checklist checklist) {
    // TODO Auto-generated method stub
    return null;
  }
}
