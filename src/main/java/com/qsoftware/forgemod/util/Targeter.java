package com.qsoftware.forgemod.util;


import com.google.common.annotations.Beta;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import org.jetbrains.annotations.Nullable;

/**
 * Targeter for finding the target entity from an entity.
 * Ported from spigot plugin QServerCore.
 *
 * @author Qboi123
 */
@Beta
public final class Targeter {
    private Targeter() {
        throw new UnsupportedOperationException("Not allowed to initialize constructor.");
    }

    @Nullable
    public static <T extends Entity> Entity getTarget(PlayerEntity player) {
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;

        Vector3d vec3d = player.getEyePosition(1.0F);

        float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float) Math.PI / 180F));

        float f6 = f3 * f4;
        float f7 = f2 * f4;

        double d0 = 6;

        Vector3d vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);

        if (Minecraft.getInstance().world != null) {
            RayTraceResult raytraceresult = Minecraft.getInstance().world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, player));
            if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
                vec3d1 = raytraceresult.getHitVec();
            }

            RayTraceResult raytraceresult1 = ProjectileHelper.rayTraceEntities(Minecraft.getInstance().world, player, vec3d, vec3d1, player.getBoundingBox().grow(5.0D), entity -> !entity.equals(player));
            if (raytraceresult1 != null) {
                raytraceresult = raytraceresult1;
            }
            if (raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
                @SuppressWarnings("ConstantConditions") EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) raytraceresult;
                return entityRayTraceResult.getEntity();
            } else {
                return null;
            }
        }
        return null;
    }

}