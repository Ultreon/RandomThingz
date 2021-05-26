package com.qtech.randomthingz.modules.ui.groups;

import com.qtech.randomthingz.item.common.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Minecraft dungeons item groups.
 *
 * @author Qboi123
 */
public class DungeonsItemGroup extends ItemGroup {
    public static final DungeonsItemGroup instance = new DungeonsItemGroup(ItemGroup.GROUPS.length, "randomthingz_dungeons");

    public DungeonsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(ModItems.DUNGEONS.get());
    }
}
