package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.api.IWrenchable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nullable;

public class WrenchItem extends Item {
    public WrenchItem() {
        super(new Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1));
    }

    private static <T extends Comparable<T>> BlockState cycleProperty(BlockState state, Property<T> propertyIn) {
        return state.setValue(propertyIn, getAdjacentValue(propertyIn.getPossibleValues(), state.getValue(propertyIn)));
    }

    private static <T> T getAdjacentValue(Iterable<T> p_195959_0_, @Nullable T p_195959_1_) {
        return Util.findNextInIterable(p_195959_0_, p_195959_1_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;

        Level dimension = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = dimension.getBlockState(pos);

        if (state.getBlock() instanceof IWrenchable) {
            InteractionResult result = ((IWrenchable) state.getBlock()).onWrench(context);
            if (result != InteractionResult.PASS) {
                return result;
            }
        }

        if (player.isCrouching() && state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            BlockState state1 = cycleProperty(state, BlockStateProperties.HORIZONTAL_FACING);
            dimension.setBlock(pos, state1, 18);
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }
}
