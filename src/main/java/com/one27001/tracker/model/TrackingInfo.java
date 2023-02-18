package com.one27001.tracker.model;

import net.minecraft.util.Identifier;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TrackingInfo extends CatalogItem {
  private long targetQuantity;
  private long collectedQuantity;

  @Builder
  public TrackingInfo(Identifier itemID, long targetQuantity, long collectedQuantity) {
    this.setItemID(itemID);
    this.setTargetQuantity(targetQuantity);
    this.setCollectedQuantity(collectedQuantity);
  }
}
