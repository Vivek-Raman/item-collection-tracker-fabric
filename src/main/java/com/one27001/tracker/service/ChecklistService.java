package com.one27001.tracker.service;

import com.one27001.tracker.model.CatalogItem;
import com.one27001.tracker.model.Checklist;
import com.one27001.tracker.model.TrackingInfo;

public class ChecklistService {
  /**
   * Fetch checklist from the data source
   * @param checklistID
   * @return
   */
  public Checklist getChecklist(String checklistID) {
    return Checklist.builder()
    .checklistID(checklistID)
    .item(new CatalogItem("minecraft:iron_block"), TrackingInfo.builder()
        .targetQuantity(65)
        .collectedQuantity(2)
        .build())
    .build();
  }

  /**
   * Determines if the item catalog should be re-generated.
   * @return true if the catalog should be regenerated.
   */
  public boolean requireCatalogRegeneration() {
    return true;
  }

  /**
   * Generates the catalog.
   */
  public void generateAndSaveCatalog() {

  }
}
