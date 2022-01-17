package com.ultreon.randomthingz.item.tool.trait;

import com.ultreon.randomthingz.entity.damagesource.DamageSourceInfinitySword;
import com.ultreon.randomthingz.init.ModStats;
import com.ultreon.randomthingz.item.tool.ToolType;
import com.ultreon.randomthingz.item.tool.Toolset;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
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
        if (attacker.level.isClientSide) {
            // Don't do anything on client.
            return true;
        }
        if (victim instanceof Player playerVictim) {
            // Get victim
            if (isInfinite(playerVictim)) {
                victim.hurt(new DamageSourceInfinitySword(attacker).bypassArmor(), 4.0f);
                return true;
            }
            //noinspection ConstantConditions
            if (playerVictim.getItemInHand(InteractionHand.MAIN_HAND) != null && playerVictim.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Toolset.INFINITY.getSword().get() && playerVictim.isUsingItem()) {
                return true;
            }
        }

        // Combat tracking.
        victim.lastHurtByPlayerTime = 60;
        victim.getCombatTracker().recordDamage(new DamageSourceInfinitySword(attacker), victim.getHealth(), victim.getHealth());
        victim.setHealth(0);

        // Death event.
        victim.die(new EntityDamageSource("infinity", attacker));

        return true;
    }

    @SuppressWarnings("RedundantIfStatement")
    public static boolean isInfinite(Player player) {
        // Get armor list.
        List<ItemStack> armor = (List<ItemStack>) player.getArmorSlots();

        // Check Armor
        if (!armor.isEmpty()) {
            // Check boots.
            if (armor.get(0).getItem().equals(Toolset.INFINITY.getBoots().get())) {
                // Check leggings.
                if (armor.get(1).getItem().equals(Toolset.INFINITY.getLeggings().get())) {
                    // Check chestplate.
                    if (armor.get(2).getItem().equals(Toolset.INFINITY.getChestplate().get())) {
                        // Check helmet.
                        if (armor.get(3).getItem().equals(Toolset.INFINITY.getHelmet().get())) {
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
    public void onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (!entity.level.isClientSide && entity instanceof Player victim) {
            if (victim.isCreative() && !(victim.getHealth() <= 0) && victim.getHealth() > 0 && !isInfinite(victim)) {
                victim.getCombatTracker().recordDamage(new DamageSourceInfinitySword(player), victim.getHealth(), victim.getHealth());
                victim.setHealth(0);
                victim.die(new EntityDamageSource("infinity", player));
                player.awardStat(ModStats.INFINITY_KILL, 1);
            }
        } else if (!entity.level.isClientSide && !(entity instanceof LivingEntity)) {
            if (entity.tickCount > 100) {
                entity.remove(Entity.RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    public boolean onBlockBroken(ItemStack stack, Level dimensionIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        return true;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        player.level.destroyBlock(pos, true);
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
