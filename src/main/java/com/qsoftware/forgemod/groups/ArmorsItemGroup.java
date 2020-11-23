package com.qsoftware.forgemod.groups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

/**
 * Armor item group.
 *
 * @author Qboi123
 */
@Deprecated
public class ArmorsItemGroup extends ItemGroup {
    public static final ArmorsItemGroup instance = new ArmorsItemGroup(ItemGroup.GROUPS.length, "qforgemod_armors");

    public ArmorsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Items.IRON_CHESTPLATE);
    }
}
