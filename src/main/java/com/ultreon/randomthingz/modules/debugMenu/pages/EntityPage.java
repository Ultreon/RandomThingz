package com.ultreon.randomthingz.modules.debugMenu.pages;

import com.ultreon.randomthingz.modules.debugMenu.DebugEntry;
import com.ultreon.randomthingz.modules.debugMenu.DebugPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class EntityPage extends DebugPage {
    public EntityPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public final List<DebugEntry> getLinesLeft() {
        List<DebugEntry> list = new ArrayList<>();
        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            list.add(new DebugEntry("$PLAYER$", null));
            return list;
        }
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;

        Vector3d vec3d = player.getEyePosition(1.0F);

        float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float) Math.PI / 180F));

        float f6 = f3 * f4;
        float f7 = f2 * f4;

        double d0 = 16;

        Vector3d vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
        Minecraft mc = Minecraft.getInstance();
        ClientWorld dimension = mc.dimension;
        if (dimension == null) {
            list.add(new DebugEntry("$WORLD$", null));
            return list;
        }

        int i = 1;
        int j = 1;

        RayTraceResult raytraceresult = dimension.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, player));
        if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
            vec3d1 = raytraceresult.getHitVec();
        }

        RayTraceResult rayTraceResult1 = ProjectileHelper.rayTraceEntities(dimension, player, vec3d, vec3d1, player.getBoundingBox().grow(16.0D), entity -> !entity.equals(player));
        if (rayTraceResult1 != null) {
            raytraceresult = rayTraceResult1;
        }
        if (raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
            @SuppressWarnings("ConstantConditions") EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) raytraceresult;

            Entity entity = entityRayTraceResult.getEntity();
            EntityType<? extends Entity> type = entity.getType();

            list.add(new DebugEntry("translatedName", I18n.format(type.getTranslationId())));
            list.add(new DebugEntry("height", type.getHeight()));
            list.add(new DebugEntry("lootTable", type.getLootTable()));
            list.add(new DebugEntry("name", type.getName().getString()));
            list.add(new DebugEntry("registryName", type.getRegistryName()));
            list.add(new DebugEntry("size", getSize(type.getSize().width, type.getSize().height)));
            list.add(new DebugEntry("air", entity.getAir()));
            list.add(new DebugEntry("maxAir", entity.getMaxAir()));
            list.add(new DebugEntry("brightness", entity.getBrightness()));
            list.add(new DebugEntry("entityId", entity.getEntityId()));
            list.add(new DebugEntry("eyeHeight", entity.getEyeHeight()));
            list.add(new DebugEntry("lookVec", entity.getLookVec()));
            list.add(new DebugEntry("ridingEntity", entity.getRidingEntity()));
            list.add(new DebugEntry("uniqueID", entity.getUniqueID()));
            list.add(new DebugEntry("yOffset", entity.getYOffset()));
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
