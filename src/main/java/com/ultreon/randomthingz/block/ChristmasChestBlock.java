package com.ultreon.randomthingz.block;

import com.ultreon.randomthingz.common.RequiresToolMat;
import com.ultreon.randomthingz.common.RequiresToolType;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.item.tool.ToolType;
import com.ultreon.randomthingz.block.entity.ChristmasChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * Christmas chest block.
 *
 * @author Qboi123
 */
public class ChristmasChestBlock extends ChestBlock implements RequiresToolMat, RequiresToolType {
    public ChristmasChestBlock(Properties builder, Supplier<BlockEntityType<? extends ChestBlockEntity>> tileEntityTypeIn) {
        super(builder, tileEntityTypeIn);
    }

    @Override
    public ChristmasChestBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ChristmasChestBlockEntity(pos, state);
    }

    @Override
    public @Nullable ToolRequirement getRequirement() {
        return null;
    }

    @Override
    public @Nullable ToolType getToolType() {
        return ToolType.AXE;
    }
}
