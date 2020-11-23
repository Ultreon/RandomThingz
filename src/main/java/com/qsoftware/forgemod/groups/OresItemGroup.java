package com.qsoftware.forgemod.groups;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.NotNull;

/**
 * Ores item group.
 *
 * @author Qboi123
 */
public class OresItemGroup extends ItemGroup {
    public static final OresItemGroup instance = new OresItemGroup(ItemGroup.GROUPS.length, "qforgemod_ores");

    public OresItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Blocks.DIAMOND_ORE);
    }

    @Override
    public void fill(NonNullList<ItemStack> items) {
        super.fill(items);
        items.add(new ItemStack(Blocks.COAL_ORE));
        items.add(new ItemStack(Blocks.IRON_ORE));
        items.add(new ItemStack(Blocks.GOLD_ORE));
        items.add(new ItemStack(Blocks.REDSTONE_ORE));
        items.add(new ItemStack(Blocks.EMERALD_ORE));
        items.add(new ItemStack(Blocks.DIAMOND_ORE));
        items.add(new ItemStack(Blocks.COAL_BLOCK));
        items.add(new ItemStack(Blocks.IRON_BLOCK));
        items.add(new ItemStack(Blocks.GOLD_BLOCK));
        items.add(new ItemStack(Blocks.REDSTONE_BLOCK));
        items.add(new ItemStack(Blocks.EMERALD_BLOCK));
        items.add(new ItemStack(Blocks.DIAMOND_BLOCK));
    }
}
