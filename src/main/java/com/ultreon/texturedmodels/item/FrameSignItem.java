package com.ultreon.texturedmodels.item;

import com.ultreon.texturedmodels.tileentity.SignFrameBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FrameSignItem extends SignItem {
    public FrameSignItem(Properties propertiesIn, Block floorBlockIn, Block wallBlockIn) {
        super(propertiesIn, floorBlockIn, wallBlockIn);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(@NotNull BlockPos pos, @NotNull Level dimensionIn, @Nullable Player player, @NotNull ItemStack stack, @NotNull BlockState state) {
        boolean flag = super.updateCustomBlockEntityTag(pos, dimensionIn, player, stack, state);
        if (!dimensionIn.isClientSide && !flag && player != null) {
            System.out.println("platziert");
            player.openTextEdit((SignFrameBlockEntity) dimensionIn.getBlockEntity(pos));
        }

        return flag;
    }
}
