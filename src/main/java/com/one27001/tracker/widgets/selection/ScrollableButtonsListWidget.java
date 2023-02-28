package com.one27001.tracker.widgets.selection;

import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

// TODO: this doesn't work
@java.lang.Deprecated
@SuppressWarnings("unused")
public class ScrollableButtonsListWidget extends EntryListWidget<ScrollableButtonsListWidget.Entry> {
  private final List<String> buttonTexts;
  private final ButtonClickListener listener;

  public ScrollableButtonsListWidget(int width, int height, int top, int bottom, List<String> buttonTexts, ButtonClickListener listener) {
    super(MinecraftClient.getInstance(), width, height, top, bottom, 20);

    this.buttonTexts = buttonTexts;
    this.listener = listener;
    for (String text : buttonTexts) {
      addEntry(new Entry(text, listener));
    }
  }

  public static class Entry extends EntryListWidget.Entry<ScrollableButtonsListWidget.Entry> {
    private final String buttonText;
    private final ButtonWidget button;

    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;

    public Entry(String buttonText, ButtonClickListener listener) {
      this.buttonText = buttonText;
      this.button = new ButtonWidget(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT, new LiteralText(buttonText),
          button -> listener.onClick(buttonText));
    }

    @Override
    public void render(MatrixStack matrices, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float delta) {
      this.button.x = Math.round((width - BUTTON_WIDTH) * 0.5f);
      this.button.y = y;
      this.button.render(matrices, mouseX, mouseY, delta);
    }


  }

  @FunctionalInterface
  public interface ButtonClickListener {
    void onClick(String buttonText);
  }

  @Override
  public void appendNarrations(NarrationMessageBuilder var1) {
    // FIXME: why
  }

  @Override
  protected int getScrollbarPositionX() {
      return super.getScrollbarPositionX() + 32;
  }

  @Override
  public int getRowWidth() {
      return 200;
  }

}
