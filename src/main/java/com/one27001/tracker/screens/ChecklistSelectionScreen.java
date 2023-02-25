package com.one27001.tracker.screens;

import java.util.List;

import org.apache.logging.log4j.Logger;

import com.one27001.tracker.service.ChecklistSelectionService;
import com.one27001.tracker.widgets.selection.ScrollableButtonsListWidget;
import com.one27001.util.ClassRegistry;
import com.one27001.util.MyLogger;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class ChecklistSelectionScreen extends Screen {
  private static final Logger log = MyLogger.get();

  // private ChecklistService checklistService;
  private ChecklistSelectionService checklistSelectionService;
  private List<String> checklists;


  public ChecklistSelectionScreen() {
    super(new TranslatableText("one27001.tracker.screen.checklist-selection.title"));

    // checklistService = ClassRegistry.supply(ChecklistService.class);
    checklistSelectionService = ClassRegistry.supply(ChecklistSelectionService.class);
    checklists = checklistSelectionService.listChecklists();
  }

  @Override
  protected void init() {
    super.init();

    ButtonWidget backButton = new ButtonWidget(width / 2 - 100, height - 30, 200, 20, new LiteralText("Back"), (button) -> {
      // Close screen and return to game
      MinecraftClient.getInstance().setScreen(null);
    });
    addDrawableChild(backButton);
  }

  private static final int BUTTON_WIDTH = 200;
  private static final int BUTTON_HEIGHT = 20;
  private static final int BUTTON_MARGIN = 8;

  @Override
  public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    renderBackground(matrices);
    super.render(matrices, mouseX, mouseY, delta);

    // addDrawableChild(new ScrollableButtonsListWidget(
    //     BUTTON_WIDTH, height - 2 * BUTTON_MARGIN, BUTTON_MARGIN, BUTTON_MARGIN,
    //     // width, height, 0, height,
    //     checklists, (checklistID) -> {
    //       log.info("Selected checklist {}, navigating to checklist details screen!", checklistID);
    //       // TODO: set selected checklist
    //       // TODO: navigate to checklist details screen
    //     }));

    if (checklists.isEmpty()) {
      // TODO: draw "no checklists, create one!"
    } else {
      for (int i = 0; i < checklists.size(); ++i) {
        final String checklistID = checklists.get(i);
        addDrawableChild(new ButtonWidget(Math.round((width - 200) * 0.5f),
          BUTTON_MARGIN + (BUTTON_HEIGHT + BUTTON_MARGIN) * i, BUTTON_WIDTH, BUTTON_HEIGHT,
          new LiteralText(checklistID), button -> {
            log.info("Selected checklist {}, navigating to checklist details screen!", checklistID);
            // TODO: continue here
          }));
      }
    }
  }
}
