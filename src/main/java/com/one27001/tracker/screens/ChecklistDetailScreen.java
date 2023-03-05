package com.one27001.tracker.screens;

import java.util.Objects;

import org.apache.logging.log4j.Logger;

import com.one27001.tracker.model.Checklist;
import com.one27001.tracker.service.ChecklistService;
import com.one27001.util.ClassRegistry;
import com.one27001.util.MyLogger;
import com.one27001.util.screen.AbstractScreen;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.widget.SpruceLabelWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class ChecklistDetailScreen extends AbstractScreen {
  private static final Logger log = MyLogger.get();
  private ChecklistService checklistService;

  // State
  private Checklist checklist = null;

  public ChecklistDetailScreen(Screen parent) {
    super(parent, new TranslatableText("one27001.tracker.screen.checklist-detail.title"));

    checklistService = ClassRegistry.supply(ChecklistService.class);
    this.checklist = checklistService.getActiveChecklist();
    if (Objects.isNull(this.checklist)) {
      log.error("Cannot find active checklist! Navigating to Checklist Selection!");
      this.navigateToParentScreen();
    }
  }



  @Override
  protected void init() {
    super.init();

    addDrawableChild(new SpruceLabelWidget(Position.center(100, 0),
      new LiteralText(checklist.getChecklistID()), 250));
    addDrawableChild(this.backButton);
  }
}
