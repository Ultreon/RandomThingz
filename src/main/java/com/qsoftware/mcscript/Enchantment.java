package com.qsoftware.mcscript;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Enchantment {
    private net.minecraft.enchantment.Enchantment wrapper;

    Enchantment(net.minecraft.enchantment.Enchantment wrapper) {
        this.wrapper = wrapper;
    }

    @Nullable
    public static Enchantment fromRegistry(String name) {
        return fromRegistry(new ResourceLocation(name));
    }

    @Nullable
    private static Enchantment fromRegistry(ResourceLocation resourceLocation) {
        net.minecraft.enchantment.Enchantment value = ForgeRegistries.ENCHANTMENTS.getValue(resourceLocation);
        if (value == null) {
            return null;
        }
        return new Enchantment(value);
    }

    public boolean canApply(ItemStack stack) {
        return wrapper.canApply(stack.wrapper);
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return wrapper.canApplyAtEnchantingTable(stack.wrapper);
    }

    public boolean canGenerateInLoot() {
        return wrapper.canGenerateInLoot();
    }

    public boolean canVillagerTrade() {
        return wrapper.canVillagerTrade();
    }

    public void canApply(int level, CreatureAttribute creatureType) {
        wrapper.calcDamageByCreature(level, creatureType);
    }
}
