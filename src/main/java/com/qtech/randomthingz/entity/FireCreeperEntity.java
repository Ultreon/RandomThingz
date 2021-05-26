package com.qtech.randomthingz.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * Fire creeper entity class.
 *
 * @author Qboi123
 */
public class FireCreeperEntity extends CreeperEntity {
    public FireCreeperEntity(EntityType<? extends FireCreeperEntity> type, World dimensionIn) {
        super(type, dimensionIn);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 12.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    protected int getExperiencePoints(@NotNull PlayerEntity player) {
        return 5 + this.dimension.rand.nextInt(8);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CREEPER_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }

    @Override
    protected @NotNull SoundEvent getSplashSound() {
        return SoundEvents.ENTITY_GENERIC_SPLASH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {

    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
    }

    @Override
    public void livingTick() {
        super.livingTick();
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        super.handleStatusUpdate(id);
    }
}
