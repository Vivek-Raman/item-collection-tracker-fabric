package com.one27001.tracker.service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.one27001.tracker.model.CatalogItem;
import com.one27001.tracker.model.Checklist;
import com.one27001.tracker.model.TrackingInfo;
import com.one27001.util.MyLogger;
import com.one27001.util.Registerable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChecklistService implements Registerable {
  private static final Logger log = MyLogger.get();
  private final CatalogService catalogService;
  private final PersistenceService persistenceService;

  /**
   * Generate a new checklist from the Minecraft Item Registry.
   * Returns a copy of the checklist after persisting it.
   *
   * @param checklistID will identify the newly created checklist
   * @return persisted checklist
   */
  public Checklist generateChecklist(String checklistID) {
    List<CatalogItem> catalog = catalogService.generateCatalog();

    Map<String, TrackingInfo> stats = new LinkedHashMap<>();
    catalog.forEach(catalogItem -> {
      long targetQuantity = catalogItem.getStackQuantity() + 1;
      catalogItem.setStackQuantity(null);
      stats.put(catalogItem.getItemID(), TrackingInfo.builder()
        .itemInfo(catalogItem)
        .collectedQuantity(0L)
        .targetQuantity(targetQuantity)
        .build());
    });

    Checklist checklist = Checklist.builder()
      .checklistID(checklistID)
      .stats(stats)
      .build();
    return persistenceService.persistChecklist(checklist);
  }


  /**
   * Fetch checklist from the data source
   * @param checklistID
   * @return
   */
  public Checklist getChecklist(String checklistID) {
    return persistenceService.findChecklistByID(checklistID);
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
