package com.qsoftware.forgemod.api.providers;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public interface IBlockProvider extends IItemProvider, com.qsoftware.silent.lib.block.IBlockProvider {

    @Nonnull
    Block getBlock();

    default boolean blockMatches(ItemStack otherStack) {
        Item item = otherStack.getItem();
        return item instanceof BlockItem && blockMatches(((BlockItem) item).getBlock());
    }

    default boolean blockMatches(Block other) {
        return getBlock() == other;
    }

    @NotNull
    @Override
    default Item getItem() {
        return getBlock().asItem();
    }

    @NotNull
    @Override
    default Item asItem() {
        return getItem();
    }

    @Override
    default ResourceLocation getRegistryName() {
        //Make sure to use the block's registry name in case it somehow doesn't match
        return getBlock().getRegistryName();
    }

    @Override
    default String getTranslationKey() {
        return getBlock().getTranslationKey();
    }

    @Override
    default @NotNull Block asBlock() {
        return getBlock();
    }
}