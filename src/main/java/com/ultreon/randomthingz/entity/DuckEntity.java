package com.ultreon.randomthingz.entity;

import com.ultreon.randomthingz.entity.common.ModEntities;
import com.ultreon.randomthingz.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Duck entity class.
 *
 * @author Qboi123
 */
public class DuckEntity extends ChickenEntity {
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.BREAD);

    public DuckEntity(EntityType<? extends ChickenEntity> type, World dimensionIn) {
        super(type, dimensionIn);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 4.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
                ;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 1.4D, 160)); // Fish has chance of 40 // Todo: check correct chance.
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(6, new LookAtGoal(this, AbstractFishEntity.class, 4.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    @Override
    protected int getExperiencePoints(@NotNull PlayerEntity player) {
        return 5 + this.dimension.rand.nextInt(8);
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public DuckEntity createChild(ServerWorld world, AgeableEntity ageable) {  // createChild
        return ModEntities.DUCK.get().create(world);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ENTITY_DUCK_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_DUCK_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return ModSounds.ENTITY_DUCK_AMBIENT.get();
    }

    @Override
    protected @NotNull SoundEvent getSplashSound() {
        return SoundEvents.ENTITY_GENERIC_SPLASH;
    }
}
