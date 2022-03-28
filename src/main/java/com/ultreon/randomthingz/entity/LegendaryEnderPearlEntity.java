package com.ultreon.randomthingz.entity;

import com.ultreon.randomthingz.common.entity.ModEntities;
import com.ultreon.randomthingz.init.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Legendary Ender Pearl entity class.
 *
 * @author Qboi123
 */
@SuppressWarnings({"unused"})
public class LegendaryEnderPearlEntity extends ThrowableItemProjectile {

    public LegendaryEnderPearlEntity(EntityType<? extends LegendaryEnderPearlEntity> p_i50153_1_, Level dimension) {
        super(p_i50153_1_, dimension);
    }

    public LegendaryEnderPearlEntity(Level dimensionIn, LivingEntity throwerIn) {
        super(ModEntities.LEGENDARY_ENDER_PEARL.getEntityType(), throwerIn, dimensionIn);
    }

    public LegendaryEnderPearlEntity(Level dimensionIn, double x, double y, double z) {
        super(ModEntities.LEGENDARY_ENDER_PEARL.getEntityType(), x, y, z, dimensionIn);
    }

    protected @NotNull
    Item getDefaultItem() {
        return ModItems.LEGENDARY_ENDER_PEARL.get();
    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onHitEntity(@NotNull EntityHitResult p_213868_1_) {
        super.onHitEntity(p_213868_1_);
        p_213868_1_.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0f);
    }

    @Override
    public @NotNull
    Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);
        Entity entity = this.getOwner();

        for (int i = 0; i < 32; ++i) {
            this.level.addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0D, this.getZ(), this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
        }

        if (!this.level.isClientSide && !this.isRemoved()) {
            if (entity instanceof ServerPlayer player) {
                if (player.connection.getConnection().isConnected() && player.level == this.level && !player.isSleeping()) {
                    EntityTeleportEvent event = new EntityTeleportEvent(player, this.getX(), this.getY(), this.getZ());
                    if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) { // Don't indent to lower patch size
                        if (entity.isPassenger()) {
                            entity.stopRiding();
                        }

                        entity.teleportTo(event.getTargetX(), event.getTargetY(), event.getTargetZ());
                        entity.fallDistance = 0f;
                    } //Forge: End
                }
            } else if (entity != null) {
                entity.teleportTo(this.getX(), this.getY(), this.getZ());
                entity.fallDistance = 0f;
            }

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
