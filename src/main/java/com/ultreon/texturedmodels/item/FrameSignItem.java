package com.ultreon.texturedmodels.item;

import com.ultreon.texturedmodels.tileentity.SignFrameTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class FrameSignItem extends SignItem {
    public FrameSignItem(Properties propertiesIn, Block floorBlockIn, Block wallBlockIn) {
        super(propertiesIn, floorBlockIn, wallBlockIn);
    }

    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level dimensionIn, @Nullable Player player, ItemStack stack, BlockState state) {
        boolean flag = super.updateCustomBlockEntityTag(pos, dimensionIn, player, stack, state);
        if (!dimensionIn.isClientSide && !flag && player != null) {
            System.out.println("platziert");
            player.openTextEdit((SignFrameTile) dimensionIn.getBlockEntity(pos));
        }

        return flag;
    }
}
//========SOLI DEO GLORIA========//