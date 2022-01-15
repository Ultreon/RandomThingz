package com.ultreon.randomthingz.entity.baby;

import com.ultreon.randomthingz.common.item.ModItemsAlt;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Baby stray entity class.
 *
 * @author QTech Community.
 */
public class BabyStrayEntity extends Stray implements IBabyEntity {

    private static final EntityDataAccessor<Boolean> IS_CHILD = SynchedEntityData.defineId(BabyStrayEntity.class, EntityDataSerializers.BOOLEAN);

    public BabyStrayEntity(EntityType<BabyStrayEntity> type, Level dimension) {
        super(type, dimension);
        setBaby(true);
    }

    //Copy of stray spawn restrictions
    public static boolean spawnRestrictions(EntityType<BabyStrayEntity> type, ServerLevelAccessor dimension, MobSpawnType reason, BlockPos pos, Random random) {
        return checkMonsterSpawnRules(type, dimension, reason, pos, random) && (reason == MobSpawnType.SPAWNER || dimension.canSeeSky(pos));
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
        return this.isBaby() ? 0.93F : super.getStandingEyeHeight(pose, size);
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(ModItemsAlt.BABY_STRAY_SPAWN_EGG.asItem());
    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}