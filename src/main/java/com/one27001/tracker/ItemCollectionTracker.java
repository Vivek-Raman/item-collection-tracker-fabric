package com.one27001.tracker;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.apache.logging.log4j.Logger;

import com.one27001.tracker.blocks.ChecklistBlock;
import com.one27001.tracker.items.ChecklistItem;
import com.one27001.tracker.service.ChecklistService;
import com.one27001.util.ClassRegistry;
import com.one27001.util.MyLogger;

@Environment(EnvType.CLIENT)
public class ItemCollectionTracker implements ClientModInitializer {
  private static final Logger log = MyLogger.get();

  private static final Block CHECKLIST_LECTERN = new ChecklistBlock(FabricBlockSettings.of(Material.WOOD));

  @Override
  public void onInitializeClient() {
    this.registerInternalClasses();
    this.registerChecklistLectern();
    this.initializeCatalog();
    this.initializeChecklist();


    log.info("Imported !");
  }

  private void registerInternalClasses() {
    ClassRegistry.init(log);
    ClassRegistry.register(new ChecklistService());
  }

  private void registerChecklistLectern() {
    Registry.register(Registry.BLOCK, new Identifier("one27001", "checklist_lectern"), CHECKLIST_LECTERN);
    Registry.register(Registry.ITEM, new Identifier("one27001", "checklist_lectern"),
        new ChecklistItem(CHECKLIST_LECTERN, new FabricItemSettings()));
  }

  private void initializeCatalog() {
  }

  private void initializeChecklist() {
  }
}
