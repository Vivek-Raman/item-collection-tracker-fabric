package com.one27001.tracker.service;

import org.apache.logging.log4j.Logger;

import com.one27001.tracker.model.Checklist;
import com.one27001.tracker.model.TrackingInfo;
import com.one27001.util.MyLogger;
import com.one27001.util.Registerable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChecklistService implements Registerable {
  private static final Logger log = MyLogger.get();
  private final CatalogService catalogService;

  /**
   * Determines if the item catalog should be re-generated.
   * @return true if the catalog should be regenerated.
   */
  public boolean requireCatalogRegeneration(String checklistID) {
    return true;
  }


  /**
   * Fetch checklist from the data source
   * @param checklistID
   * @return
   */
  public Checklist getChecklist(String checklistID) {
    // TODO: implement with persistence layer
    return Checklist.builder()
        .checklistID(checklistID)
        .item("minecraft:iron_block", TrackingInfo.builder()
            .targetQuantity(65)
            .collectedQuantity(2)
            .build())
        .build();
  }

  public void starItem(String checklistID, String itemID) {
    // TODO: implement
    log.info("Starred item {} in checklist {}!", itemID, checklistID);
  }

  public void setCollectedQuantity(String checklistID, String itemID, long collectedQuantity) {
    // TODO: implement
    log.info("Set collected quantity to {} for item {} in checklist {}!",
        collectedQuantity, itemID, checklistID);
  }

  public void setTargetQuantity(String checklistID, String itemID, long targetQuantity) {
    // TODO: implement
    log.info("Set target quantity to {} for item {} in checklist {}!",
        targetQuantity, itemID, checklistID);
}

  /**
   * Locks the checklist. Disallows editing of the target quantity.
   */
  public void lockChecklist(String checklistID) {
    // TODO: implement
    log.info("Locked checklist {}!", checklistID);
  }
}
