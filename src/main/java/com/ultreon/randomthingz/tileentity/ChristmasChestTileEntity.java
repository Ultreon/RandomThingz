package com.ultreon.randomthingz.tileentity;

import com.ultreon.randomthingz.block.entity.ModTileEntities;
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
        super(ModTileEntities.CHRISTMAS_CHEST.get(), pos, state);
    }
}
