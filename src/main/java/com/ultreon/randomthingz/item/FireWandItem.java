package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.common.item.ModItemGroups;
import com.ultreon.randomthingz.common.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * Fire staff item class.<br>
 * <i>A item that shoots fireballs.</i><br>
 *
 * @author Qboi123
 * @see ModItems#FIRE_STAFF
 */
public class FireWandItem extends WandItem {
    private static final int SPEED_MODIFIER = 1;
    private static final int EXPLOSION_MODIFIER = 4;
    private static final int EXPLOSION_BASE = 4;

    public FireWandItem() {
        super(80, 20, new Item.Properties().tab(ModItemGroups.OVERPOWERED).rarity(Rarity.EPIC));
    }

    @Override
    public void activate(final ItemStack stack, final @NotNull Level dimensionIn, final @NotNull LivingEntity livingIn, final float charge) {
        if (!(livingIn instanceof Player)) {
            return;
        }

        final int strength = getStrength(stack);
        if (strength < 0) {
            return;
        }

        final float speed = 1 + (SPEED_MODIFIER * charge * strength);
        final int explosionPower = EXPLOSION_BASE + (int) (EXPLOSION_MODIFIER * charge * strength);

        final Player player = (Player) livingIn;

        if (!dimensionIn.isClientSide()) {
            ServerLevel dimension = (ServerLevel) dimensionIn;

            LargeFireball l = new LargeFireball(EntityType.FIREBALL, dimensionIn);
            l.absMoveTo(player.getX(), player.getEyeY(), player.getZ(), player.yRot, player.xRot);
            Vec3 vector3d = player.getLookAngle();
            vector3d.multiply(speed, speed, speed);
            l.setDeltaMovement(vector3d);
            l.explosionPower = explosionPower;
            l.setInvulnerable(true);
            l.setOwner(player);

            dimension.addFreshEntity(l);

            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }
}
