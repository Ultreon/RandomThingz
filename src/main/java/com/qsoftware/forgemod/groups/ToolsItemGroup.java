package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.NotNull;

/**
 * Tools item group.
 * For generic tools.
 *
 * @author Qboi123
 */
public class ToolsItemGroup extends ItemGroup {
    public static final ToolsItemGroup instance = new ToolsItemGroup(ItemGroup.GROUPS.length, "qforgemod_tools");

    public ToolsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Items.IRON_AXE);
    }

    @Override
    public void fill(NonNullList<ItemStack> items) {
        super.fill(items);
        items.add(new ItemStack(Items.LEATHER_HELMET));
        items.add(new ItemStack(Items.LEATHER_CHESTPLATE));
        items.add(new ItemStack(Items.LEATHER_LEGGINGS));
        items.add(new ItemStack(Items.LEATHER_BOOTS));
        items.add(new ItemStack(Items.WOODEN_SWORD));
        items.add(new ItemStack(Items.WOODEN_PICKAXE));
        items.add(new ItemStack(Items.WOODEN_SHOVEL));
        items.add(new ItemStack(Items.WOODEN_AXE));
        items.add(new ItemStack(Items.WOODEN_HOE));
        items.add(new ItemStack(Items.CHAINMAIL_HELMET));
        items.add(new ItemStack(Items.CHAINMAIL_CHESTPLATE));
        items.add(new ItemStack(Items.CHAINMAIL_LEGGINGS));
        items.add(new ItemStack(Items.CHAINMAIL_BOOTS));
        items.add(new ItemStack(Items.STONE_SWORD));
        items.add(new ItemStack(Items.STONE_PICKAXE));
        items.add(new ItemStack(Items.STONE_SHOVEL));
        items.add(new ItemStack(Items.STONE_AXE));
        items.add(new ItemStack(Items.STONE_HOE));
        items.add(new ItemStack(Items.GOLDEN_HELMET));
        items.add(new ItemStack(Items.GOLDEN_CHESTPLATE));
        items.add(new ItemStack(Items.GOLDEN_LEGGINGS));
        items.add(new ItemStack(Items.GOLDEN_BOOTS));
        items.add(new ItemStack(Items.GOLDEN_SWORD));
        items.add(new ItemStack(Items.GOLDEN_PICKAXE));
        items.add(new ItemStack(Items.GOLDEN_SHOVEL));
        items.add(new ItemStack(Items.GOLDEN_AXE));
        items.add(new ItemStack(Items.GOLDEN_HOE));
        items.add(new ItemStack(Items.IRON_HELMET));
        items.add(new ItemStack(Items.IRON_CHESTPLATE));
        items.add(new ItemStack(Items.IRON_LEGGINGS));
        items.add(new ItemStack(Items.IRON_BOOTS));
        items.add(new ItemStack(Items.IRON_SWORD));
        items.add(new ItemStack(Items.IRON_PICKAXE));
        items.add(new ItemStack(Items.IRON_SHOVEL));
        items.add(new ItemStack(Items.IRON_AXE));
        items.add(new ItemStack(Items.IRON_HOE));
        items.add(new ItemStack(Items.DIAMOND_HELMET));
        items.add(new ItemStack(Items.DIAMOND_CHESTPLATE));
        items.add(new ItemStack(Items.DIAMOND_LEGGINGS));
        items.add(new ItemStack(Items.DIAMOND_BOOTS));
        items.add(new ItemStack(Items.DIAMOND_SWORD));
        items.add(new ItemStack(Items.DIAMOND_PICKAXE));
        items.add(new ItemStack(Items.DIAMOND_SHOVEL));
        items.add(new ItemStack(Items.DIAMOND_AXE));
        items.add(new ItemStack(Items.DIAMOND_HOE));
        items.add(new ItemStack(Items.NETHERITE_HELMET));
        items.add(new ItemStack(Items.NETHERITE_CHESTPLATE));
        items.add(new ItemStack(Items.NETHERITE_LEGGINGS));
        items.add(new ItemStack(Items.NETHERITE_BOOTS));
        items.add(new ItemStack(Items.NETHERITE_SWORD));
        items.add(new ItemStack(Items.NETHERITE_PICKAXE));
        items.add(new ItemStack(Items.NETHERITE_SHOVEL));
        items.add(new ItemStack(Items.NETHERITE_AXE));
        items.add(new ItemStack(Items.NETHERITE_HOE));
    }
}
