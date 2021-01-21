package com.qsoftware.forgemod.modules.entities.objects;

import com.qsoftware.forgemod.modules.items.ModItems;
import com.qsoftware.forgemod.modules.entities.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
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

    public DynamiteEntity(EntityType<? extends DynamiteEntity> p_i50153_1_, World world) {
        super(p_i50153_1_, world);
    }

    public DynamiteEntity(World worldIn, LivingEntity throwerIn) {
        super(ModEntities.DYNAMITE.getEntityType(), throwerIn, worldIn);
    }

    public DynamiteEntity(World worldIn, double x, double y, double z) {
        super(ModEntities.DYNAMITE.getEntityType(), x, y, z, worldIn);
    }

    protected @NotNull Item getDefaultItem() {
        return ModItems.LEGENDARY_ENDER_PEARL.get();
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

        if (!this.world.isRemote && !this.removed) {
            this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), 4f, false, Explosion.Mode.BREAK);
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
            this.setShooter(null);
        }

        return super.changeDimension(server, teleporter);
    }
}
