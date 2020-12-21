package com.qsoftware.forgemod.common;

import com.qsoftware.forgemod.QForgeMod;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class QItemGroup extends ItemGroup {
    private final ItemStack icon;

    public QItemGroup(String label, ItemStack icon) {
        super(QForgeMod.MOD_ID + "_" + label);
        this.icon = icon;
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return this.icon;
    }
}
