package com.qtech.forgemod.modules.environment.entities;

import com.qtech.forgemod.modules.items.ModItems;
import com.qtech.forgemod.modules.environment.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
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
public class LegendaryEnderPearlEntity extends ProjectileItemEntity {

    public LegendaryEnderPearlEntity(EntityType<? extends LegendaryEnderPearlEntity> p_i50153_1_, World dimension) {
        super(p_i50153_1_, dimension);
    }

    public LegendaryEnderPearlEntity(World dimensionIn, LivingEntity throwerIn) {
        super(ModEntities.LEGENDARY_ENDER_PEARL.getEntityType(), throwerIn, dimensionIn);
    }

    public LegendaryEnderPearlEntity(World dimensionIn, double x, double y, double z) {
        super(ModEntities.LEGENDARY_ENDER_PEARL.getEntityType(), x, y, z, dimensionIn);
    }

    protected @NotNull Item getDefaultItem() {
        return ModItems.LEGENDARY_ENDER_PEARL.get();
    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onEntityHit(@NotNull EntityRayTraceResult p_213868_1_) {
        super.onEntityHit(p_213868_1_);
        p_213868_1_.getEntity().attack(DamageSource.causeThrownDamage(this, this.getShooter()), 0.0F);
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

        for (int i = 0; i < 32; ++i) {
            this.dimension.addParticle(ParticleTypes.PORTAL, this.getPosX(), this.getPosY() + this.rand.nextDouble() * 2.0D, this.getPosZ(), this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());
        }

        if (!this.dimension.isClientSided && !this.removed) {
            if (entity instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) entity;
                if (serverplayerentity.connection.getNetworkManager().isChannelOpen() && serverplayerentity.dimension == this.dimension && !serverplayerentity.isSleeping()) {
                    net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(serverplayerentity, this.getPosX(), this.getPosY(), this.getPosZ(), 5.0F);
                    if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) { // Don't indent to lower patch size
                        if (entity.isPassenger()) {
                            entity.stopRiding();
                        }

                        entity.setPositionAndUpdate(event.getTargetX(), event.getTargetY(), event.getTargetZ());
                        entity.fallDistance = 0.0F;
                    } //Forge: End
                }
            } else if (entity != null) {
                entity.setPositionAndUpdate(this.getPosX(), this.getPosY(), this.getPosZ());
                entity.fallDistance = 0.0F;
            }

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
