package com.qsoftware.forgemod.client.debug.pages;

import com.qsoftware.forgemod.client.debug.DebugPage;
import com.qsoftware.forgemod.client.debug.DebugText;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import org.jetbrains.annotations.NotNull;

public class TargetEntityPage extends DebugPage {
    @Override
    public @NotNull DebugText generateText() {
        DebugText debugText = new DebugText();

        if (mc.player != null) {
            PlayerEntity player = mc.player;
            float f = player.rotationPitch;
            float f1 = player.rotationYaw;

            Vector3d vec3d = player.getEyePosition(1.0F);

            float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
            float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
            float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180F));
            float f5 = MathHelper.sin(-f * ((float) Math.PI / 180F));

            float f6 = f3 * f4;
            float f7 = f2 * f4;

            double d0 = 16; // Todo: test value, if it will lag, then lower the value. ( Possible not needed ;) )

            Vector3d vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);

            if (Minecraft.getInstance().world != null) {
                RayTraceResult raytraceresult = Minecraft.getInstance().world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, player));
                if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
                    vec3d1 = raytraceresult.getHitVec();
                }

                RayTraceResult rayTraceResult1 = ProjectileHelper.rayTraceEntities(Minecraft.getInstance().world, player, vec3d, vec3d1, player.getBoundingBox().grow(16.0D), entity -> !entity.equals(player));
                if (rayTraceResult1 != null) {
                    raytraceresult = rayTraceResult1;
                }
                if (raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
                    @SuppressWarnings("ConstantConditions") EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) raytraceresult;

                    Entity entity = entityRayTraceResult.getEntity();
                    EntityType<? extends Entity> type = entity.getType();

                    debugText.addLeftText("translatedName", I18n.format(type.getTranslationKey()));
                    debugText.addLeftText("height", type.getHeight());
                    debugText.addLeftText("lootTable", type.getLootTable());
                    debugText.addLeftText("name", type.getName().getString());
                    debugText.addLeftText("registryName", type.getRegistryName());
                    debugText.addLeftText("size", type.getSize().width, type.getSize().height);
                    debugText.addLeftText("air", entity.getAir());
                    debugText.addLeftText("maxAir", entity.getMaxAir());
                    debugText.addLeftText("brightness", entity.getBrightness());
                    debugText.addLeftText("entityId", entity.getEntityId());
                    debugText.addLeftText("eyeHeight", entity.getEyeHeight());
                    debugText.addLeftText("lookVec", entity.getLookVec());
                    debugText.addLeftText("ridingEntity", entity.getRidingEntity());
                    debugText.addLeftText("uniqueID", entity.getUniqueID());
                    debugText.addLeftText("yOffset", entity.getYOffset());

                    if (entity instanceof LivingEntity) {
                        LivingEntity living = (LivingEntity) entity;

                        debugText.addRightText("health", living.getHealth());
                        debugText.addRightText("maxHealth", living.getMaxHealth());
                        debugText.addRightText("absorptionAmount", living.getAbsorptionAmount());
                        debugText.addRightText("movementSpeed", living.getMovementSpeed());
                        debugText.addRightText("aiMoveSpeed", living.getAIMoveSpeed());
                        debugText.addRightText("activeHand", living.getActiveHand());
                        debugText.addRightText("attackingEntity", living.getAttackingEntity());
                        debugText.addRightText("itemInUseCount", living.getItemInUseCount());
                        debugText.addRightText("lastAttackedEntity", living.getLastAttackedEntity());
                        debugText.addRightText("leashPosition", living.getLeashPosition(mc.getRenderPartialTicks()));
                        debugText.addRightText("pose", living.getPose());
                        debugText.addRightText("scale", living.getRenderScale());
                        debugText.addRightText("revengeTarget", living.getRevengeTarget());
                        debugText.addRightText("totalArmorValue", living.getTotalArmorValue());
                    } else if (entity instanceof ItemEntity) {
                        ItemEntity itemEntity = (ItemEntity) entity;

                        debugText.addRightText("age", itemEntity.getAge());
                        debugText.addRightText("item", itemEntity.getItem());
                        debugText.addRightText("leashPosition", itemEntity.getLeashPosition(mc.getRenderPartialTicks()));
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
