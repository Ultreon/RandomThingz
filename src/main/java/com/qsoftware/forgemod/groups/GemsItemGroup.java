package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

/**
 * Gems item group.
 *
 * @author Qboi123
 */
public class GemsItemGroup extends ItemGroup {
    public static final GemsItemGroup instance = new GemsItemGroup(ItemGroup.GROUPS.length, "qforgemod_gems");

    public GemsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Items.DIAMOND);
    }
}
