package com.ultreon.randomthingz.entity;

import com.ultreon.randomthingz.common.entity.ModEntities;
import com.ultreon.randomthingz.common.item.ModItems;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Legendary Ender Pearl entity class.
 *
 * @author Qboi123
 */
@SuppressWarnings({"unused"})
public class DynamiteEntity extends ThrowableItemProjectile {

    public DynamiteEntity(EntityType<? extends DynamiteEntity> p_i50153_1_, Level dimension) {
        super(p_i50153_1_, dimension);
    }

    public DynamiteEntity(Level dimensionIn, LivingEntity throwerIn) {
        super(ModEntities.DYNAMITE.getEntityType(), throwerIn, dimensionIn);
    }

    public DynamiteEntity(Level dimensionIn, double x, double y, double z) {
        super(ModEntities.DYNAMITE.getEntityType(), x, y, z, dimensionIn);
    }

    protected @NotNull Item getDefaultItem() {
        return ModItems.LEGENDARY_ENDER_PEARL.get();
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);
        Entity entity = this.getOwner();

        if (!this.level.isClientSide && !this.isRemoved()) {
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 4f, false, Explosion.BlockInteraction.BREAK);
            this.discard();
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        Entity entity = this.getOwner();
        if (entity instanceof Player && !entity.isAlive()) {
            this.discard();
        } else {
            super.tick();
        }

    }

    @Nullable
    public Entity changeDimension(@NotNull ServerLevel server, net.minecraftforge.common.util.@NotNull ITeleporter teleporter) {
        Entity entity = this.getOwner();
        if (entity != null && entity.level.dimension() != server.dimension()) {
            this.setOwner(null);
        }

        return super.changeDimension(server, teleporter);
    }
}
