package com.ultreon.randomthingz.entity;

import com.ultreon.randomthingz.common.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Hog entity class.
 *
 * @author Qboi123
 */
public class HogEntity extends Pig {
    public static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.CARROT, Items.POTATO, Items.BEETROOT, Blocks.BEDROCK);

    private EatBlockGoal eatGrassGoal;
    private int hogTimer;

    public HogEntity(EntityType<? extends HogEntity> type, Level dimensionIn) {
        super(type, dimensionIn);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void registerGoals() {
        this.eatGrassGoal = new EatBlockGoal(this);

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25d));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1d));
        this.goalSelector.addGoal(5, this.eatGrassGoal);
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 7.0f));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Creeper.class, 10.0f));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    protected int getExperienceReward(@NotNull Player player) {
        return 3 + this.level.random.nextInt(4);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PIG_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PIG_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.PIG_HURT;
    }

    @Override
    protected @NotNull SoundEvent getSwimSplashSound() {
        return SoundEvents.GENERIC_SPLASH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, BlockState blockIn) {
        if (!blockIn.getMaterial().isLiquid()) {
            this.playSound(SoundEvents.PIG_STEP, 0.15f, 1.0f);
        }
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public HogEntity getBreedOffspring(ServerLevel dimensionIn, AgableMob ageable) {  // createChild
        return ModEntities.HOG.get().create(this.level);
    }

    @Override
    protected void customServerAiStep() {
        this.hogTimer = this.eatGrassGoal.getEatAnimationTick();
        super.customServerAiStep();
    }

    @Override
    public void aiStep() {
        if (this.level.isClientSide) {
            this.hogTimer = Math.max(0, this.hogTimer - 1);
        }
        super.aiStep();
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 10) {
            hogTimer = 40;
        } else {
            super.handleEntityEvent(id);
        }
    }
}
