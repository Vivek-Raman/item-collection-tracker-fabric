package com.one27001.tracker;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.apache.logging.log4j.Logger;

import com.one27001.tracker.blocks.ChecklistBlock;
import com.one27001.tracker.items.ChecklistItem;
import com.one27001.util.MyLogger;

public class ItemCollectionTracker implements ModInitializer {
  private static final Logger log = MyLogger.get();

  private static final Block CHECKLIST_LECTERN = new ChecklistBlock(FabricBlockSettings.of(Material.WOOD));

  @Override
  public void onInitialize() {
    Registry.register(Registry.BLOCK, new Identifier("one27001", "checklist_lectern"), CHECKLIST_LECTERN);
    Registry.register(Registry.ITEM, new Identifier("one27001", "checklist_lectern"),
        new ChecklistItem(CHECKLIST_LECTERN, new FabricItemSettings()));

    log.info("Imported !");
  }
}
