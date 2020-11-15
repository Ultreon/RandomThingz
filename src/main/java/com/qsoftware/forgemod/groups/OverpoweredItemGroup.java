package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.ModItems;
import com.qsoftware.forgemod.init.Registration;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.NotNull;

/**
 * OP item group.
 *
 * @author Qboi123
 */
public class OverpoweredItemGroup extends ItemGroup {
    public static final OverpoweredItemGroup instance = new OverpoweredItemGroup(ItemGroup.GROUPS.length, "qforgemod_god");

    public OverpoweredItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Blocks.COMMAND_BLOCK);
    }

    @Override
    public void fill(NonNullList<ItemStack> items) {
        super.fill(items);
        items.add(new ItemStack(Blocks.COMMAND_BLOCK));
        items.add(new ItemStack(Blocks.CHAIN_COMMAND_BLOCK));
        items.add(new ItemStack(Blocks.REPEATING_COMMAND_BLOCK));
        items.add(new ItemStack(Blocks.BARRIER));
        items.add(new ItemStack(Blocks.STRUCTURE_VOID));
        items.add(new ItemStack(Blocks.STRUCTURE_BLOCK));
        items.add(new ItemStack(Blocks.JIGSAW));
        items.add(new ItemStack(Blocks.SPAWNER));
        items.add(new ItemStack(Blocks.DRAGON_EGG));
        items.add(new ItemStack(Blocks.PETRIFIED_OAK_SLAB));
    }
}
