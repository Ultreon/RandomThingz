package com.qtech.forgemod.modules.ui.groups;

import com.qtech.forgemod.modules.items.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Nature item groups.
 * Such as saplings, flowers.
 *
 * @author Qboi123
 */
public class NatureItemGroup extends ItemGroup {
    public static final NatureItemGroup instance = new NatureItemGroup(ItemGroup.GROUPS.length, "qforgemod_nature");

    public NatureItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(ModItems.EUCALYPTUS_LEAF.get());
    }
}
