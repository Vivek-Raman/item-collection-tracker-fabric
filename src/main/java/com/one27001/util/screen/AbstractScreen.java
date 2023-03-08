package com.one27001.util.screen;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public abstract class AbstractScreen extends SpruceScreen {
  protected static final int BUTTON_WIDTH = 200;
  protected static final int BUTTON_HEIGHT = 20;
  protected static final int VERTICAL_MARGIN = 16;

  protected Screen parent = null;

  protected SpruceButtonWidget backButton = null;

  public AbstractScreen(Screen parent, Text title) {
    super(title);
    this.parent = parent;
  }

  @Override
  protected void init() {
    super.init();

    this.backButton = new SpruceButtonWidget(
      Position.of((this.width - BUTTON_WIDTH) / 2, this.height - VERTICAL_MARGIN - BUTTON_HEIGHT),
      BUTTON_WIDTH, BUTTON_HEIGHT, new TranslatableText("one27001.tracker.screen.common.back"),
      (button) -> this.navigateToParentScreen());
  }

  @Override
  public void renderTitle(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    drawCenteredText(matrices, textRenderer, this.title, this.width / 2, VERTICAL_MARGIN, 0xFFFFFF);
  }

  protected void navigateToParentScreen() {
    MinecraftClient.getInstance().setScreen(this.parent);
  }

  protected void drawToast(MinecraftClient client, Text toastTitle, Text toastDescription) {
    client.getToastManager().add(SystemToast.create(
       client, SystemToast.Type.TUTORIAL_HINT, toastTitle, toastDescription));
  }
}
