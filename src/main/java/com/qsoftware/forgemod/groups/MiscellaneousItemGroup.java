package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.NotNull;

/**
 * Miscellaneous item group.
 *
 * @author Qboi123
 */
public class MiscellaneousItemGroup extends ItemGroup {
    public static final MiscellaneousItemGroup instance = new MiscellaneousItemGroup(ItemGroup.GROUPS.length, "qforgemod_misc");

    public MiscellaneousItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(ModItems.LEGENDARY_ENDER_PEARL.get());
    }

    @Override
    public void fill(NonNullList<ItemStack> items) {
        super.fill(items);
        items.add(new ItemStack(Items.ENDER_PEARL));
        items.add(new ItemStack(Items.ENDER_EYE));
    }
}
