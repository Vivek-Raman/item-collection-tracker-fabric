package com.one27001.tracker.widgets.selection;

import java.util.List;

import org.apache.logging.log4j.Logger;

import com.one27001.util.MyLogger;

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
  private static Logger log = MyLogger.get();
  private static final int BUTTON_WIDTH = 200;
  private static final int BUTTON_HEIGHT = 20;
  private static final int BUTTON_MARGIN = 4;

  public ChecklistSelectionListWidget(List<String> checklistIDs, ChecklistSelectionEntryClickedAction onSelected,
      Position position, int width, int height) {
    super(position, width, height, BUTTON_MARGIN, ChecklistSelectionListWidget.Entry.class);

    for (String checklistID : checklistIDs) {
      this.addEntry(new Entry(this, checklistID, onSelected));
    }
  }

  public static class Entry extends SpruceEntryListWidget.Entry {
    @Getter private final String checklistID;
    private final AbstractSpruceWidget widget;

    public Entry(SpruceWidget parent, String checklistID, ChecklistSelectionEntryClickedAction onSelected) {
      this.checklistID = checklistID;
      this.widget = new SpruceButtonWidget(Position.of(this, 0, 4),
        BUTTON_WIDTH, BUTTON_HEIGHT, new LiteralText(this.checklistID),
        (button) -> {
          log.info("Clicked button {}", this.checklistID); // TODO: This isn't working
          onSelected.onClick(this.checklistID);
        });
    }

    @Override
    public int getHeight() {
      return BUTTON_HEIGHT + BUTTON_MARGIN;
    }

    @Override
    protected void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.widget.render(matrices, mouseX, mouseY, delta);
    }
  }

  @FunctionalInterface
  public interface ChecklistSelectionEntryClickedAction {
    void onClick(String selectedChecklistID);
  }
}
