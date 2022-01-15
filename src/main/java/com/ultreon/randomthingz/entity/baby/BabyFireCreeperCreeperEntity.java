package com.ultreon.randomthingz.entity.baby;

import com.ultreon.randomthingz.common.item.ModItemsAlt;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

/**
 * Baby creeper entity class.
 *
 * @author QTech Community.
 */
public class BabyFireCreeperCreeperEntity extends Creeper implements IBabyEntity {

    private static final EntityDataAccessor<Boolean> IS_CHILD = SynchedEntityData.defineId(BabyFireCreeperCreeperEntity.class, EntityDataSerializers.BOOLEAN);

    public BabyFireCreeperCreeperEntity(EntityType<BabyFireCreeperCreeperEntity> type, Level dimension) {
        super(type, dimension);
        setBaby(true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(IS_CHILD, false);
    }

    @Override
    public boolean isBaby() {
        return getEntityData().get(IS_CHILD);
    }

    @Override
    public void setBaby(boolean child) {
        setChild(IS_CHILD, child);
    }

    @Override
    public void onSyncedDataUpdated(@Nonnull EntityDataAccessor<?> key) {
        if (IS_CHILD.equals(key)) {
            refreshDimensions();
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    protected int getExperienceReward(@Nonnull Player player) {
        if (isBaby()) {
            xpReward = (int) (xpReward * 2.5F);
        }
        return super.getExperienceReward(player);
    }

    @Override
    public double getMyRidingOffset() {
        return isBaby() ? 0 : super.getMyRidingOffset();
    }

    @Override
    protected float getStandingEyeHeight(@Nonnull Pose pose, @Nonnull EntityDimensions size) {
        return isBaby() ? 0.88F : super.getStandingEyeHeight(pose, size);
    }

    /**
     * Modify vanilla's explode method to half the explosion strength of baby creepers, and charged baby creepers
     */
    @Override
    public void explodeCreeper() {
        if (!level.isClientSide) {
            Explosion.BlockInteraction mode = ForgeEventFactory.getMobGriefingEvent(level, this) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
            float f = isPowered() ? 1 : 0.5F;
            dead = true;
            level.explode(this, getX(), getY(), getZ(), explosionRadius * f, mode);
            remove();
            spawnLingeringCloud();
        }
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(ModItemsAlt.BABY_CREEPER_SPAWN_EGG.asItem());
    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}