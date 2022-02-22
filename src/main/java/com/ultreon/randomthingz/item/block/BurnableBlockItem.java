package com.ultreon.randomthingz.item.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

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
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return burnTime;
    }
}
