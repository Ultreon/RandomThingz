package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.api.IWrenchable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.Property;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class WrenchItem extends Item {
    public WrenchItem() {
        super(new Properties().group(ItemGroup.TOOLS).maxStackSize(1));
    }

    private static <T extends Comparable<T>> BlockState cycleProperty(BlockState state, Property<T> propertyIn) {
        return state.with(propertyIn, getAdjacentValue(propertyIn.getAllowedValues(), state.get(propertyIn)));
    }

    private static <T> T getAdjacentValue(Iterable<T> p_195959_0_, @Nullable T p_195959_1_) {
        return Util.getElementAfter(p_195959_0_, p_195959_1_);
    }

    @Override
    public ActionResultType onUseItem(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null) return ActionResultType.PASS;

        World dimension = context.getDimension();
        BlockPos pos = context.getPos();
        BlockState state = dimension.getBlockState(pos);

        if (state.getBlock() instanceof IWrenchable) {
            ActionResultType result = ((IWrenchable) state.getBlock()).onWrench(context);
            if (result != ActionResultType.PASS) {
                return result;
            }
        }

        if (player.isCrouching() && state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            BlockState state1 = cycleProperty(state, BlockStateProperties.HORIZONTAL_FACING);
            dimension.setBlockState(pos, state1, 18);
            player.addStat(Stats.ITEM_USED.get(this));
            return ActionResultType.SUCCESS;
        }

        return super.onUseItem(context);
    }
}
