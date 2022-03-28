package com.ultreon.randomthingz.item.creativetab;

import com.ultreon.randomthingz.init.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Special miscellaneous group.
 *
 * @author Qboi123
 */
public class SpecialsTab extends CreativeModeTab {
    public static final SpecialsTab instance = new SpecialsTab(CreativeModeTab.TABS.length, "randomthingz_specials");

    public SpecialsTab(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull
    ItemStack makeIcon() {
        return new ItemStack(ModItems.FIRE_SWORD.get());
    }
}
