package com.ultreon.randomthingz.entity.baby;

import com.ultreon.randomthingz.init.ModItemsAlt;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

/**
 * Baby enderman entity class.
 *
 * @author Ultreon Team.
 */
public class BabyIceEnderman extends EnderMan implements BabyMob {

    private static final EntityDataAccessor<Boolean> IS_CHILD = SynchedEntityData.defineId(BabyIceEnderman.class, EntityDataSerializers.BOOLEAN);

    public BabyIceEnderman(EntityType<BabyIceEnderman> type, Level dimension) {
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
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        if (IS_CHILD.equals(key)) {
            refreshDimensions();
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    protected int getExperienceReward(@NotNull Player player) {
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
    protected float getStandingEyeHeight(@NotNull Pose pose, @NotNull EntityDimensions size) {
        return this.isBaby() ? 1.3F : super.getStandingEyeHeight(pose, size);
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(ModItemsAlt.BABY_ENDERMAN_SPAWN_EGG.asItem());
    }

    @NotNull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}