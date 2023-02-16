package com.one27001.tracker.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Rarity;

public class ChecklistItem extends BlockItem {



  public ChecklistItem(Block block, Settings settings) {
    super(block, settings);
    settings.maxCount(1);
    settings.rarity(Rarity.EPIC);
  }
}
