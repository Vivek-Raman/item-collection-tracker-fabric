package com.one27001.tracker.blocks;

import org.apache.logging.log4j.Logger;

import com.one27001.tracker.screens.ChecklistSelectionScreen;
import com.one27001.util.MyLogger;

import net.minecraft.block.BlockState;
import net.minecraft.block.LecternBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChecklistBlock extends LecternBlock {
  private static final Logger log = MyLogger.get();

  public ChecklistBlock(Settings settings) {
    super(settings);
  }

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
      BlockHitResult hit) {
    log.debug("onUse called on Checklist Lectern");
    if (!world.isClient()) {
      return super.onUse(state, world, pos, player, hand, hit);
    }

    // load UI
    MinecraftClient.getInstance().setScreen(new ChecklistSelectionScreen());
    return super.onUse(state, world, pos, player, hand, hit);
  }
}
