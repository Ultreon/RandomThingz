package com.ultreon.randomthingz.util;

import lombok.experimental.UtilityClass;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

/**
 * TODO: Move to Silent Lib
 */
@UtilityClass
public final class ParticleUtils {
    public static <T extends ParticleOptions> void spawn(Level dimension, T type, Vec3i pos, int particleCount, double xOffset, double yOffset, double zOffset, double speed) {
        spawn(dimension, type, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, particleCount, xOffset, yOffset, zOffset, speed);
    }

    public static <T extends ParticleOptions> void spawn(Level dimension, T type, double x, double y, double z, int particleCount, double xOffset, double yOffset, double zOffset, double speed) {
        if (dimension instanceof ServerLevel) {
            ((ServerLevel) dimension).sendParticles(type, x, y, z, particleCount, xOffset, yOffset, zOffset, speed);
        }
    }
}
