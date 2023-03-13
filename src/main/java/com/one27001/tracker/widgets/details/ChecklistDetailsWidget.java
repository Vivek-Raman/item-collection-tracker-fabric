package com.one27001.tracker.widgets.details;

import com.one27001.tracker.model.Checklist;
import com.one27001.tracker.model.TrackingInfo;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.background.EmptyBackground;
import dev.lambdaurora.spruceui.border.EmptyBorder;
import dev.lambdaurora.spruceui.widget.SpruceLabelWidget;
import dev.lambdaurora.spruceui.widget.SpruceWidget;
import dev.lambdaurora.spruceui.widget.container.SpruceEntryListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class ChecklistDetailsWidget extends SpruceEntryListWidget<ChecklistDetailsWidget.Entry> {
  private static final int BUTTON_WIDTH = 200;
  private static final int BUTTON_HEIGHT = 20;
  private static final int BUTTON_MARGIN = 4;

  private final Checklist checklist;

  public ChecklistDetailsWidget(Checklist checklist, Position position, int width, int height) {
    super(position, width, height, BUTTON_MARGIN, ChecklistDetailsWidget.Entry.class);
    this.setBackground(EmptyBackground.EMPTY_BACKGROUND);
    this.setBorder(EmptyBorder.EMPTY_BORDER);
    this.setRenderTransition(false);

    this.checklist = checklist;
    this.checklist.getStats().forEach((String itemID, TrackingInfo info) -> {
      this.addEntry(new Entry(this, itemID, info));
    });
  }


  public static class Entry extends SpruceEntryListWidget.Entry {
    private SpruceWidget widget;
    private TrackingInfo info;

    public Entry(SpruceWidget parent, String itemID, TrackingInfo info) {
      super();
      this.info = info;
      this.widget = new SpruceLabelWidget(Position.of(this, 0, BUTTON_HEIGHT),
        new LiteralText(itemID), BUTTON_WIDTH);
    }

    @Override
    protected void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.widget.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public int getHeight() {
      return BUTTON_HEIGHT + BUTTON_MARGIN;
    }
  }
}
