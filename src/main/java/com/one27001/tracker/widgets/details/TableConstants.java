package com.one27001.tracker.widgets.details;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class TableConstants {
  @RequiredArgsConstructor
  public static class Position {
    private final Size sizeOf;

    public int icon() {
      return 0;
    }

    public int name() {
      return icon() + sizeOf.icon();
    }

    public int targetQuantity() {
      return name() + sizeOf.name();
    }

    public int currentQuantity() {
      return targetQuantity() + sizeOf.targetQuantity();
    }

    public int star() {
      return currentQuantity() + sizeOf.currentQuantity();
    }
  }

  @RequiredArgsConstructor
  public static class Size {
    private final int contentWidth;

    private static final int SIZE_X_ICON = 32;
    public static int icon() {
      return SIZE_X_ICON;
    }

    public int name() {
      return contentWidth - icon() -
        targetQuantity() - currentQuantity() - star();
    }

    private static final int SIZE_X_QUANTITY = 50;
    public int targetQuantity() {
      return SIZE_X_QUANTITY;
    }

    public int currentQuantity() {
      return SIZE_X_QUANTITY;
    }

    public int star() {
      return SIZE_X_ICON;
    }
  }

  // icon: fixed size
  // name: proportional to table width
  // collected qty: proportional to table width
  // target qty: proportional to table width
}
