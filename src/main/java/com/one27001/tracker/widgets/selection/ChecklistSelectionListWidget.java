package com.one27001.tracker.widgets.selection;

import java.util.List;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.background.EmptyBackground;
import dev.lambdaurora.spruceui.border.EmptyBorder;
import dev.lambdaurora.spruceui.widget.AbstractSpruceWidget;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.SpruceWidget;
import dev.lambdaurora.spruceui.widget.container.SpruceEntryListWidget;
import lombok.Getter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class ChecklistSelectionListWidget extends SpruceEntryListWidget<ChecklistSelectionListWidget.Entry> {
  private static final int BUTTON_WIDTH = 200;
  private static final int BUTTON_HEIGHT = 20;
  private static final int BUTTON_MARGIN = 4;

  public ChecklistSelectionListWidget(List<String> checklistIDs, ChecklistSelectionEntryClickedAction onSelected,
      Position position, int width, int height) {
    super(position, width, height, BUTTON_MARGIN, ChecklistSelectionListWidget.Entry.class);
    this.setBackground(EmptyBackground.EMPTY_BACKGROUND);
    this.setBorder(EmptyBorder.EMPTY_BORDER);
    this.setRenderTransition(false);

    for (String checklistID : checklistIDs) {
      this.addEntry(new Entry(this, checklistID, onSelected));
    }
  }

  @Override
  public boolean mouseClicked(double mouseX, double mouseY, int button) {
    super.mouseClicked(mouseX, mouseY, button);
    for (Entry entry : this.children()) {
      if (entry.isMouseOver(mouseX, mouseY)) {
        return entry.select();
      }
    }
    return false;
  }

  public static class Entry extends SpruceEntryListWidget.Entry {
    @Getter private final String checklistID;
    private final ChecklistSelectionEntryClickedAction onSelected;
    private final AbstractSpruceWidget widget;

    public Entry(SpruceWidget parent, String checklistID, ChecklistSelectionEntryClickedAction onSelected) {
      super();
      this.checklistID = checklistID;
      this.onSelected = onSelected;
      this.widget = new SpruceButtonWidget(Position.of(this, 0, 4),
        BUTTON_WIDTH, BUTTON_HEIGHT, new LiteralText(this.checklistID), button -> {});
    }

    @Override
    public int getHeight() {
      return BUTTON_HEIGHT + BUTTON_MARGIN;
    }

    @Override
    protected void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.widget.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
      return this.widget.isMouseOver(mouseX, mouseY);
    }

    public boolean select() {
      this.widget.playDownSound();
      return this.onSelected.onClick(this.checklistID);
    }
  }

  @FunctionalInterface
  public interface ChecklistSelectionEntryClickedAction {
    boolean onClick(String selectedChecklistID);
  }
}
