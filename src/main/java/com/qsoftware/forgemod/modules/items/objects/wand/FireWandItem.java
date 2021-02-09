package com.qsoftware.forgemod.modules.items.objects.wand;

import com.qsoftware.forgemod.modules.ui.ModItemGroups;
import com.qsoftware.forgemod.modules.items.objects.base.WandItem;
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
 * Fire staff item class.
 *
 * @author Qboi123
 */
public class FireWandItem extends WandItem {
    public FireWandItem() {
        super(80, 20, new Properties().group(ModItemGroups.OVERPOWERED).rarity(Rarity.EPIC));
    }

    @Override
    public void activate(ItemStack stack, @NotNull World worldIn, @NotNull LivingEntity livingIn, float charge) {
        if (!(livingIn instanceof PlayerEntity)) {
            return;
        }

        int strength = getStrength(stack);
        if (strength == -1) {
            return;
        }

        PlayerEntity player = (PlayerEntity) livingIn;

        if (worldIn instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) worldIn;

            FireballEntity l = new FireballEntity(EntityType.FIREBALL, worldIn);
            l.setPositionAndRotation(player.getPosX(), player.getPosY() + 1, player.getPosZ(), player.rotationYaw, player.rotationPitch);
            Vector3d vector3d = player.getLookVec();
            l.setMotion(vector3d);
            l.explosionPower = (int) (4 * charge * strength);
            l.accelerationX = vector3d.x * (3 * charge * strength);
            l.accelerationY = vector3d.y * (3 * charge * strength);
            l.accelerationZ = vector3d.z * (3 * charge * strength);
            l.setInvulnerable(true);
            l.setShooter(player);

            world.addEntity(l);

            player.addStat(Stats.ITEM_USED.get(this));
        }
    }
}
