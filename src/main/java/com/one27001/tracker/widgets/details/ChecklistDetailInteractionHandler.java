package com.one27001.tracker.widgets.details;

public interface ChecklistDetailInteractionHandler {
  void onStarButtonPressed(String itemID);
  void onCurrentQuantityChanged(String itemID, int newCurrentQuantity);
  void onTargetQuantityChanged(String itemID, int newTargetQuantity);
  boolean isLocked();
}
