package com.one27001.tracker;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.apache.logging.log4j.Logger;

import com.one27001.tracker.blocks.ChecklistBlock;
import com.one27001.tracker.items.ChecklistItem;
import com.one27001.tracker.service.CatalogService;
import com.one27001.tracker.service.ChecklistSelectionService;
import com.one27001.tracker.service.ChecklistService;
import com.one27001.tracker.service.ConfigService;
import com.one27001.tracker.service.PersistenceService;
import com.one27001.util.ClassRegistry;
import com.one27001.util.MyLogger;

@Environment(EnvType.CLIENT)
public class ItemCollectionTracker implements ClientModInitializer {
  private static final Logger log = MyLogger.get();

  private static final Block CHECKLIST_LECTERN = new ChecklistBlock();

  @Override
  public void onInitializeClient() {
    this.registerInternalClasses();
    this.registerChecklistLectern();
    log.info("item-collection-tracker is initialized on the client!");
  }

  private void registerInternalClasses() {
    ClassRegistry.init(log);
    ClassRegistry.register(new PersistenceService());
    ClassRegistry.register(new ConfigService(
      ClassRegistry.supply(PersistenceService.class)));
    ClassRegistry.register(new CatalogService(Registry.ITEM));
    ClassRegistry.register(new ChecklistSelectionService(
      ClassRegistry.supply(ConfigService.class),
        ClassRegistry.supply(PersistenceService.class)));
    ClassRegistry.register(new ChecklistService(
        ClassRegistry.supply(CatalogService.class),
        ClassRegistry.supply(ConfigService.class),
        ClassRegistry.supply(PersistenceService.class)));
  }

  private void registerChecklistLectern() {
    Registry.register(Registry.BLOCK, new Identifier("one27001", "checklist_lectern"), CHECKLIST_LECTERN);
    Registry.register(Registry.ITEM, new Identifier("one27001", "checklist_lectern"),
        new ChecklistItem(CHECKLIST_LECTERN, new FabricItemSettings()));
  }
}
