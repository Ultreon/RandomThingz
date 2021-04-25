package com.qtech.forgemod.groups;

import com.qtech.forgemod.QForgeMod;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

/**
 * Gems item group.
 *
 * @author Qboi123
 */
public class GemsItemGroup extends ItemGroup {
    public static final GemsItemGroup instance = new GemsItemGroup(ItemGroup.GROUPS.length, "qforgemod_gems");

    public GemsItemGroup(int index, String label) {
        super(index, label);
        setBackgroundImage(QForgeMod.rl("creative_inventory/tab_gems.png"));
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Items.DIAMOND);
    }
}
