package com.one27001.tracker.widgets.details;

import com.one27001.tracker.model.Checklist;
import com.one27001.tracker.model.TrackingInfo;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.background.EmptyBackground;
import dev.lambdaurora.spruceui.border.EmptyBorder;
import dev.lambdaurora.spruceui.widget.SpruceIconButtonWidget;
import dev.lambdaurora.spruceui.widget.SpruceLabelWidget;
import dev.lambdaurora.spruceui.widget.SpruceWidget;
import dev.lambdaurora.spruceui.widget.container.SpruceEntryListWidget;
import dev.lambdaurora.spruceui.widget.text.SpruceTextFieldWidget;
import lombok.Getter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class ChecklistDetailsWidget extends SpruceEntryListWidget<ChecklistDetailsWidget.Entry> {
  private static final int BUTTON_HEIGHT = 20;
  private static final int BUTTON_MARGIN = 4;

  private final Checklist checklist;
  private final ChecklistDetailInteractionHandler actionHandler;

  public ChecklistDetailsWidget(Checklist checklist, ChecklistDetailInteractionHandler actionHandler,
      Position position, int width, int height) {
    super(position, width, height, BUTTON_MARGIN, ChecklistDetailsWidget.Entry.class);
    this.setBackground(EmptyBackground.EMPTY_BACKGROUND);
    this.setBorder(EmptyBorder.EMPTY_BORDER);
    this.setRenderTransition(false);

    this.checklist = checklist;
    this.actionHandler = actionHandler;
    this.checklist.getStats().forEach((String itemID, TrackingInfo info) -> {
      this.addEntry(new Entry(this, actionHandler, itemID, info, width));
    });
  }


  public static class Entry extends SpruceEntryListWidget.Entry {
    @Getter private final TrackingInfo info;
    @Getter private final ChecklistDetailInteractionHandler actionHandler;

    private SpruceWidget name;
    private SpruceWidget targetQuantity;
    private SpruceWidget currentQuantity;
    private SpruceWidget star;

    public Entry(SpruceWidget parent, ChecklistDetailInteractionHandler actionHandler,
        String itemID, TrackingInfo info, int contentWidth) {
      super();
      this.actionHandler = actionHandler;
      this.info = info;

      TableConstants.Size sizeOf = new TableConstants.Size(contentWidth);
      TableConstants.Position positionOf = new TableConstants.Position(sizeOf);

      this.name = new SpruceLabelWidget(Position.of(position, positionOf.name(), 0),
        new LiteralText(this.getInfo().getItemInfo().getItemID()), sizeOf.name());

      if (this.actionHandler.isLocked()) {
        this.targetQuantity = new SpruceLabelWidget(Position.of(position, positionOf.targetQuantity(), 0),
          new LiteralText(this.getInfo().getTargetQuantity().toString()), sizeOf.targetQuantity());
      } else {
        // TODO: test this
        this.targetQuantity = new SpruceTextFieldWidget(Position.of(position, positionOf.targetQuantity(), 0),
          sizeOf.targetQuantity(), BUTTON_HEIGHT, new LiteralText(this.getInfo().getTargetQuantity().toString()));
      }

      // TODO: convert to text input
      SpruceTextFieldWidget currentQuantity = new SpruceTextFieldWidget(Position.of(position, positionOf.currentQuantity(), 0),
        sizeOf.currentQuantity(), BUTTON_HEIGHT, new LiteralText(this.getInfo().getCollectedQuantity().toString()));
      currentQuantity.setTextPredicate(SpruceTextFieldWidget.INTEGER_INPUT_PREDICATE);
      currentQuantity.setText(info.getCollectedQuantity().toString());
      this.currentQuantity = currentQuantity;

      SpruceIconButtonWidget star = new SpruceIconButtonWidget(Position.of(position, positionOf.star(), 0),
        sizeOf.star(), BUTTON_HEIGHT, new LiteralText(Boolean.toString(this.getInfo().isStar())),
        (button) -> {
          this.getActionHandler().onStarButtonPressed(itemID);
        });
      this.star = star;
    }

    private void renderIcon(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      // MinecraftClient.getInstance().getItemRenderer().renderGuiItemIcon(
      //   new ItemStack(Item.byRawId(this.getInfo().getItemInfo().getRawID())),
      //   TableConstants.Size.icon(), TableConstants.Size.icon());
    }

    @Override
    protected void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderIcon(matrices, mouseX, mouseY, delta);
      this.name.render(matrices, mouseX, mouseY, delta);
      this.targetQuantity.render(matrices, mouseX, mouseY, delta);
      this.currentQuantity.render(matrices, mouseX, mouseY, delta);
      this.star.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public int getHeight() {
      return BUTTON_HEIGHT + BUTTON_MARGIN;
    }
  }
}
