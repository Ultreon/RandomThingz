package com.ultreon.randomthingz.client.debug.menu.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.client.debug.menu.DebugEntry;
import com.ultreon.randomthingz.client.debug.menu.DebugRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class LivingEntityPage extends EntityPage {
    public LivingEntityPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public void render(PoseStack poseStack, DebugRenderContext ctx) {
        if (Minecraft.getInstance().player != null) {
            Player player = Minecraft.getInstance().player;
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

            if (Minecraft.getInstance().level != null) {
                HitResult hit = Minecraft.getInstance().level.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
                if (hit.getType() != HitResult.Type.MISS) {
                    vec3d1 = hit.getLocation();
                }

                HitResult tempHit = ProjectileUtil.getEntityHitResult(Minecraft.getInstance().level, player, vec3d, vec3d1, player.getBoundingBox().inflate(16.0D), entity -> !entity.equals(player));
                if (tempHit != null) {
                    hit = tempHit;
                }
                if (hit instanceof EntityHitResult entityHit) {
                    if (entityHit.getEntity() instanceof LivingEntity entity) {
                        ctx.left("Health", entity.getHealth());
                        ctx.left("Max Health", entity.getMaxHealth());
                        ctx.left("Absorption", entity.getAbsorptionAmount());
                        ctx.left("Armor Points", entity.getArmorValue());
                        ctx.left("Arrows", entity.getArrowCount());
                        ctx.left("In Combat", entity.getCombatTracker().isInCombat());
                        ctx.left("Time Fall-flying", entity.getFallFlyingTicks());
                        ctx.left("Main Hand Item", entity.getItemInHand(InteractionHand.MAIN_HAND));
                        ctx.left("Off Hand Item", entity.getItemInHand(InteractionHand.OFF_HAND));
                        ctx.left("Jump Boost Power", entity.getJumpBoostPower());
                        ctx.left("Mob Type", entity.getMobType());
                        ctx.left("Idle For", entity.getNoActionTime());
                        ctx.left("Scale", entity.getScale());
                        ctx.left("Speed", entity.getSpeed());
                        ctx.left("Used Hand", entity.getUsedItemHand());
                        ctx.left("Voice Pitch", entity.getVoicePitch());
                        ctx.left("Head Rotation", entity.getYHeadRot());

                        ctx.right("Alive", entity.isAlive());
                        ctx.right("Baby Variant", entity.isBaby());
                        ctx.right("Affected By Potions", entity.isAffectedByPotions());
                        ctx.right("Is Blocking", entity.isBlocking());
                        ctx.right("Glowing", entity.isCurrentlyGlowing());
                        ctx.right("Dead / Dying", entity.isDeadOrDying());
                        ctx.right("Effective AI", entity.isEffectiveAi());
                        ctx.right("Fall Flying", entity.isFallFlying());
                        ctx.right("In Wall", entity.isInWall());
                        ctx.right("Pickable", entity.isPickable());
                        ctx.right("Pushable", entity.isPushable());
                        ctx.right("Sensitive To Water", entity.isSensitiveToWater());
                        ctx.right("Sleeping", entity.isSleeping());
                        ctx.right("Using Item", entity.isUsingItem());
                        ctx.right("Visually Swimming", entity.isVisuallySwimming());
                    }
                }
            }
        }
    }
}
