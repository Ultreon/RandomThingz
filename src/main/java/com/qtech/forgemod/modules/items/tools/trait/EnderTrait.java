package com.qtech.forgemod.modules.items.tools.trait;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EnderTrait extends AbstractTrait {
    public EnderTrait() {

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
}
