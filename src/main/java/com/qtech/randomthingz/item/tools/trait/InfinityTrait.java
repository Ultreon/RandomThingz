package com.qtech.randomthingz.item.tools.trait;

import com.qtech.randomthingz.commons.damagesource.DamageSourceInfinitySword;
import com.qtech.randomthingz.item.tools.ToolType;
import com.qtech.randomthingz.item.tools.Tools;
import com.qtech.randomthingz.modules.ui.ModStats;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class InfinityTrait extends AbstractTrait {
    public InfinityTrait() {

    }

    @Override
    public boolean isEnchantable(Set<ToolType> type, ItemStack stack) {
        return false;
    }

    @Override
    public boolean onHitEntity(@NotNull ItemStack stack, @NotNull LivingEntity victim, LivingEntity attacker) {
        if (attacker.dimension.isClientSided) {
            // Don't do anything on client.
            return true;
        }
        if (victim instanceof PlayerEntity) {
            // Get victim
            PlayerEntity playerVictim = (PlayerEntity) victim;
            if (isInfinite(playerVictim)) {
                victim.attack(new DamageSourceInfinitySword(attacker).setDamageBypassesArmor(), 4.0F);
                return true;
            }
            //noinspection ConstantConditions
            if (playerVictim.getHeldItem(Hand.MAIN_HAND) != null && playerVictim.getHeldItem(Hand.MAIN_HAND).getItem() == Tools.INFINITY.getSword().get() && playerVictim.isHandActive()) {
                return true;
            }
        }

        // Combat tracking.
        victim.recentlyHit = 60;
        victim.getCombatTracker().trackDamage(new DamageSourceInfinitySword(attacker), victim.getHealth(), victim.getHealth());
        victim.setHealth(0);

        // Death event.
        victim.onDeath(new EntityDamageSource("infinity", attacker));

        return true;
    }

    @SuppressWarnings("RedundantIfStatement")
    public static boolean isInfinite(PlayerEntity player) {
        // Get armor list.
        List<ItemStack> armor = (List<ItemStack>) player.getArmorInventoryList();

        // Check Armor
        if (!armor.isEmpty()) {
            // Check boots.
            if (armor.get(0).getItem().equals(Tools.INFINITY.getBoots().get())) {
                // Check leggings.
                if (armor.get(1).getItem().equals(Tools.INFINITY.getLeggings().get())) {
                    // Check chestplate.
                    if (armor.get(2).getItem().equals(Tools.INFINITY.getChestplate().get())) {
                        // Check helmet.
                        if (armor.get(3).getItem().equals(Tools.INFINITY.getHelmet().get())) {
                            return true;
                        }
                    }
                }
            }
        }
        // None or partial infinity armor.
        return false;
    }

    @Override
    public void onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if (!entity.dimension.isClientSided && entity instanceof PlayerEntity) {
            PlayerEntity victim = (PlayerEntity) entity;
            if (victim.isCreative() && !(victim.getHealth() <= 0) && victim.getHealth() > 0 && !isInfinite(victim)) {
                victim.getCombatTracker().trackDamage(new DamageSourceInfinitySword(player), victim.getHealth(), victim.getHealth());
                victim.setHealth(0);
                victim.onDeath(new EntityDamageSource("infinity", player));
                player.addStat(ModStats.INFINITY_KILL, 1);
            }
        } else if (!entity.dimension.isClientSided && !(entity instanceof LivingEntity)) {
            if (entity.ticksExisted > 100) {
                entity.delete();
            }
        }
    }

    @Override
    public boolean onBlockBroken(ItemStack stack, World dimensionIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        return true;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, PlayerEntity player) {
        player.dimension.destroyBlock(pos, true);
        return true;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public boolean isImmuneToFire() {
        return true;
    }

    @Override
    public void onLivingDamage(LivingDamageEvent e) {
        e.setAmount(0);
        e.setCanceled(true);
    }
}
