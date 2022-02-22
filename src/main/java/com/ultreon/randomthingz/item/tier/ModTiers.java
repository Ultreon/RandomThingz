package com.ultreon.randomthingz.item.tier;

import com.ultreon.randomthingz.common.item.ItemMaterial;
import com.ultreon.randomthingz.util.builder.ItemTier;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class ModTiers {
    public static final Tier INFINITY = ItemTier.builder()
            .tier(7).maxUses((int) Float.POSITIVE_INFINITY).efficiency(Float.POSITIVE_INFINITY).attackDamage(Float.POSITIVE_INFINITY).enchantability((int) Float.POSITIVE_INFINITY)
            .repairMaterial(() -> Ingredient.of(ItemMaterial.INFINITY.getIngot().orElseThrow())).build();
    public static final Tier ULTRINIUM = ItemTier.builder()
            .tier(6).maxUses(65536).efficiency(102.8f).attackDamage(127f).enchantability(224)
            .repairMaterial(() -> Ingredient.of(ItemMaterial.ULTRINIUM.getIngot().orElseThrow())).build();
    public static final Tier COBALT = ItemTier.builder()
            .tier(5).maxUses(6535).efficiency(27.5f).attackDamage(31f).enchantability(93)
            .repairMaterial(() -> Ingredient.of(ItemMaterial.COBALT.getIngot().orElseThrow())).build();
}
