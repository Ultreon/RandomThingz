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
        List<DebugEntry> entries = new ArrayList<>();
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            entries.add(new DebugEntry("$PLAYER$", null));
            return entries;
        }
        float xRot = player.getXRot();
        float yRot = player.getYRot();

        Vec3 eye = player.getEyePosition(1f);

        float f2 = Mth.cos(-yRot * ((float) Math.PI / 180f) - (float) Math.PI);
        float f3 = Mth.sin(-yRot * ((float) Math.PI / 180f) - (float) Math.PI);
        float f4 = -Mth.cos(-xRot * ((float) Math.PI / 180f));
        float f5 = Mth.sin(-xRot * ((float) Math.PI / 180f));

        float f6 = f3 * f4;
        float f7 = f2 * f4;

        double d0 = 16;

        Vec3 hitPos = eye.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level == null) {
            entries.add(new DebugEntry("$WORLD$", null));
            return entries;
        }

        HitResult blockHit = level.clip(new ClipContext(eye, hitPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if (blockHit.getType() != HitResult.Type.MISS) {
            hitPos = blockHit.getLocation();
        }

        HitResult entityHit = ProjectileUtil.getEntityHitResult(level, player, eye, hitPos, player.getBoundingBox().inflate(16.0D), entity -> !entity.equals(player));
        if (entityHit != null) {
            blockHit = entityHit;
        }
        if (blockHit.getType() == HitResult.Type.ENTITY) {
            @SuppressWarnings("ConstantConditions") EntityHitResult entityHitResult = (EntityHitResult) blockHit;

            Entity entity = entityHitResult.getEntity();
            EntityType<? extends Entity> type = entity.getType();

            entries.add(new DebugEntry("translatedName", I18n.get(type.getDescriptionId())));
            entries.add(new DebugEntry("height", type.getHeight()));
            entries.add(new DebugEntry("target", type.getDefaultLootTable()));
            entries.add(new DebugEntry("name", type.getDescription().getString()));
            entries.add(new DebugEntry("registryName", type.getRegistryName()));
            entries.add(new DebugEntry("size", getSize(type.getDimensions().width, type.getDimensions().height)));
            entries.add(new DebugEntry("air", entity.getAirSupply()));
            entries.add(new DebugEntry("maxAir", entity.getMaxAirSupply()));
            entries.add(new DebugEntry("brightness", entity.getBrightness()));
            entries.add(new DebugEntry("entityId", entity.getId()));
            entries.add(new DebugEntry("eyeHeight", entity.getEyeHeight()));
            entries.add(new DebugEntry("lookVec", entity.getLookAngle()));
            entries.add(new DebugEntry("ridingEntity", entity.getVehicle()));
            entries.add(new DebugEntry("uniqueID", entity.getUUID()));
            entries.add(new DebugEntry("yOffset", entity.getMyRidingOffset()));
        } else {
            entries.add(new DebugEntry("$ENTITY$", null));
            return entries;
        }
        return entries;
    }

    @Override
    public boolean hasRequiredComponents() {
        return Minecraft.getInstance().player != null;
    }
}
