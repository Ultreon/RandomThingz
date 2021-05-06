package com.qtech.forgemod.client.groups;

import com.qtech.forgemod.modules.tiles.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Food item group.
 *
 * @author Qboi123
 */
public class FurnitureItemGroup extends ItemGroup {
    public static final FurnitureItemGroup instance = new FurnitureItemGroup(ItemGroup.GROUPS.length, "qforgemod_furniture");

    public FurnitureItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(ModBlocks.GAME_PC.get());
    }
}
