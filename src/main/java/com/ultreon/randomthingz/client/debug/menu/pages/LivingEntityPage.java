package com.ultreon.randomthingz.client.debug.menu.pages;

import com.ultreon.randomthingz.client.debug.menu.DebugEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
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
            if (entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                list.add(new DebugEntry("health", living::getHealth));
                list.add(new DebugEntry("maxHealth", living::getMaxHealth));
                list.add(new DebugEntry("absorptionAmount", living::getAbsorptionAmount));
                list.add(new DebugEntry("movementSpeed", living::canSpawnSoulSpeedParticle));
                list.add(new DebugEntry("aiMoveSpeed", living::getSpeed));
                list.add(new DebugEntry("activeHand", living::getUsedItemHand));
                list.add(new DebugEntry("attackingEntity", living::getKillCredit));
                list.add(new DebugEntry("itemInUseCount", living::getUseItemRemainingTicks));
                list.add(new DebugEntry("lastAttackedEntity", living::getLastHurtMob));
                list.add(new DebugEntry("leashPosition", () -> living.getRopeHoldPosition(mc.getFrameTime())));
                list.add(new DebugEntry("pose", living::getPose));
                list.add(new DebugEntry("scale", living::getScale));
                list.add(new DebugEntry("revengeTarget", living::getLastHurtByMob));
                list.add(new DebugEntry("totalArmorValue", living::getArmorValue));
            } else {
                list.add(new DebugEntry("$LIVING_ENTITY$", null));
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
