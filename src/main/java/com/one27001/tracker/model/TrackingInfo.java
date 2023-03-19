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
public class TrackingInfo implements Serializable {
  private CatalogItem itemInfo;
  private boolean star;
  private Long targetQuantity;
  private Long collectedQuantity;
}
