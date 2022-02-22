package com.ultreon.randomthingz.item;

import net.minecraft.MethodsReturnNonnullByDefault;
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
public class CustomBowItem extends BowItem {
    private float velocity;
    private float inaccuracy;
    private boolean defaultFlame;
    private double arrowAttackDamage;
    private int knockback;

    @SuppressWarnings("unused")
    public CustomBowItem(Properties builder) {
        this(builder, 3f);
    }

    public CustomBowItem(Properties builder, float velocity) {
        this(builder, velocity, 1f);
    }

    public CustomBowItem(Properties builder, float velocity, float inaccuracy) {
        this(builder, velocity, inaccuracy, 1.0D);
    }

    public CustomBowItem(Properties builder, float velocity, float inaccuracy, double arrowAttackDamage) {
        this(builder, velocity, inaccuracy, arrowAttackDamage, 1);
    }

    public CustomBowItem(Properties builder, float velocity, float inaccuracy, double arrowAttackDamage, int knockback) {
        this(builder, velocity, inaccuracy, arrowAttackDamage, knockback, false);
    }

    public CustomBowItem(Properties builder, float velocity, float inaccuracy, double arrowAttackDamage, int knockback, boolean defaultFlame) {
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
        float f = (float) charge / 20f;
        f = (f * f + f * 2f) / 3f;
        if (f > 1f) {
            f = 1f;
        }

        return f;
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     * This code was modified for the custom item.
     */
    @ParametersAreNonnullByDefault
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity living, int timeLeft) {
        if (living instanceof Player player) {
            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
            ItemStack projectileStack = player.getProjectile(stack);

            int i = this.getUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, level, player, i, !projectileStack.isEmpty() || flag);
            if (i < 0) return;

            if (!projectileStack.isEmpty() || flag) {
                if (projectileStack.isEmpty()) {
                    projectileStack = new ItemStack(Items.ARROW);
                }

                float velocity = getArrowVelocity(i);
                if (!((double) velocity < 0.1D)) {
                    boolean flag1 = player.getAbilities().instabuild || (projectileStack.getItem() instanceof ArrowItem && ((ArrowItem) projectileStack.getItem()).isInfinite(projectileStack, stack, player));
                    if (!level.isClientSide) {
                        ArrowItem arrowItem = (ArrowItem) (projectileStack.getItem() instanceof ArrowItem ? projectileStack.getItem() : Items.ARROW);
                        AbstractArrow arrow = arrowItem.createArrow(level, projectileStack, player);
                        arrow = customArrow(arrow);
                        arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0f, velocity * this.velocity, this.inaccuracy);
                        if (velocity == 1f) {
                            arrow.setCritArrow(true);
                        }

                        int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
                        if (powerLevel > 0) {
                            arrow.setBaseDamage((arrow.getBaseDamage() + (double) powerLevel * 0.5D + 0.5D) * arrowAttackDamage);
                        }

                        int punchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
                        if (punchLevel > 0) {
                            arrow.setKnockback(punchLevel * knockback);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
                            arrow.setSecondsOnFire(100);
                        }

                        if (defaultFlame) {
                            arrow.setSecondsOnFire(100);
                        }

                        stack.hurtAndBreak(1, player, (p_220009_1_) -> p_220009_1_.broadcastBreakEvent(player.getUsedItemHand()));
                        if (flag1 || player.getAbilities().instabuild && (projectileStack.getItem() == Items.SPECTRAL_ARROW || projectileStack.getItem() == Items.TIPPED_ARROW)) {
                            arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }

                        level.addFreshEntity(arrow);
                    }

                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1f, 1f / (player.getRandom().nextFloat() * .4f + 1.2F) + velocity * .5f);
                    if (!flag1 && !player.getAbilities().instabuild) {
                        projectileStack.shrink(1);
                        if (projectileStack.isEmpty()) {
                            player.getInventory().removeItem(projectileStack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
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
