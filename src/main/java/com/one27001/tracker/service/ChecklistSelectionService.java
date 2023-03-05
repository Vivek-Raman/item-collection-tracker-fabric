package com.one27001.tracker.service;

import java.util.List;

import com.one27001.tracker.model.GlobalConfig;
import com.one27001.util.Registerable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChecklistSelectionService implements Registerable {
  private final ConfigService configService;
  private final PersistenceService persistenceService;

  public List<String> listChecklists() {
    return this.persistenceService.listChecklists();
  }

  public void setActiveChecklistID(String checklistID) {
    GlobalConfig toUpdate = this.configService.get();
    toUpdate.setActiveChecklistID(checklistID);
    this.configService.update(toUpdate);
  }

  public String getActiveChecklistID() {
    return this.configService.get().getActiveChecklistID();

  }
}
