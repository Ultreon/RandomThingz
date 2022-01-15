package com.ultreon.randomthingz.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class MixinItemEntity extends Entity {
    public MixinItemEntity(EntityType<?> entityTypeIn, Level dimensionIn) {
        super(entityTypeIn, dimensionIn);
    }

    @Inject(at = @At("HEAD"), method = "attack(Lnet/minecraft/util/DamageSource;F)Z", cancellable = true, remap = false)
    private void attack(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callback) {
        if (source.isFire()) {
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_PROTECTION, this.getItem()) > 0) {
                callback.setReturnValue(false);
            } else {
                if (getItem().getItem() == Items.GUNPOWDER) {
                    remove(false);
                    getCommandSenderWorld().explode(null, getX(), getY(), getZ(), ((float) getItem().getCount()) / 2, false, Explosion.BlockInteraction.BREAK);
                } else if (getItem().getItem() == Items.TNT) {
                    remove(false);
                    getCommandSenderWorld().explode(null, getX(), getY(), getZ(), getItem().getCount() * 4, false, Explosion.BlockInteraction.BREAK);
                } else if (getItem().getItem() == Items.TNT_MINECART) {
                    remove(false);
                    getCommandSenderWorld().explode(null, getX(), getY(), getZ(), getItem().getCount() * 4, false, Explosion.BlockInteraction.BREAK);
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
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PROJECTILE_PROTECTION, this.getItem()) > 0) {
                callback.setReturnValue(false);
            }
        } else if (source == DamageSource.ANVIL) {
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PROJECTILE_PROTECTION, this.getItem()) > 0) {
                callback.setReturnValue(false);
            }
        } else if (source == DamageSource.FALLING_BLOCK) {
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PROJECTILE_PROTECTION, this.getItem()) > 0) {
                callback.setReturnValue(false);
            }
        }
    }

    @Shadow
    public ItemStack getItem() {
        throw new IllegalStateException("Mixin failed to shadow getItem()");
    }
}
