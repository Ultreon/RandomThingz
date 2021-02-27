package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.js.CommonScriptJSUtils;
import com.qsoftware.forgemod.script.js.ServerScriptJSUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

import java.util.UUID;

@SuppressWarnings({"unused", "deprecation"})
public class ServerPlayer extends Player implements Formattable {
    final ServerPlayerEntity wrapper;

    public ServerPlayer(UUID uuid) {
        this(ServerScriptJSUtils.getServer().getPlayerList().getPlayerByUUID(uuid));
    }

    public ServerPlayer(String name) {
        this(ServerScriptJSUtils.getServer().getPlayerList().getPlayerByUsername(name));
    }

    @Override
    public ServerWorld getWorld() {
        return new ServerWorld(wrapper.getServerWorld());
    }

    public String getPlayerIP() {
        return this.wrapper.getPlayerIP();
    }
    public String getLanguage() {
        return this.wrapper.getLanguage();
    }

    public void setSpectatingEntity(Entity entity) {
        this.wrapper.setSpectatingEntity(entity.wrapper);
    }

    public Entity getSpectatingEntity() {
        return getCorrectEntity(wrapper.getSpectatingEntity());
    }

    net.minecraft.world.server.ServerWorld getMcWorld() {
        return this.wrapper.getServerWorld();
    }

    ServerPlayerEntity getWrapper() {
        return this.wrapper;
    }

    public void setExpLevel(int level) {
        this.wrapper.setExperienceLevel(level);
    }

    protected ServerPlayer(ServerPlayerEntity player) {
        super(player);
        this.wrapper = player;
    }

    public static ServerPlayer fromName(String name) {
        return new ServerPlayer(ServerScriptJSUtils.getServer().getPlayerList().getPlayerByUsername(name));
    }

    public static ServerPlayer fromUUID(String uuid) {
        return new ServerPlayer(ServerScriptJSUtils.getServer().getPlayerList().getPlayerByUUID(UUID.fromString(uuid)));
    }
    public Entity getEntityLookingAt() {
        ServerPlayerEntity player = this.wrapper;
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

        int i = 1;
        int j = 1;

        if (player.world != null) {
            RayTraceResult raytraceresult = player.world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, player));
            if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
                vec3d1 = raytraceresult.getHitVec();
            }

            RayTraceResult rayTraceResult1 = ProjectileHelper.rayTraceEntities(player.world, player, vec3d, vec3d1, player.getBoundingBox().grow(16.0D), entity -> !entity.equals(player));
            if (rayTraceResult1 != null) {
                raytraceresult = rayTraceResult1;
            }
            if (raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
                @SuppressWarnings("ConstantConditions") EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) raytraceresult;
                return getCorrectEntity(entityRayTraceResult.getEntity());
            }
        }

        return null;
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("ServerPlayer", getName());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("ServerPlayer", getName());
    }
}
