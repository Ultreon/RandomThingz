package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.ItemInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SpecialsItemGroup extends ItemGroup {
    public static final SpecialsItemGroup instance = new SpecialsItemGroup(ItemGroup.GROUPS.length, "qforgemod_specials");

    public SpecialsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(ItemInit.WALKING_STAFF);
    }
}
