package com.ultreon.randomthingz.modules.ui.groups;

import com.ultreon.randomthingz.item.common.ModItemsAlt;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Spawn egg item group.
 *
 * @author Qboi123
 */
public class SpawnEggsItemGroup extends ItemGroup {
    public static final SpawnEggsItemGroup instance = new SpawnEggsItemGroup(ItemGroup.GROUPS.length, "randomthingz_spawn_eggs");

    public SpawnEggsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull
    ItemStack createIcon() {
        return new ItemStack(ModItemsAlt.BABY_CREEPER_SPAWN_EGG);
    }
}
