package com.one27001.tracker.service;

import java.util.Arrays;
import java.util.List;

import com.one27001.tracker.model.Checklist;
import com.one27001.tracker.model.GlobalConfig;
import com.one27001.util.Registerable;

public abstract class PersistenceService implements Registerable {
  public Checklist findChecklistByID(String checklistID) {
    // TODO: implement
    return null;
  }

  public Checklist persistChecklist(Checklist checklist) {
    // TODO: implement persistence
    return checklist;
  }

  public List<String> listChecklists() {
    // TODO: implement persistence, resume here
    return Arrays.asList("myDevList", "myDevList12");
  }

  public abstract GlobalConfig fetchGlobalConfig();

  public abstract GlobalConfig saveGlobalConfig(GlobalConfig toPersist);
}
