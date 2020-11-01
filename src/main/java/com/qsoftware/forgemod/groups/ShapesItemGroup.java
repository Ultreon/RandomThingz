package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.BlockInit;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ShapesItemGroup extends ItemGroup {
    public static final ShapesItemGroup instance = new ShapesItemGroup(ItemGroup.GROUPS.length, "shapes_tab");

    public ShapesItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Blocks.OAK_STAIRS);
    }
}
