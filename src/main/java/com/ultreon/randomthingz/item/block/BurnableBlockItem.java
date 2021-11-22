package com.ultreon.randomthingz.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

/**
 * Burnable block item class.
 *
 * @author Qboi123
 */
public class BurnableBlockItem extends BlockItem {
    private final int burnTime;

    public BurnableBlockItem(Block blockIn, Properties builder, int burnTime) {
        super(blockIn, builder);
        this.burnTime = burnTime;
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return burnTime;
    }
}
