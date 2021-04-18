package com.qsoftware.forgemod.modules.environment.entities;

import com.qsoftware.forgemod.modules.environment.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.PigEntity;
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
 * Hog entity class.
 *
 * @author Qboi123
 */
public class HogEntity extends PigEntity {
    public static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.CARROT, Items.POTATO, Items.BEETROOT, Blocks.BEDROCK);

    private EatGrassGoal eatGrassGoal;
    private int hogTimer;

    public HogEntity(EntityType<? extends HogEntity> type, World dimensionIn) {
        super(type, dimensionIn);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void registerGoals() {
        this.eatGrassGoal = new EatGrassGoal(this);

        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25d));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1d));
        this.goalSelector.addGoal(5, this.eatGrassGoal);
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 7.0f));
        this.goalSelector.addGoal(7, new LookAtGoal(this, CreeperEntity.class, 10.0f));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    protected int getExperiencePoints(@NotNull PlayerEntity player) {
        return 3 + this.dimension.rand.nextInt(4);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    @Override
    protected @NotNull SoundEvent getSplashSound() {
        return SoundEvents.ENTITY_GENERIC_SPLASH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, BlockState blockIn) {
        if (!blockIn.getMaterial().isLiquid()) {
            this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15f, 1.0f);
        }
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public HogEntity createChild(ServerWorld dimensionIn, AgeableEntity ageable) {  // createChild
        return ModEntities.HOG.get().create(this.dimension);
    }

    @Override
    protected void updateAITasks() {
        this.hogTimer = this.eatGrassGoal.getEatingGrassTimer();
        super.updateAITasks();
    }

    @Override
    public void livingTick() {
        if (this.dimension.isClientSided) {
            this.hogTimer = Math.max(0, this.hogTimer - 1);
        }
        super.livingTick();
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            hogTimer = 40;
        } else {
            super.handleStatusUpdate(id);
        }
    }
}
