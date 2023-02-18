package com.one27001.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minecraft.util.Identifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogItem {
  private Identifier itemID;

  public CatalogItem(String itemID) {
    this.itemID = Identifier.tryParse(itemID);
  }
}
