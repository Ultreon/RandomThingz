package com.qsoftware.forgemod.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public class MixinItemEntity {
    @Inject(at = @At("HEAD"), method = "attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z", cancellable = true)
    private void attackEntityFrom(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callback) {
        System.out.println("HELLO MIXIN");
        if (source.isFireDamage() && EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_PROTECTION, this.getItem()) > 0) {
            callback.setReturnValue(false);
        }
    }

    @Shadow
    public ItemStack getItem() {
        throw new IllegalStateException("Mixin failed to shadow getItem()");
    }
}