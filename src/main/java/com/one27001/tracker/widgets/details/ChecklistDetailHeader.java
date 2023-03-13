package com.one27001.tracker.widgets.details;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.widget.SpruceLabelWidget;
import dev.lambdaurora.spruceui.widget.container.SpruceContainerWidget;
import net.minecraft.text.LiteralText;

public class ChecklistDetailHeader extends SpruceContainerWidget {
  private static final int BUTTON_WIDTH = 200;
  private static final int BUTTON_HEIGHT = 20;
  private static final int VERTICAL_MARGIN = 16;

  public ChecklistDetailHeader(Position position, int width, int height) {
    super(position, width, height);

    this.addChild(new SpruceLabelWidget(Position.of(position, 0, 0),
      new LiteralText("asd"), BUTTON_WIDTH));

    this.addChild(new SpruceLabelWidget(Position.of(position, 50, 0),
      new LiteralText("asd2"), BUTTON_WIDTH));
  }
}
