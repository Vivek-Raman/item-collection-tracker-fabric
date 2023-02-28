package com.one27001.util.screen;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public abstract class AbstractScreen extends SpruceScreen {
  protected Screen parent = null;

  protected SpruceButtonWidget backButton = null;

  public AbstractScreen(Screen parent, Text title) {
    super(title);
    this.parent = parent;
  }



  @Override
  public void renderTitle(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    drawCenteredText(matrices, textRenderer, this.title, this.width / 2, 8, 0xFFFFFF);

    this.backButton = new SpruceButtonWidget(Position.of(this.width / 2, this.height - 60),
      100, 20, new TranslatableText("one27001.tracker.screen.common.back"), (button) -> {
        navigateToParentScreen();
      });
  }

  protected void navigateToParentScreen() {
    MinecraftClient.getInstance().setScreen(this.parent);
  }
}
