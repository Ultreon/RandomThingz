package com.qtech.randomthingz.item.tools.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.Color;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Attacked entity has 5 in 16 chance to randomly teleport.
 * Right click causes teleport to the block you looking at in range.
 */
public class EnderTrait extends AbstractTrait {
    public EnderTrait() {

    }

    public Color getColor() {
        return Color.fromHex("#3D9964");
    }

    @Override
    public boolean onHitEntity(@NotNull ItemStack stack, @NotNull LivingEntity victim, LivingEntity attacker) {
        super.onHitEntity(stack, victim, attacker);

        int retries = 5;

        while (retries > 0) {
            if (victim.getRNG().nextInt(16) == 0) {
                World entityDimension = victim.getEntityDimension();
                int x = victim.getRNG().nextInt(20) - 10;
                int z = victim.getRNG().nextInt(20) - 10;
                int y = entityDimension.getHeight(Heightmap.Type.WORLD_SURFACE, x, z);

                victim.teleportKeepLoaded(x, y, z);
                break;
            }

            retries--;
        }

        return true;
    }

    protected static BlockRayTraceResult rayTrace(World dimensionIn, PlayerEntity player) {
        float pitch = player.rotationPitch;
        float yaw = player.rotationYaw;

        // Get the player's eye position, put is as start position.
        Vector3d startPos = player.getEyePosition(1.0F);

        // Calculations.
        float fz = MathHelper.cos(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
        float fx = MathHelper.sin(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
        float f = -MathHelper.cos(-pitch * ((float) Math.PI / 180F));
        float lookY = MathHelper.sin(-pitch * ((float) Math.PI / 180F));
        float lookX = fx * f;
        float lookZ = fz * f;

        // Ray length.
        double rayLength = 12;
        Vector3d endPos = startPos.add((double) lookX * rayLength, (double) lookY * rayLength, (double) lookZ * rayLength);

        // Raytracing.
        return dimensionIn.rayTraceBlocks(new RayTraceContext(startPos, endPos, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, player));
    }

    @Override
    public boolean onRightClick(Item item, World dimension, PlayerEntity clicker, Hand hand) {
        if (dimension instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) dimension;

            RayTraceResult raytraceresult = rayTrace(serverWorld, clicker);
            double posX = raytraceresult.getHitVec().x;
            double posY = Math.floor(raytraceresult.getHitVec().y);
            double posZ = raytraceresult.getHitVec().z;

            for (int i = 0; i < 32; ++i) {
                clicker.dimension.addParticle(ParticleTypes.PORTAL, posX, posY + clicker.getRNG().nextDouble() * 2.0D, posZ, clicker.getRNG().nextGaussian(), 0.0D, clicker.getRNG().nextGaussian());
            }

            if (clicker.isPassenger()) {
                clicker.stopRiding();
            }

            clicker.setPositionAndUpdate(posX, posY, posZ);
            clicker.fallDistance = 0.0F;

            clicker.addStat(Stats.ITEM_USED.get(item));
            clicker.getCooldownTracker().setCooldown(item, 100);
        }
        return true;
    }

    @Override
    public void onLivingDamage(LivingDamageEvent e) {
        LivingEntity livingBeing = e.getEntityLiving();
        if (e.getEntityLiving().getHealth() - e.getAmount() < 1.0f) {
            if (livingBeing.getRNG().nextInt(5) == 0) {
                e.setCanceled(true);

                World entityDimension = livingBeing.getEntityDimension();
                int x = livingBeing.getRNG().nextInt(20) - 10;
                int z = livingBeing.getRNG().nextInt(20) - 10;
                x = (int) (e.getEntityLiving().getPosX() + x);
                z = (int) (e.getEntityLiving().getPosZ() + z);
                int y = entityDimension.getHeight(Heightmap.Type.WORLD_SURFACE, x, z);

                livingBeing.teleportKeepLoaded(x, y, z);
                return;
            }
        }
        super.onLivingDamage(e);
    }
}
