package com.ultreon.randomthingz.client.debug.menu.pages;

import com.ultreon.randomthingz.client.debug.menu.DebugEntry;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class EntityPage extends DebugPage {
    public EntityPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public final List<DebugEntry> getLinesLeft() {
        List<DebugEntry> list = new ArrayList<>();
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            list.add(new DebugEntry("$PLAYER$", null));
            return list;
        }
        float f = player.xRot;
        float f1 = player.yRot;

        Vec3 vec3d = player.getEyePosition(1.0f);

        float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float) Math.PI / 180F));

        float f6 = f3 * f4;
        float f7 = f2 * f4;

        double d0 = 16;

        Vec3 vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
        Minecraft mc = Minecraft.getInstance();
        ClientLevel dimension = mc.level;
        if (dimension == null) {
            list.add(new DebugEntry("$WORLD$", null));
            return list;
        }

        int i = 1;
        int j = 1;

        HitResult raytraceresult = dimension.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
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
            EntityType<? extends Entity> type = entity.getType();

            list.add(new DebugEntry("translatedName", I18n.get(type.getDescriptionId())));
            list.add(new DebugEntry("height", type.getHeight()));
            list.add(new DebugEntry("lootTable", type.getDefaultLootTable()));
            list.add(new DebugEntry("name", type.getDescription().getString()));
            list.add(new DebugEntry("registryName", type.getRegistryName()));
            list.add(new DebugEntry("size", getSize(type.getDimensions().width, type.getDimensions().height)));
            list.add(new DebugEntry("air", entity.getAirSupply()));
            list.add(new DebugEntry("maxAir", entity.getMaxAirSupply()));
            list.add(new DebugEntry("brightness", entity.getBrightness()));
            list.add(new DebugEntry("entityId", entity.getId()));
            list.add(new DebugEntry("eyeHeight", entity.getEyeHeight()));
            list.add(new DebugEntry("lookVec", entity.getLookAngle()));
            list.add(new DebugEntry("ridingEntity", entity.getVehicle()));
            list.add(new DebugEntry("uniqueID", entity.getUUID()));
            list.add(new DebugEntry("yOffset", entity.getMyRidingOffset()));
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
