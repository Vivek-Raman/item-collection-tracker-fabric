package com.one27001.tracker.screens;

import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.Logger;

import com.one27001.tracker.service.ChecklistSelectionService;
import com.one27001.tracker.widgets.selection.ChecklistSelectionListWidget;
import com.one27001.util.ClassRegistry;
import com.one27001.util.MyLogger;
import com.one27001.util.screen.AbstractScreen;

import dev.lambdaurora.spruceui.Position;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

public class ChecklistSelectionScreen extends AbstractScreen {
  private static final Logger log = MyLogger.get();
  private static final int BUTTON_WIDTH = 200;
  private static final int BUTTON_HEIGHT = 20;
  private static final int BUTTON_MARGIN = 4;

  //     private ChecklistService checklistService;
  private ChecklistSelectionService checklistSelectionService;

  // State
  private List<String> checklists;
  private ChecklistSelectionListWidget widgetList;

  public ChecklistSelectionScreen() {
    super(null, new TranslatableText("one27001.tracker.screen.checklist-selection.title"));

    // checklistService = ClassRegistry.supply(ChecklistService.class);
    this.checklistSelectionService = ClassRegistry.supply(ChecklistSelectionService.class);
    this.checklists = checklistSelectionService.listChecklists();
  }

  @Override
  protected void init() {
    super.init();

    if (Objects.nonNull(this.checklists) && !this.checklists.isEmpty()) {
      this.widgetList = new ChecklistSelectionListWidget(this.checklists, checklistID -> {
          log.info("Selected checklist {}, navigating to checklist details screen!", checklistID);
          this.checklistSelectionService.setActiveChecklistID(checklistID);
          MinecraftClient.getInstance().setScreen(new ChecklistDetailScreen(this));
          return true;
        }, Position.of((this.width - BUTTON_WIDTH) / 2, 40), BUTTON_WIDTH,
        Math.min((BUTTON_HEIGHT + BUTTON_MARGIN) * (this.checklists.size() + 1), this.height - 60));
      this.addDrawableChild(this.widgetList);
    } else {
      // TODO: navigate to "create new checklist" screen
    }

    this.addDrawableChild(this.backButton);
  }
}
