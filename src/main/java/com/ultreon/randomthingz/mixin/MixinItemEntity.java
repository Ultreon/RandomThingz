package com.ultreon.randomthingz.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class MixinItemEntity extends Entity {
    public MixinItemEntity(EntityType<?> entityTypeIn, World dimensionIn) {
        super(entityTypeIn, dimensionIn);
    }

    @Inject(at = @At("HEAD"), method = "attack(Lnet/minecraft/util/DamageSource;F)Z", cancellable = true)
    private void attack(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callback) {
        if (source.isFireDamage()) {
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_PROTECTION, this.getItem()) > 0) {
                callback.setReturnValue(false);
            } else {
                if (getItem().getItem() == Items.GUNPOWDER) {
                    remove(false);
                    getEntityDimension().createExplosion(null, getPosX(), getPosY(), getPosZ(), ((float)getItem().getCount()) / 2, false, Explosion.Mode.BREAK);
                } else if (getItem().getItem() == Items.TNT) {
                    remove(false);
                    getEntityDimension().createExplosion(null, getPosX(), getPosY(), getPosZ(), getItem().getCount() * 4, false, Explosion.Mode.BREAK);
                } else if (getItem().getItem() == Items.TNT_MINECART) {
                    remove(false);
                    getEntityDimension().createExplosion(null, getPosX(), getPosY(), getPosZ(), getItem().getCount() * 4, false, Explosion.Mode.BREAK);
                }
            }
//        } else if (source.isExplosion()) {
//            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.BLAST_PROTECTION, this.getItem()) > 0) {
//                callback.setReturnValue(false);
//            } else {
//                if (getItem().getItem() == Items.GUNPOWDER) {
//                    remove(false);
//                    getEntityDimension().createExplosion(null, getPosX(), getPosY(), getPosZ(), getItem().getCount(), false, Explosion.Mode.BREAK);
//                } else if (getItem().getItem() == Items.TNT) {
//                    remove(false);
//                    getEntityDimension().createExplosion(null, getPosX(), getPosY(), getPosZ(), getItem().getCount() * 4, false, Explosion.Mode.BREAK);
//                } else if (getItem().getItem() == Items.TNT_MINECART) {
//                    remove(false);
//                    getEntityDimension().createExplosion(null, getPosX(), getPosY(), getPosZ(), getItem().getCount() * 4, false, Explosion.Mode.BREAK);
//                }
//            }
        } else if (source == DamageSource.CACTUS) {
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.PROJECTILE_PROTECTION, this.getItem()) > 0) {
                callback.setReturnValue(false);
            }
        } else if (source == DamageSource.ANVIL) {
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.PROJECTILE_PROTECTION, this.getItem()) > 0) {
                callback.setReturnValue(false);
            }
        } else if (source == DamageSource.FALLING_BLOCK) {
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.PROJECTILE_PROTECTION, this.getItem()) > 0) {
                callback.setReturnValue(false);
            }
        }
    }

    @Shadow
    public ItemStack getItem() {
        throw new IllegalStateException("Mixin failed to shadow getItem()");
    }
}
