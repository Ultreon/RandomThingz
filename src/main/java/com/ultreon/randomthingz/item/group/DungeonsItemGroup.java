package com.ultreon.randomthingz.item.group;

import com.ultreon.randomthingz.common.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Minecraft dungeons item groups.
 *
 * @author Qboi123
 */
public class DungeonsItemGroup extends CreativeModeTab {
    public static final DungeonsItemGroup instance = new DungeonsItemGroup(CreativeModeTab.TABS.length, "randomthingz_dungeons");

    public DungeonsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(ModItems.DUNGEONS.get());
    }
}
