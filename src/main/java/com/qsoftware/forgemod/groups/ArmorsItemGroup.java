package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.ItemInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

public class ArmorsItemGroup extends ItemGroup {
    public static final ArmorsItemGroup instance = new ArmorsItemGroup(ItemGroup.GROUPS.length, "armors_tab");

    public ArmorsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Items.IRON_CHESTPLATE);
    }
}
