package com.ultreon.randomthingz.util.builder;

import lombok.Builder;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@Builder
public class ItemTier implements Tier {
    protected int tier;
    protected int maxUses;
    protected float efficiency;
    protected float attackDamage;
    protected int enchantability;
    protected Supplier<Ingredient> repairMaterial;

    public ItemTier(int tier, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.tier = tier;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getUses() {
        return maxUses;
    }

    @Override
    public float getSpeed() {
        return efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamage;
    }

    @Override
    public int getLevel() {
        return tier;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return repairMaterial.get();
    }
}
