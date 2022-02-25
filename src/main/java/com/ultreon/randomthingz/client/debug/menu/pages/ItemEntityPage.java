package com.ultreon.randomthingz.client.debug.menu.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.client.debug.menu.DebugEntry;
import com.ultreon.randomthingz.client.debug.menu.DebugRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ItemEntityPage extends EntityPage {
    public ItemEntityPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public void render(PoseStack poseStack, DebugRenderContext ctx) {
        if (Minecraft.getInstance().player != null) {
            Player player = Minecraft.getInstance().player;
            float xRot = player.getXRot();
            float yRot = player.getYRot();

            Vec3 clipStart = player.getEyePosition(1f);

            float f2 = Mth.cos(-yRot * ((float) Math.PI / 180f) - (float) Math.PI);
            float f3 = Mth.sin(-yRot * ((float) Math.PI / 180f) - (float) Math.PI);
            float f4 = -Mth.cos(-xRot * ((float) Math.PI / 180f));
            float deltaY = Mth.sin(-xRot * ((float) Math.PI / 180f));

            float deltaX = f3 * f4;
            float deltaZ = f2 * f4;

            double distance = 16;

            Vec3 clipEnd = clipStart.add((double) deltaX * distance, (double) deltaY * distance, (double) deltaZ * distance);

            if (Minecraft.getInstance().level != null) {
                HitResult hit = Minecraft.getInstance().level.clip(new ClipContext(clipStart, clipEnd, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
                if (hit.getType() != HitResult.Type.MISS) {
                    clipEnd = hit.getLocation();
                }

                HitResult tempHit = ProjectileUtil.getEntityHitResult(Minecraft.getInstance().level, player, clipStart, clipEnd, player.getBoundingBox().inflate(16.0D), entity -> !entity.equals(player));
                if (tempHit != null) {
                    hit = tempHit;
                }
                if (hit instanceof EntityHitResult entityHit && entityHit.getEntity() instanceof ItemEntity entity) {
                    ctx.left("Thrower", entity.getThrower());
                    ctx.left("Owner", entity.getOwner());
                    ctx.left("Item", entity.getItem());

                    ctx.right("Attackable", entity.isAttackable());
                }
            }
        }
    }

    @Override
    public List<DebugEntry> getLinesRight() {
        List<DebugEntry> list = new ArrayList<>();
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            list.add(new DebugEntry("$PLAYER$", null));
            return list;
        }

        float f = player.getXRot();
        float f1 = player.getYRot();
        Vec3 vec3d = player.getEyePosition(1f);

        float f2 = Mth.cos(-f1 * ((float) Math.PI / 180f) - (float) Math.PI);
        float f3 = Mth.sin(-f1 * ((float) Math.PI / 180f) - (float) Math.PI);
        float f4 = -Mth.cos(-f * ((float) Math.PI / 180f));
        float f5 = Mth.sin(-f * ((float) Math.PI / 180f));
        float f6 = f3 * f4;
        float f7 = f2 * f4;

        double d0 = 16;

        Vec3 vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
        Minecraft mc = Minecraft.getInstance();
        ClientLevel dimension = Minecraft.getInstance().level;
        if (dimension == null) {
            list.add(new DebugEntry("$WORLD$", null));
            return list;
        }
        HitResult raytraceresult = Minecraft.getInstance().level.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if (raytraceresult.getType() != HitResult.Type.MISS) {
            vec3d1 = raytraceresult.getLocation();
        }

        HitResult rayTraceResult1 = ProjectileUtil.getEntityHitResult(dimension, player, vec3d, vec3d1, player.getBoundingBox().inflate(16.0D), entity -> !entity.equals(player));
        if (rayTraceResult1 != null) {
            raytraceresult = rayTraceResult1;
        }
        if (raytraceresult.getType() == HitResult.Type.ENTITY) {
            @SuppressWarnings("ConstantConditions") EntityHitResult entityRayTraceResult = (EntityHitResult) raytraceresult;

            Entity entity = entityRayTraceResult.getEntity();
            if (entity instanceof ItemEntity) {
                ItemEntity itemEntity = (ItemEntity) entity;
                list.add(new DebugEntry("age", itemEntity::getAge));
                list.add(new DebugEntry("item", itemEntity::getItem));
                list.add(new DebugEntry("leashPosition", () -> itemEntity.getRopeHoldPosition(mc.getFrameTime())));
                list.add(new DebugEntry("pose", itemEntity::getPose));
            } else {
                list.add(new DebugEntry("$ITEM_ENTITY$", null));
                return list;
            }
        } else {
            list.add(new DebugEntry("$ENTITY$", null));
            return list;
        }
        return list;
    }

    @Override
    public boolean hasRequiredComponents() {
        return Minecraft.getInstance().player != null;
    }
}
