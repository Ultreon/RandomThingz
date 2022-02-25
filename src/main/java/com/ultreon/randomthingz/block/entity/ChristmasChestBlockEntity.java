package com.ultreon.randomthingz.block.entity;

import com.ultreon.randomthingz.block._common.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT, _interface = LidBlockEntity.class
)
public class ChristmasChestBlockEntity extends ChestBlockEntity {
    public ChristmasChestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHRISTMAS_CHEST.get(), pos, state);
    }

    public ChristmasChestBlockEntity() {
        this(BlockPos.ZERO, ModBlocks.CHRISTMAS_CHEST.asBlockState());
    }
}
