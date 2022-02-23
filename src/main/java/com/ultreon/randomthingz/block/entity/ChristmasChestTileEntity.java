package com.ultreon.randomthingz.block.entity;

import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT, _interface = LidBlockEntity.class
)
public class ChristmasChestTileEntity extends ChestBlockEntity {
    public ChristmasChestTileEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHRISTMAS_CHEST.get(), pos, state);
    }

    public ChristmasChestTileEntity() {
        this(BlockPos.ZERO, ModBlocks.CHRISTMAS_CHEST.asBlockState());
    }
}
