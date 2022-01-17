package com.ultreon.randomthingz.client.debug.pages;

import com.ultreon.randomthingz.client.debug.DebugPage;
import com.ultreon.randomthingz.client.debug.DebugText;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class TargetEntityPage extends DebugPage {
    @Override
    public @NotNull
    DebugText generateText() {
        DebugText debugText = new DebugText();

        if (mc.player != null) {
            Player player = mc.player;
            float playerPitch = player.xRot;
            float playerYaw = player.yRot;

            Vec3 playerEye = player.getEyePosition(1.0f);

            float f2 = Mth.cos(-playerYaw * ((float) Math.PI / 180F) - (float) Math.PI);
            float f3 = Mth.sin(-playerYaw * ((float) Math.PI / 180F) - (float) Math.PI);
            float f4 = -Mth.cos(-playerPitch * ((float) Math.PI / 180F));
            float f5 = Mth.sin(-playerPitch * ((float) Math.PI / 180F));

            float f6 = f3 * f4;
            float f7 = f2 * f4;

            double distance = 16;

            Vec3 endPoint = playerEye.add((double) f6 * distance, (double) f5 * distance, (double) f7 * distance);

            if (Minecraft.getInstance().level != null) {
                HitResult rayTraceResult = Minecraft.getInstance().level.clip(new ClipContext(playerEye, endPoint, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
                if (rayTraceResult.getType() != HitResult.Type.MISS) {
                    endPoint = rayTraceResult.getLocation();
                }

                HitResult rayTraceResult1 = ProjectileUtil.getEntityHitResult(Minecraft.getInstance().level, player, playerEye, endPoint, player.getBoundingBox().inflate(16.0D), entity -> !entity.equals(player));
                if (rayTraceResult1 != null) {
                    rayTraceResult = rayTraceResult1;
                }
                if (rayTraceResult.getType() == HitResult.Type.ENTITY) {
                    @SuppressWarnings("ConstantConditions") EntityHitResult entityRayTraceResult = (EntityHitResult) rayTraceResult;

                    Entity entity = entityRayTraceResult.getEntity();
                    EntityType<? extends Entity> type = entity.getType();

                    debugText.addLeftText("translatedName", I18n.get(type.getDescriptionId()));
                    debugText.addLeftText("height", type.getHeight());
                    debugText.addLeftText("lootTable", type.getDefaultLootTable());
                    debugText.addLeftText("name", type.getDescription().getString());
                    debugText.addLeftText("registryName", type.getRegistryName());
                    debugText.addLeftText("size", type.getDimensions().width, type.getDimensions().height);
                    debugText.addLeftText("air", entity.getAirSupply());
                    debugText.addLeftText("maxAir", entity.getMaxAirSupply());
                    debugText.addLeftText("brightness", entity.getBrightness());
                    debugText.addLeftText("entityId", entity.getId());
                    debugText.addLeftText("eyeHeight", entity.getEyeHeight());
                    debugText.addLeftText("lookVec", entity.getLookAngle());
                    debugText.addLeftText("ridingEntity", entity.getVehicle());
                    debugText.addLeftText("uniqueID", entity.getUUID());
                    debugText.addLeftText("yOffset", entity.getMyRidingOffset());

                    if (entity instanceof LivingEntity) {
                        LivingEntity living = (LivingEntity) entity;

                        debugText.addRightText("health", living.getHealth());
                        debugText.addRightText("maxHealth", living.getMaxHealth());
                        debugText.addRightText("absorptionAmount", living.getAbsorptionAmount());
                        debugText.addRightText("movementSpeed", living.canSpawnSoulSpeedParticle());
                        debugText.addRightText("aiMoveSpeed", living.getSpeed());
                        debugText.addRightText("activeHand", living.getUsedItemHand());
                        debugText.addRightText("attackingEntity", living.getKillCredit());
                        debugText.addRightText("itemInUseCount", living.getUseItemRemainingTicks());
                        debugText.addRightText("lastAttackedEntity", living.getLastHurtMob());
                        debugText.addRightText("leashPosition", living.getRopeHoldPosition(mc.getFrameTime()));
                        debugText.addRightText("pose", living.getPose());
                        debugText.addRightText("scale", living.getScale());
                        debugText.addRightText("revengeTarget", living.getLastHurtByMob());
                        debugText.addRightText("totalArmorValue", living.getArmorValue());
                    } else if (entity instanceof ItemEntity) {
                        ItemEntity itemEntity = (ItemEntity) entity;

                        debugText.addRightText("age", itemEntity.getAge());
                        debugText.addRightText("item", itemEntity.getItem());
                        debugText.addRightText("leashPosition", itemEntity.getRopeHoldPosition(mc.getFrameTime()));
                        debugText.addRightText("pose", itemEntity.getPose());
                    }
                }
            }
        } else {
            debugText.addLeftText("error", "No entity targeted.");
        }

        return debugText;
    }
}
