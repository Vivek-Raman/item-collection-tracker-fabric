package com.one27001.tracker.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TrackingInfo implements Serializable {
  private CatalogItem itemInfo;
  private Long targetQuantity;
  private Long collectedQuantity;
}
