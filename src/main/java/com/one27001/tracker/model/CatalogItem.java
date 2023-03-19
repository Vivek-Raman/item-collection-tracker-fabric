package com.one27001.tracker.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogItem implements Serializable, Comparable<CatalogItem> {
  private String itemID;
  private String itemGroup;
  private Long stackQuantity;

  @Override
  public int compareTo(CatalogItem other) {
    return this.getItemID().compareTo(((CatalogItem) other).getItemID());
  }
}
