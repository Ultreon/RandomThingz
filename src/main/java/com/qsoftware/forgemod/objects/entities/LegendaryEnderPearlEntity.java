package com.qsoftware.forgemod.objects.entities;

import com.qsoftware.forgemod.init.ItemInit;
import com.qsoftware.forgemod.init.types.EntityTypeInit;
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

    public LegendaryEnderPearlEntity(EntityType<? extends LegendaryEnderPearlEntity> p_i50153_1_, World world) {
        super(p_i50153_1_, world);
    }

    public LegendaryEnderPearlEntity(World worldIn, LivingEntity throwerIn) {
        super(EntityTypeInit.LEGENDARY_ENDER_PEARL.getEntityType(), throwerIn, worldIn);
    }

    public LegendaryEnderPearlEntity(World worldIn, double x, double y, double z) {
        super(EntityTypeInit.LEGENDARY_ENDER_PEARL.getEntityType(), x, y, z, worldIn);
    }

    protected @NotNull Item getDefaultItem() {
        return ItemInit.LEGENDARY_ENDER_PEARL.get();
    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onEntityHit(@NotNull EntityRayTraceResult p_213868_1_) {
        super.onEntityHit(p_213868_1_);
        p_213868_1_.getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_234616_v_()), 0.0F);
    }

    @Override
    public @NotNull IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onImpact(@NotNull RayTraceResult result) {
        super.onImpact(result);
        Entity entity = this.func_234616_v_();

        for(int i = 0; i < 32; ++i) {
            this.world.addParticle(ParticleTypes.PORTAL, this.getPosX(), this.getPosY() + this.rand.nextDouble() * 2.0D, this.getPosZ(), this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());
        }

        if (!this.world.isRemote && !this.removed) {
            if (entity instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entity;
                if (serverplayerentity.connection.getNetworkManager().isChannelOpen() && serverplayerentity.world == this.world && !serverplayerentity.isSleeping()) {
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

            this.remove();
        }

    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        Entity entity = this.func_234616_v_();
        if (entity instanceof PlayerEntity && !entity.isAlive()) {
            this.remove();
        } else {
            super.tick();
        }

    }

    @Nullable
    public Entity changeDimension(@NotNull ServerWorld server, net.minecraftforge.common.util.@NotNull ITeleporter teleporter) {
        Entity entity = this.func_234616_v_();
        if (entity != null && entity.world.getDimensionKey() != server.getDimensionKey()) {
//            this.setShooter((Entity)null);
            entity.changeDimension(server, teleporter);
        }

        return super.changeDimension(server, teleporter);
    }
}
