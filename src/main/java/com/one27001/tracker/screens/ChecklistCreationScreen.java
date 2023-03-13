package com.one27001.tracker.screens;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import com.one27001.tracker.service.ChecklistService;
import com.one27001.util.ClassRegistry;
import com.one27001.util.MyLogger;
import com.one27001.util.screen.AbstractScreen;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.text.SpruceTextFieldWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class ChecklistCreationScreen extends AbstractScreen {
  private static Logger log = MyLogger.get();

  private ChecklistService checklistService;

  private SpruceTextFieldWidget textInputWidget;
  private SpruceButtonWidget submitButtonWidget;

  public ChecklistCreationScreen(AbstractScreen parent) {
    super(parent, new TranslatableText("one27001.tracker.screen.checklist-creation.title"));
    this.checklistService = ClassRegistry.supply(ChecklistService.class);
  }

  @Override
  protected void init() {
    super.init();
    this.addDrawableChild(this.backButton);

    this.textInputWidget = new SpruceTextFieldWidget(
      Position.of((this.width - BUTTON_WIDTH) / 2, 3 * BUTTON_HEIGHT + VERTICAL_MARGIN),
      BUTTON_WIDTH, BUTTON_HEIGHT, new LiteralText("checklistID"));
    this.addDrawableChild(this.textInputWidget);

    this.submitButtonWidget = new SpruceButtonWidget(
      Position.of((this.width - BUTTON_WIDTH) / 2, this.height - BUTTON_HEIGHT - 3 * VERTICAL_MARGIN),
      BUTTON_WIDTH, BUTTON_HEIGHT, new TranslatableText("one27001.tracker.screen.checklist-creation.submit"),
      button -> this.tryCreateChecklist());
    this.addDrawableChild(submitButtonWidget);
  }

  private void tryCreateChecklist() {
    String checklistID = this.textInputWidget.getText().trim();
    if (StringUtils.isBlank(checklistID)) {
      log.warn("Failed to create checklist: checklistID \"{}\" is blank.", checklistID);
      this.drawToast(MinecraftClient.getInstance(),
        new TranslatableText("one27001.tracker.screen.checklist-creation.validation.failure.toast.title"),
        new TranslatableText("one27001.tracker.screen.checklist-creation.validation.failure.checklist-id-blank.toast.description"));
    }
    if (this.checklistService.getChecklist(checklistID) != null) {
      log.error("Failed to create checklist: checklist with ID \"{}\" already exists.", checklistID);
      this.drawToast(MinecraftClient.getInstance(),
        new TranslatableText("one27001.tracker.screen.checklist-creation.validation.failure.toast.title"),
        new TranslatableText("one27001.tracker.screen.checklist-creation.validation.failure.checklist-id-exists.toast.description"));
    }

    boolean success = this.checklistService.generateChecklist(checklistID) != null;
    if (success) {
      this.navigateToParentScreen();
    }
  }
}
