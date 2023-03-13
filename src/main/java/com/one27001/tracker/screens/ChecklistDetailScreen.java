package com.one27001.tracker.screens;

import java.util.Objects;

import org.apache.logging.log4j.Logger;

import com.one27001.tracker.model.Checklist;
import com.one27001.tracker.service.ChecklistService;
import com.one27001.tracker.widgets.details.ChecklistDetailHeader;
import com.one27001.tracker.widgets.details.ChecklistDetailsWidget;
import com.one27001.util.ClassRegistry;
import com.one27001.util.MyLogger;
import com.one27001.util.screen.AbstractScreen;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.widget.SpruceWidget;
import net.minecraft.text.TranslatableText;

public class ChecklistDetailScreen extends AbstractScreen {
  private static final float CONTENT_RATIO = 10f / 16f;
  private static final Logger log = MyLogger.get();
  private ChecklistService checklistService;

  private SpruceWidget header;
  private SpruceWidget table;

  // State
  private Checklist checklist = null;

  public ChecklistDetailScreen(AbstractScreen parent) {
    super(parent, new TranslatableText("one27001.tracker.screen.checklist-detail.title"));

    this.checklistService = ClassRegistry.supply(ChecklistService.class);
    this.reinitialize();
  }

  @Override
  protected void reinitialize() {
    super.reinitialize();
    this.checklist = checklistService.getActiveChecklist();
  }

  @Override
  protected void init() {
    super.init();
    this.addDrawableChild(this.backButton);

    int contentWidth = Math.round(this.width * CONTENT_RATIO);
    // int maxY = this.height - MAX_Y_OFFSET;

    if (Objects.isNull(this.checklist)) {
      log.error("Cannot find active checklist! Navigating to Checklist Selection!");
      // this.navigateToParentScreen(); // TODO: why does this crash the game?
      return;
    }

    // draw table header
    this.header = new ChecklistDetailHeader(Position.of((this.width - contentWidth) / 2, STARTING_Y),
      contentWidth, BUTTON_HEIGHT);
    this.addDrawableChild(this.header);

    // draw table
    this.table = new ChecklistDetailsWidget(checklist, Position.of(header, 0, BUTTON_HEIGHT),
      contentWidth, this.height - MAX_Y_OFFSET - STARTING_Y - BUTTON_HEIGHT - VERTICAL_MARGIN);
    this.addDrawableChild(this.table); // TODO: Fix this overflow

    // addDrawableChild(new SpruceLabelWidget(Position.center(100, 0),
    //   new LiteralText(checklist.getChecklistID()), 250));
  }
}
