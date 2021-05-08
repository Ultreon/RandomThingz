package com.qtech.randomthingz.util;

import lombok.experimental.UtilityClass;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * TODO: Move to Silent Lib
 */
@UtilityClass
public final class ParticleUtils {
    public static <T extends IParticleData> void spawn(World dimension, T type, Vector3i pos, int particleCount, double xOffset, double yOffset, double zOffset, double speed) {
        spawn(dimension, type, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, particleCount, xOffset, yOffset, zOffset, speed);
    }

    public static <T extends IParticleData> void spawn(World dimension, T type, double x, double y, double z, int particleCount, double xOffset, double yOffset, double zOffset, double speed) {
        if (dimension instanceof ServerWorld) {
            ((ServerWorld) dimension).spawnParticle(type, x, y, z, particleCount, xOffset, yOffset, zOffset, speed);
        }
    }
}
