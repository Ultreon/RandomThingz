package com.qtech.forgemod.entity;

import com.qtech.forgemod.item.common.ModItems;
import com.qtech.forgemod.entity.common.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * Legendary Ender Pearl entity class.
 *
 * @author Qboi123
 */
@SuppressWarnings({"deprecation", "unused"})
public class DynamiteEntity extends ProjectileItemEntity {

    public DynamiteEntity(EntityType<? extends DynamiteEntity> p_i50153_1_, World dimension) {
        super(p_i50153_1_, dimension);
    }

    public DynamiteEntity(World dimensionIn, LivingEntity throwerIn) {
        super(ModEntities.DYNAMITE.getEntityType(), throwerIn, dimensionIn);
    }

    public DynamiteEntity(World dimensionIn, double x, double y, double z) {
        super(ModEntities.DYNAMITE.getEntityType(), x, y, z, dimensionIn);
    }

    protected @NotNull Item getDefaultItem() {
        return ModItems.LEGENDARY_ENDER_PEARL.get();
    }

    @Override
    public @NotNull IPacket<?> getSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onImpact(@NotNull RayTraceResult result) {
        super.onImpact(result);
        Entity entity = this.getShooter();

        if (!this.dimension.isClientSided && !this.removed) {
            this.dimension.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), 4f, false, Explosion.Mode.BREAK);
            this.delete();
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        Entity entity = this.getShooter();
        if (entity instanceof PlayerEntity && !entity.isAlive()) {
            this.delete();
        } else {
            super.tick();
        }

    }

    @Nullable
    public Entity changeDimension(@NotNull ServerWorld server, net.minecraftforge.common.util.@NotNull ITeleporter teleporter) {
        Entity entity = this.getShooter();
        if (entity != null && entity.dimension.getDimensionKey() != server.getDimensionKey()) {
            this.setShooter(null);
        }

        return super.changeDimension(server, teleporter);
    }
}
