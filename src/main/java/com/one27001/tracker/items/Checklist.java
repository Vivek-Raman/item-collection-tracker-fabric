package com.one27001.tracker.items;

import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class Checklist extends Item {

  public Checklist(Settings settings) {
    super(settings);
    settings.maxCount(1);
    settings.rarity(Rarity.EPIC);
  }


}
