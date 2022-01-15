package com.ultreon.randomthingz.util;


import com.google.common.annotations.Beta;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

/**
 * Targeter for finding the target entity from an entity.
 * Ported from spigot plugin QServerCore.
 *
 * @author Qboi123
 */
@Beta
@UtilityClass
public final class Targeter {
    @Nullable
    public static EntityHitResult rayTraceEntities(Player player, Level dimension) {
        if (player != null && dimension != null) {
            float f = player.xRot;
            float f1 = player.yRot;

            Vec3 vec3d = player.getEyePosition(1.0F);

            float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
            float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
            float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
            float f5 = Mth.sin(-f * ((float) Math.PI / 180F));

            float f6 = f3 * f4;
            float f7 = f2 * f4;

            double d0 = 16;

            Vec3 vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);

            HitResult raytraceresult = dimension.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
            if (raytraceresult.getType() != HitResult.Type.MISS) {
                vec3d1 = raytraceresult.getLocation();
            }

            EntityHitResult rayTraceResult1 = ProjectileUtil.getEntityHitResult(dimension, player, vec3d, vec3d1, player.getBoundingBox().inflate(16.0D), entity -> !entity.equals(player));
            if (rayTraceResult1 != null) {
                raytraceresult = rayTraceResult1;
            }
            if (raytraceresult.getType() == HitResult.Type.ENTITY) {
                if (raytraceresult instanceof EntityHitResult) {
                    return (EntityHitResult) raytraceresult;
                }
            }
        }
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    public static <T extends Entity> Entity getTarget(Player player) {
        float f = player.xRot;
        float f1 = player.yRot;

        Vec3 vec3d = player.getEyePosition(1.0F);

        float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float) Math.PI / 180F));

        float f6 = f3 * f4;
        float f7 = f2 * f4;

        double d0 = 6;

        Vec3 vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);

        if (Minecraft.getInstance().level != null) {
            HitResult raytraceresult = Minecraft.getInstance().level.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
            if (raytraceresult.getType() != HitResult.Type.MISS) {
                vec3d1 = raytraceresult.getLocation();
            }

            HitResult raytraceresult1 = ProjectileUtil.getEntityHitResult(Minecraft.getInstance().level, player, vec3d, vec3d1, player.getBoundingBox().inflate(5.0D), entity -> !entity.equals(player));
            if (raytraceresult1 != null) {
                raytraceresult = raytraceresult1;
            }
            if (raytraceresult.getType() == HitResult.Type.ENTITY) {
                @SuppressWarnings("ConstantConditions") EntityHitResult entityRayTraceResult = (EntityHitResult) raytraceresult;
                return entityRayTraceResult.getEntity();
            } else {
                return null;
            }
        }
        return null;
    }

}