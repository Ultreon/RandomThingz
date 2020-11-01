package com.qsoftware.forgemod.objects.items.base;

import com.qsoftware.forgemod.common.Sliceable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
