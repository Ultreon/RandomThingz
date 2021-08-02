package com.ultreon.texturedmodels.item;

import com.ultreon.texturedmodels.tileentity.SignFrameTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SignItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FrameSignItem extends SignItem {
    public FrameSignItem(Properties propertiesIn, Block floorBlockIn, Block wallBlockIn) {
        super(propertiesIn, floorBlockIn, wallBlockIn);
    }

    protected boolean onBlockPlaced(BlockPos pos, World dimensionIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        boolean flag = super.onBlockPlaced(pos, dimensionIn, player, stack, state);
        if (!dimensionIn.isClientSided && !flag && player != null) {
            System.out.println("platziert");
            player.openSignEditor((SignFrameTile) dimensionIn.getTileEntity(pos));
        }

        return flag;
    }
}
//========SOLI DEO GLORIA========//