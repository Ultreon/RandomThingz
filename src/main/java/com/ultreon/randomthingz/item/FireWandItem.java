package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.common.item.ModCreativeTabs;
import com.ultreon.randomthingz.common.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.core.Direction.Axis;

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
        super(80, 20, new Item.Properties().tab(ModCreativeTabs.OVERPOWERED).rarity(Rarity.EPIC));
    }

    @Override
    public void activate(final ItemStack stack, final @NotNull Level dimensionIn, final @NotNull LivingEntity livingIn, final float charge) {
        if (!(livingIn instanceof final Player player)) return;

        final int strength = getStrength(stack);
        if (strength < 0) return;

        final float speed = 1 + (SPEED_MODIFIER * charge * strength);
        final int explosionPower = EXPLOSION_BASE + (int) (EXPLOSION_MODIFIER * charge * strength);

        if (!dimensionIn.isClientSide()) {
            final ServerLevel dimension = (ServerLevel) dimensionIn;
            final Vec3 direction = player.getLookAngle();

            direction.multiply(speed, speed, speed);

            final double powerX = player.getRandom().nextGaussian() * 0.05D + direction.get(Axis.X);
            final double powerY = player.getRandom().nextGaussian() * 0.05D + direction.get(Axis.Y);
            final double powerZ = player.getRandom().nextGaussian() * 0.05D + direction.get(Axis.Z);

            final LargeFireball l = new LargeFireball(dimensionIn, player, powerX, powerY, powerZ, explosionPower);
            l.absMoveTo(player.getX(), player.getEyeY(), player.getZ(), player.getYRot(), player.getXRot());
            l.setDeltaMovement(direction);
            l.setInvulnerable(true);
            l.setOwner(player);

            dimension.addFreshEntity(l);

            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }
}
