package com.ultreon.randomthingz.block;

import com.ultreon.randomthingz.tileentity.ChristmasChestTileEntity;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.BlockGetter;

import java.util.function.Supplier;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

/**
 * Christmas chest block.
 *
 * @author Qboi123
 */
public class ChristmasChestBlock extends ChestBlock {
    public ChristmasChestBlock(Properties builder, Supplier<BlockEntityType<? extends ChestBlockEntity>> tileEntityTypeIn) {
        super(builder, tileEntityTypeIn);
    }

    @Override
    public ChristmasChestTileEntity newBlockEntity(BlockGetter worldIn) {
        return new ChristmasChestTileEntity();
    }
}
