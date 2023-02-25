package com.one27001.tracker.service;

import java.util.Arrays;
import java.util.List;

import com.one27001.util.Registerable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChecklistSelectionService implements Registerable {
  private final PersistenceService persistenceService;

  public List<String> listChecklists() {
    // TODO: implement
    return Arrays.asList("myDevList", "myDevList12");
  }
}
