package com.qsoftware.forgemod.util.builder;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Armor material builder.
 *
 * @author Qboi123
 */
public class ArmorMaterialBuilder {
    public static class Builder implements IArmorMaterial {

        private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
        private final String name;
        private final int maxDamageFactor;
        private final int[] damageReductionAmountArray;
        private final int enchantability;
        private final SoundEvent soundEvent;
        private final float toughness;
        private final LazyValue<Ingredient> repairMaterial;
        private final float knockbackResistance;

        public Builder(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountsIn, int enchantabilityIn, float knockbackResistance, SoundEvent equipSoundIn, float toughnessIn, Supplier<Ingredient> repairMaterialSupplier) {
            this.name = nameIn;
            this.maxDamageFactor = maxDamageFactorIn;
            this.damageReductionAmountArray = damageReductionAmountsIn;
            this.enchantability = enchantabilityIn;
            this.knockbackResistance = knockbackResistance;
            this.soundEvent = equipSoundIn;
            this.toughness = toughnessIn;
            this.repairMaterial = new LazyValue<>(repairMaterialSupplier);
        }

        public int getDurability(EquipmentSlotType slotIn) {
            return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
        }

        public int getDamageReductionAmount(EquipmentSlotType slotIn) {
            return this.damageReductionAmountArray[slotIn.getIndex()];
        }

        public int getEnchantability() {
            return this.enchantability;
        }

        public SoundEvent getSoundEvent() {
            return this.soundEvent;
        }

        public Ingredient getRepairMaterial() {
            return this.repairMaterial.getValue();
        }

        @OnlyIn(Dist.CLIENT)
        public String getName() {
            return this.name;
        }

        public float getToughness() {
            return this.toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return this.knockbackResistance;
        }
    }
}
