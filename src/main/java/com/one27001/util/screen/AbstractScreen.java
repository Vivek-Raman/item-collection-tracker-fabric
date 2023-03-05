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
  private static final int BUTTON_WIDTH = 200;
  private static final int BUTTON_HEIGHT = 20;
  private static final int BUTTON_MARGIN = 4;

  protected Screen parent = null;

  protected SpruceButtonWidget backButton = null;

  public AbstractScreen(Screen parent, Text title) {
    super(title);
    this.parent = parent;
  }

  @Override
  protected void init() {
    super.init();

    this.backButton = new SpruceButtonWidget(Position.of(this.width / 2 - BUTTON_WIDTH / 2, this.height - 20),
    BUTTON_WIDTH, BUTTON_HEIGHT, new TranslatableText("one27001.tracker.screen.common.back"), (button) -> {
      this.navigateToParentScreen();
    });
  }

  @Override
  public void renderTitle(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    this.drawCenteredText(matrices, textRenderer, this.title, this.width / 2, 8, 0xFFFFFF);
  }

  protected void navigateToParentScreen() {
    MinecraftClient.getInstance().setScreen(this.parent);
  }
}
