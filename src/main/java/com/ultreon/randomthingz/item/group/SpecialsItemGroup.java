package com.ultreon.randomthingz.item.group;

import com.ultreon.randomthingz.common.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Special miscellaneous group.
 *
 * @author Qboi123
 */
public class SpecialsItemGroup extends CreativeModeTab {
    public static final SpecialsItemGroup instance = new SpecialsItemGroup(CreativeModeTab.TABS.length, "randomthingz_specials");

    public SpecialsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull
    ItemStack makeIcon() {
        return new ItemStack(ModItems.FIRE_SWORD.get());
    }
}
