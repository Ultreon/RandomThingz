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

import static net.minecraft.ChatFormatting.RED;

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
                } else {
                    // not looking at a block, or too far away from one to tell
                    ctx.top(RED + "<No Entity Was Found>");
                }
            } else {
                ctx.top(RED + "<World / Dimension Not Found>");
            }
        } else {
            ctx.top(RED + "<Local Player Not Found>");
        }
    }
}
