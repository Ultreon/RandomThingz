package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.BlockInit;
import com.qsoftware.forgemod.init.ItemInit;
import com.qsoftware.forgemod.init.renew.ItemInitNew;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

/**
 * Spawn egg item group.
 *
 * @author Qboi123
 */
public class SpawnEggsItemGroup extends ItemGroup {
    public static final SpawnEggsItemGroup instance = new SpawnEggsItemGroup(ItemGroup.GROUPS.length, "qforgemod_spawn_eggs");

    public SpawnEggsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(ItemInitNew.BABY_CREEPER_SPAWN_EGG);
    }
}
