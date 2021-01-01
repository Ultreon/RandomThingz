package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.common.BetterItemGroup;
import com.qsoftware.forgemod.init.variants.OreMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MetalsAndGems extends BetterItemGroup {
    public MetalsAndGems() {
        super(new ResourceLocation(QForgeMod.MOD_ID, "metals_and_gems"), Items.IRON_INGOT);
    }

    @Override
    public void fill(@NotNull NonNullList<ItemStack> items) {
        addEnumValues(items, OreMaterial.class, (material) -> material.getOre().orElse(null));
        addEnumValues(items, OreMaterial.class, (material) -> material.getChunks().orElse(null));
        addEnumValues(items, OreMaterial.class, (material) -> material.getStorageBlock().orElse(null));
        addEnumValues(items, OreMaterial.class, (material) -> material.getIngot().orElse(null));
        addEnumValues(items, OreMaterial.class, (material) -> material.getNugget().orElse(null));
        addEnumValues(items, OreMaterial.class, (material) -> material.getDust().orElse(null));
    }
}
