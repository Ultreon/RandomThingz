package com.ultreon.randomthingz.item.type;

import com.ultreon.randomthingz.common.interfaces.Sliceable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Sliceable item class.
 *
 * @author Qboi123
 */
public class SliceableItem extends Item implements Sliceable {
    private final SliceAction action;

    public SliceableItem(Properties properties, SliceAction action) {
        super(properties);

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
