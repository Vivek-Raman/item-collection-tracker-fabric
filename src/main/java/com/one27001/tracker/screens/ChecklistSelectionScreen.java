package com.one27001.tracker.screens;

import java.util.List;

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

    log.info("Checklists: {}, width {}, height, {}", this.checklists, this.width, this.height);
    this.widgetList = new ChecklistSelectionListWidget(checklists, checklistID -> {
      log.info("Selected checklist {}, navigating to checklist details screen!", checklistID);
      this.checklistSelectionService.setActiveChecklistID(checklistID);
      MinecraftClient.getInstance().setScreen(new ChecklistDetailScreen(this));
    }, Position.of(this.width / 2 - 150, 40), 300, this.height - 45);

    this.addDrawableChild(this.widgetList);
    this.addDrawableChild(this.backButton);
  }
}
