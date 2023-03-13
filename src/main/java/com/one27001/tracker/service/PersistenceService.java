package com.one27001.tracker.service;

import java.util.List;

import com.one27001.tracker.model.Checklist;
import com.one27001.tracker.model.GlobalConfig;
import com.one27001.util.Registerable;

public interface PersistenceService extends Registerable {
  default void init() throws Exception {
    Registerable.super.init();
  }

  GlobalConfig fetchGlobalConfig();

  GlobalConfig saveGlobalConfig(GlobalConfig toPersist);

  Checklist saveNewChecklist(Checklist checklist);

  List<String> listChecklists();

  Checklist findChecklistByID(String checklistID);

  Checklist persistChecklist(Checklist checklist);
}
