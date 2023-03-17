package com.one27001.tracker.widgets.details;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.widget.SpruceLabelWidget;
import dev.lambdaurora.spruceui.widget.container.SpruceContainerWidget;
import net.minecraft.text.LiteralText;

public class ChecklistDetailHeader extends SpruceContainerWidget {
  public ChecklistDetailHeader(Position position, int width, int height) {
    super(position, width, height);
    TableConstants.Size sizeOf = new TableConstants.Size(width);
    TableConstants.Position positionOf = new TableConstants.Position(sizeOf);

    this.addChild(new SpruceLabelWidget(Position.of(position, positionOf.icon(), 0),
      new LiteralText("ICON"), sizeOf.icon()));

    this.addChild(new SpruceLabelWidget(Position.of(position, positionOf.name(), 0),
      new LiteralText("NAME"), sizeOf.name()));

    this.addChild(new SpruceLabelWidget(Position.of(position, positionOf.targetQuantity(), 0),
      new LiteralText("TARGET"), sizeOf.targetQuantity()));

    this.addChild(new SpruceLabelWidget(Position.of(position, positionOf.currentQuantity(), 0),
      new LiteralText("CURRENT"), sizeOf.currentQuantity()));

    this.addChild(new SpruceLabelWidget(Position.of(position, positionOf.star(), 0),
      new LiteralText("STAR"), sizeOf.star()));
  }
}
