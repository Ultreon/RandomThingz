package com.qsoftware.forgemod.objects.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
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

public class FireCreeperEntity extends CreeperEntity {
    public FireCreeperEntity(EntityType<? extends CreeperEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    protected int getExperiencePoints(@NotNull PlayerEntity player) {
        return 5 + this.world.rand.nextInt(8);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ENDERMAN_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ENDERMAN_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_ENDERMAN_HURT;
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
