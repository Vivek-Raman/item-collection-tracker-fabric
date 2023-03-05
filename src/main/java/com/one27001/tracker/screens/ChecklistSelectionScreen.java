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
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.SpruceLabelWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

public class ChecklistSelectionScreen extends AbstractScreen {
  private static final Logger log = MyLogger.get();
  private static final int BUTTON_MARGIN = 4;

  //     private ChecklistService checklistService;
  private ChecklistSelectionService checklistSelectionService;

  // State
  private List<String> checklists;

  public ChecklistSelectionScreen() {
    super(null, new TranslatableText("one27001.tracker.screen.checklist-selection.title"));

    // this.checklistService = ClassRegistry.supply(ChecklistService.class);
    this.checklistSelectionService = ClassRegistry.supply(ChecklistSelectionService.class);
    this.checklists = checklistSelectionService.listChecklists();
  }

  @Override
  protected void init() {
    super.init();

    if (Objects.nonNull(this.checklists) && !this.checklists.isEmpty()) {
      this.addDrawableChild(new ChecklistSelectionListWidget(this.checklists, checklistID -> {
          log.info("Selected checklist {}, navigating to checklist details screen!", checklistID);
          this.checklistSelectionService.setActiveChecklistID(checklistID);
          MinecraftClient.getInstance().setScreen(new ChecklistDetailScreen(this));
          return true;
        }, Position.of((this.width - BUTTON_WIDTH) / 2, 40), BUTTON_WIDTH,
        Math.min((BUTTON_HEIGHT + BUTTON_MARGIN) * (this.checklists.size() + 1),
        this.height - Math.max(BUTTON_HEIGHT + BUTTON_MARGIN, 60)))); // TODO: Fix this
    } else {
      this.addDrawableChild(new SpruceLabelWidget(Position.of((this.width - BUTTON_WIDTH) / 2, 40),
        new TranslatableText("one27001.tracker.screen.checklist-selection.no-lists-found"), BUTTON_WIDTH));
    }

    this.addDrawableChild(new SpruceButtonWidget(
        Position.of((this.width - BUTTON_WIDTH) / 2, this.height - (VERTICAL_MARGIN + BUTTON_HEIGHT) * 2),
        BUTTON_WIDTH, BUTTON_HEIGHT, new TranslatableText("one27001.tracker.screen.checklist-selection.create-checklist"),
        button -> MinecraftClient.getInstance().setScreen(new ChecklistCreationScreen(this))));
    this.addDrawableChild(this.backButton);
  }
}
