package com.ultreon.randomthingz.item;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Advanced bow item class.
 * Support for custom velocity, inaccuracy, arrow attack damage, knockback and default flame.
 *
 * @author Qboi123
 */
@MethodsReturnNonnullByDefault
public class AdvancedBowItem extends BowItem {
    private float velocity;
    private float inaccuracy;
    private boolean defaultFlame;
    private double arrowAttackDamage;
    private int knockback;

    @SuppressWarnings("unused")
    public AdvancedBowItem(Properties builder) {
        this(builder, 3.0F);
    }

    public AdvancedBowItem(Properties builder, float velocity) {
        this(builder, velocity, 1.0F);
    }

    public AdvancedBowItem(Properties builder, float velocity, float inaccuracy) {
        this(builder, velocity, inaccuracy, 1.0D);
    }

    public AdvancedBowItem(Properties builder, float velocity, float inaccuracy, double arrowAttackDamage) {
        this(builder, velocity, inaccuracy, arrowAttackDamage, 1);
    }

    public AdvancedBowItem(Properties builder, float velocity, float inaccuracy, double arrowAttackDamage, int knockback) {
        this(builder, velocity, inaccuracy, arrowAttackDamage, knockback, false);
    }

    public AdvancedBowItem(Properties builder, float velocity, float inaccuracy, double arrowAttackDamage, int knockback, boolean defaultFlame) {
        super(builder.stacksTo(1));

        this.velocity = velocity;
        this.inaccuracy = inaccuracy;
        this.arrowAttackDamage = arrowAttackDamage;
        this.knockback = knockback;
        this.defaultFlame = defaultFlame;
    }

    /**
     * Gets the velocity of the arrow entity from the bow's charge
     */
    public static float getArrowVelocity(int charge) {
        float f = (float) charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    @ParametersAreNonnullByDefault
    @Override
    public void releaseUsing(ItemStack stack, Level dimensionIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player) {
            Player playerentity = (Player) entityLiving;
            boolean flag = playerentity.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
            ItemStack itemstack = playerentity.getProjectile(stack);

            int i = this.getUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, dimensionIn, playerentity, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getArrowVelocity(i);
                if (!((double) f < 0.1D)) {
                    boolean flag1 = playerentity.abilities.instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem) itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
                    if (!dimensionIn.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem) (itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrow abstractarrowentity = arrowitem.createArrow(dimensionIn, itemstack, playerentity);
                        abstractarrowentity = customArrow(abstractarrowentity);
                        abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, f * this.velocity, this.inaccuracy);
                        if (f == 1.0F) {
                            abstractarrowentity.setCritArrow(true);
                        }

                        int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
                        if (j > 0) {
                            abstractarrowentity.setBaseDamage((abstractarrowentity.getBaseDamage() + (double) j * 0.5D + 0.5D) * arrowAttackDamage);
                        }

                        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
                        if (k > 0) {
                            abstractarrowentity.setKnockback(k * knockback);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
                            abstractarrowentity.setSecondsOnFire(100);
                        }

                        if (defaultFlame) {
                            abstractarrowentity.setSecondsOnFire(100);
                        }

                        stack.hurtAndBreak(1, playerentity, (p_220009_1_) -> p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand()));
                        if (flag1 || playerentity.abilities.instabuild && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                            abstractarrowentity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }

                        dimensionIn.addFreshEntity(abstractarrowentity);
                    }

                    dimensionIn.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !playerentity.abilities.instabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            playerentity.inventory.removeItem(itemstack);
                        }
                    }

                    playerentity.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    /**
     * How long it takes to use or consume an item
     */
    @ParametersAreNonnullByDefault
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @ParametersAreNonnullByDefault
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@linkplain #onUseItem}.
     */
//    public ActionResult<ItemStack> onItemRightClick(World dimensionIn, PlayerEntity playerIn, Hand handIn) {
//        ItemStack itemstack = playerIn.getHeldItem(handIn);
//        boolean flag = !playerIn.findAmmo(itemstack).isEmpty();
//
//        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, dimensionIn, playerIn, handIn, flag);
//        if (ret != null) return ret;
//
//        if (!playerIn.abilities.isCreativeMode && !flag) {
//            return ActionResult.resultFail(itemstack);
//        } else {
//            playerIn.setActiveHand(handIn);
//            return ActionResult.resultConsume(itemstack);
//        }
//    }
//
//    /**
//     * Get the predicate to match ammunition when searching the player's inventory, not their main/offhand
//     */
//    public Predicate<ItemStack> getInventoryAmmoPredicate() {
//        return ARROWS;
//    }
//
//    public AbstractArrowEntity customArrow(AbstractArrowEntity arrow) {
//        return arrow;
//    }
//
//    public int getDefaultProjectileRange() {
//        return 15;
//    }
//
//    /**
//     * Called when the player stops using an Item (stops holding the right mouse button).
//     */
//    public void onPlayerStoppedUsing(ItemStack stack, World dimensionIn, LivingEntity entityLiving, int timeLeft) {
//        if (entityLiving instanceof PlayerEntity) {
//            PlayerEntity playerentity = (PlayerEntity)entityLiving;
//            boolean flag = playerentity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
//            ItemStack itemstack = playerentity.findAmmo(stack);
//
//            int i = this.getUseDuration(stack) - timeLeft;
//            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, dimensionIn, playerentity, i, !itemstack.isEmpty() || flag);
//            if (i < 0) return;
//
//            if (!itemstack.isEmpty() || flag) {
//                if (itemstack.isEmpty()) {
//                    itemstack = new ItemStack(Items.ARROW);
//                }
//
//                float f = getArrowVelocity(i);
//                if (!((double)f < 0.1D)) {
//                    boolean flag1 = playerentity.abilities.isCreativeMode || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
//                    if (!dimensionIn.isClientSided) {
//                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
//                        AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(dimensionIn, itemstack, playerentity);
//                        abstractarrowentity = customeArrow(abstractarrowentity);
//                        abstractarrowentity.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f * this.velocity, this.inaccuracy);
//                        if (f == 1.0F) {
//                            abstractarrowentity.setIsCritical(true);
//                        }
//
//                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
//                        if (j > 0) {
//                            abstractarrowentity.setDamage((abstractarrowentity.getDamage() + (double)j * 0.5D + 0.5D) * arrowAttackDamage);
//                        }
//
//                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
//                        if (k > 0) {
//                            abstractarrowentity.setKnockbackStrength(k * knockback);
//                        }
//
//                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
//                            abstractarrowentity.setFire(100);
//                        } else if (defaultFlame) {
//                            abstractarrowentity.setFire(100);
//                        }
//
//                        stack.damageItem(1, playerentity, (p_220009_1_) -> {
//                            p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
//                        });
//                        if (flag1 || playerentity.abilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
//                            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
//                        }
//
//                        dimensionIn.spawnEntity(abstractarrowentity);
//                    }
//
//                    dimensionIn.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
//                    if (!flag1 && !playerentity.abilities.isCreativeMode) {
//                        itemstack.shrink(1);
//                        if (itemstack.isEmpty()) {
//                            playerentity.inventory.deleteStack(itemstack);
//                        }
//                    }
//
//                    playerentity.addStat(Stats.ITEM_USED.get(this));
//                }
//            }
//        }
//    }
    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getInaccuracy() {
        return inaccuracy;
    }

    public void setInaccuracy(float inaccuracy) {
        this.inaccuracy = inaccuracy;
    }

    public boolean hasDefaultFlame() {
        return defaultFlame;
    }

    public void setDefaultFlame(boolean defaultFlame) {
        this.defaultFlame = defaultFlame;
    }

    public double getArrowAttackDamage() {
        return arrowAttackDamage;
    }

    public void setArrowAttackDamage(double arrowAttackDamage) {
        this.arrowAttackDamage = arrowAttackDamage;
    }

    public int getKnockback() {
        return knockback;
    }

    public void setKnockback(int knockback) {
        this.knockback = knockback;
    }
}
