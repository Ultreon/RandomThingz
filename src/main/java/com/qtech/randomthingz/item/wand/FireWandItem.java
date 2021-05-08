package com.qtech.randomthingz.item.wand;

import com.qtech.randomthingz.item.base.WandItem;
import com.qtech.randomthingz.item.common.ModItems;
import com.qtech.randomthingz.modules.ui.ModItemGroups;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
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
        super(80, 20, new Properties().group(ModItemGroups.OVERPOWERED).rarity(Rarity.EPIC));
    }

    @Override
    public void activate(final ItemStack stack, final @NotNull World dimensionIn, final @NotNull LivingEntity livingIn, final float charge) {
        if (!(livingIn instanceof PlayerEntity)) {
            return;
        }

        final int strength = getStrength(stack);
        if (strength < 0) {
            return;
        }

        final float speed = 1 + (SPEED_MODIFIER * charge * strength);
        final int explosionPower = EXPLOSION_BASE + (int) (EXPLOSION_MODIFIER * charge * strength);

        final PlayerEntity player = (PlayerEntity) livingIn;

        if (!dimensionIn.isClientSided()) {
            ServerWorld dimension = (ServerWorld) dimensionIn;

            FireballEntity l = new FireballEntity(EntityType.FIREBALL, dimensionIn);
            l.setPositionAndRotation(player.getPosX(), player.getPosYEye(), player.getPosZ(), player.rotationYaw, player.rotationPitch);
            Vector3d vector3d = player.getLookVec();
            vector3d.mul(speed, speed, speed);
            l.setMotion(vector3d);
            l.explosionPower = explosionPower;
            l.setInvulnerable(true);
            l.setShooter(player);

            dimension.spawnEntity(l);

            player.addStat(Stats.ITEM_USED.get(this));
        }
    }
}
