package com.qsoftware.forgemod.util;


import com.google.common.annotations.Beta;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

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
    public static <T extends Entity> Entity getTarget() {
        Minecraft mc = Minecraft.getInstance();
        float partialTicks = 1.0f;

        Entity entity = mc.getRenderViewEntity();
        if (entity != null) {
            if (mc.world != null) {
                mc.getProfiler().startSection("pick");
                mc.pointedEntity = null;
                double reachDistance = Objects.requireNonNull(mc.playerController).getBlockReachDistance();

                mc.objectMouseOver = entity.pick(reachDistance, partialTicks, false);
                Vector3d vector3d = entity.getEyePosition(partialTicks);
                boolean flag = false;
                int i = 3;
                double reach = reachDistance;
                if (mc.playerController.extendedReach()) {
                    reach = 6.0D;
                    reachDistance = reach;
                } else {
                    if (reachDistance > 3.0D) {
                        flag = true;
                    }
                }

                reach = reach * reach;
                if (mc.objectMouseOver != null) {
                    reach = mc.objectMouseOver.getHitVec().squareDistanceTo(vector3d);
                }

                Vector3d entityLook = entity.getLook(1.0F);
                Vector3d vector3d2 = vector3d.add(entityLook.x * reachDistance, entityLook.y * reachDistance, entityLook.z * reachDistance);
                float f = 1.0F;
                AxisAlignedBB expandedBounding = entity.getBoundingBox().expand(entityLook.scale(reachDistance)).grow(1.0D, 1.0D, 1.0D);
                EntityRayTraceResult entityraytraceresult = ProjectileHelper.rayTraceEntities(entity, vector3d, vector3d2, expandedBounding, (p_215312_0_) -> !p_215312_0_.isSpectator() && p_215312_0_.canBeCollidedWith(), reach);
                if (entityraytraceresult != null) {
                    Entity entity1 = entityraytraceresult.getEntity();
                    Vector3d vector3d3 = entityraytraceresult.getHitVec();
                    double distance = vector3d.squareDistanceTo(vector3d3);
                    if (flag && distance > 9.0D) {
                        mc.objectMouseOver = BlockRayTraceResult.createMiss(vector3d3, Direction.getFacingFromVector(entityLook.x, entityLook.y, entityLook.z), new BlockPos(vector3d3));
                    } else if (distance < reach || mc.objectMouseOver == null) {
                        mc.objectMouseOver = entityraytraceresult;
                        return entity1;
                    }
                }

                mc.getProfiler().endSection();
            }
        }
        return null;
    }

}