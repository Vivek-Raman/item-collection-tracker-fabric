package com.one27001.util.screen;

import java.util.Objects;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public abstract class AbstractScreen extends SpruceScreen {
  protected static final int BUTTON_WIDTH = 200;
  protected static final int BUTTON_HEIGHT = 20;
  protected static final int VERTICAL_MARGIN = 16;
  protected static final int STARTING_Y = 3 * VERTICAL_MARGIN;
  protected static final int MAX_Y_OFFSET = 2 * VERTICAL_MARGIN + BUTTON_HEIGHT;

  protected AbstractScreen parent = null;
  protected SpruceButtonWidget backButton = null;

  public AbstractScreen(AbstractScreen parent, Text title) {
    super(title);
    this.parent = parent;
  }

  @Override
  protected void init() {
    super.init();

    this.backButton = new SpruceButtonWidget(
      Position.of((this.width - BUTTON_WIDTH) / 2, this.height - MAX_Y_OFFSET + VERTICAL_MARGIN),
      BUTTON_WIDTH, BUTTON_HEIGHT, new TranslatableText("one27001.tracker.screen.common.back"),
      (button) -> this.navigateToParentScreen());
  }

  protected void reinitialize() {
  }

  @Override
  public void renderTitle(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    drawCenteredText(matrices, textRenderer, this.title, this.width / 2, VERTICAL_MARGIN, 0xFFFFFF);
  }

  protected void navigateToParentScreen() {
    MinecraftClient.getInstance().setScreen(this.parent);
    if (Objects.nonNull(this.parent))
      this.parent.reinitialize();
  }

  protected void drawToast(MinecraftClient client, Text toastTitle, Text toastDescription) {
    client.getToastManager().add(SystemToast.create(
       client, SystemToast.Type.TUTORIAL_HINT, toastTitle, toastDescription));
  }
}
