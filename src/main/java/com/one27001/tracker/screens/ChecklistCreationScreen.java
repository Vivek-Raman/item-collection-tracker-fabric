package com.one27001.tracker.screens;

import com.one27001.tracker.service.ChecklistService;
import com.one27001.util.ClassRegistry;
import com.one27001.util.screen.AbstractScreen;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.text.SpruceTextFieldWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class ChecklistCreationScreen extends AbstractScreen {
  private ChecklistService checklistService;

  private SpruceTextFieldWidget textInputWidget;

  public ChecklistCreationScreen(Screen parent) {
    super(parent, new TranslatableText("one27001.tracker.screen.checklist-creation.title"));
    this.checklistService = ClassRegistry.supply(ChecklistService.class);
  }

  @Override
  protected void init() {
    super.init();
    this.addDrawableChild(this.backButton);

    // TODO: resume here
    this.textInputWidget = new SpruceTextFieldWidget(Position.of((this.width - BUTTON_WIDTH) / 2, 3 * BUTTON_HEIGHT + VERTICAL_MARGIN),
      BUTTON_WIDTH, BUTTON_HEIGHT, new LiteralText("what am I"));
    this.addDrawableChild(this.textInputWidget);
    this.addDrawableChild(new SpruceButtonWidget(Position.of((this.width - BUTTON_WIDTH) / 2, 4 * BUTTON_HEIGHT + VERTICAL_MARGIN),
      this.width, this.height, new TranslatableText("one27001.tracker.screen.checklist-creation.submit"), button -> this.createChecklist()));
  }

  private void createChecklist() {
    String checklistID = this.textInputWidget.getText();
    this.checklistService.generateChecklist(checklistID);
  }
}
