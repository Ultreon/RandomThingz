package com.ultreon.randomthingz.item.creativetab;

import com.ultreon.randomthingz.init.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Minecraft dungeons item groups.
 *
 * @author Qboi123
 */
public class DungeonsTab extends CreativeModeTab {
    public static final DungeonsTab instance = new DungeonsTab(CreativeModeTab.TABS.length, "randomthingz_dungeons");

    public DungeonsTab(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(ModItems.DUNGEONS.get());
    }
}
