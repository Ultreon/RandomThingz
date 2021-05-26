package com.qtech.randomthingz.commons;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;

import javax.annotation.Nonnull;

/**
 * Fake use context for block-item uses class.
 *
 * @author Qboi123
 */
public class FakeUseContext extends BlockItemUseContext {
    private final BlockRayTraceResult rayTraceResult;

    public FakeUseContext(PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        super(new ItemUseContext(player, handIn, hit));
        rayTraceResult = hit;
    }

    @Nonnull
    @Override
    public BlockPos getPos() {
        return rayTraceResult.getPos();
    }
}
