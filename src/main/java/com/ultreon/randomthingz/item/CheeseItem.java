package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.init.ModBlocks;
import com.ultreon.randomthingz.common.interfaces.Sliceable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

/**
 * Sliceable item class.
 *
 * @author Qboi123
 */
public class CheeseItem extends BlockItem implements Sliceable {
    private final SliceAction action;

    public CheeseItem(Properties properties, SliceAction action) {
        super(ModBlocks.CHEESE.get(), properties);

        this.action = action;
    }

    @Override
    public ItemStack onKnifeSlice(ItemStack stack) {
        return action.slice(stack);
    }

    @FunctionalInterface
    public interface SliceAction {
        ItemStack slice(ItemStack stack);
    }
}
