package com.one27001.tracker.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ChecklistScreen extends Screen {
  private TextFieldWidget searchBox;
  private ButtonWidget backButton;

  public ChecklistScreen() {
    super(new LiteralText("Item Checklist"));
  }

  @Override
  protected void init() {
    super.init();

    // Create search box
    searchBox = new TextFieldWidget(textRenderer, width / 2 - 100, 20, 200, 20, new LiteralText("Search"));
    searchBox.setChangedListener((searchText) -> {
      // Filter items by search text
      // ...
    });
    addDrawableChild(searchBox);

    // Create back button
    backButton = new ButtonWidget(width / 2 - 100, height - 30, 200, 20, new LiteralText("Back"), (button) -> {
      // Close screen and return to game
      MinecraftClient.getInstance().setScreen(null);
    });
    addDrawableChild(backButton);
  }

  @Override
  public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    renderBackground(matrices);

    // Draw search box and back button
    super.render(matrices, mouseX, mouseY, delta);

    // Draw list of items
    int x = 10;
    int y = 50;
    for (Identifier id : Registry.ITEM.getIds()) {
      Item item = Registry.ITEM.get(id);
      if (item == Items.AIR) {
        continue;
      }
      Text itemName = item.getName();
      int stringWidth = textRenderer.getWidth(itemName);
      if (x + stringWidth > width - 10) {
        x = 10;
        y += 20;
      }
      drawTextWithShadow(matrices, textRenderer, itemName, x, y, 0xFFFFFF);
      x += stringWidth + 5;
    }
  }

}
