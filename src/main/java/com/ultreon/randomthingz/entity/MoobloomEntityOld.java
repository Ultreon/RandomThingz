package com.ultreon.randomthingz.entity;

import com.ultreon.randomthingz.block._common.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

/**
 * Moobloom entity class.
 *
 * @author Qboi123
 * @deprecated Use {@linkplain MoobloomEntity} instead.
 */
@Deprecated
@SuppressWarnings("ALL")
public class MoobloomEntityOld extends Cow implements Shearable, net.minecraftforge.common.IForgeShearable {
    private MobEffect hasStewEffect;
    private int effectDuration;

    /**
     * Stores the UUID of the most recent lightning bolt to strike
     */

    public MoobloomEntityOld(EntityType<? extends Cow> type, Level dimensionIn) {
        super(type, dimensionIn);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 12.0D).add(Attributes.MOVEMENT_SPEED, 0.26D);
    }

    @SuppressWarnings("unused")
    public static boolean checkMushroomSpawnRules(EntityType<MoobloomEntityOld> p_223318_0_, LevelAccessor p_223318_1_, MobSpawnType p_223318_2_, BlockPos p_223318_3_, Random p_223318_4_) {
        return p_223318_1_.getBlockState(p_223318_3_.below()).is(Blocks.MYCELIUM) && p_223318_1_.getRawBrightness(p_223318_3_, 0) > 8;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.WHEAT), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, new TargetPredicate(this)));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public float getWalkTargetValue(BlockPos pos, LevelReader dimensionIn) {
        return dimensionIn.getBlockState(pos.below()).is(Blocks.MYCELIUM) ? 10.0F : dimensionIn.getBrightness(pos) - 0.5F;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    public @NotNull
    InteractionResult mobInteract(Player p_230254_1_, @NotNull InteractionHand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
        if (itemstack.getItem() == Items.BOWL && !this.isBaby()) {
            boolean flag = false;
            ItemStack itemStack1;
            if (this.hasStewEffect != null) {
                flag = true;
                itemStack1 = new ItemStack(Items.SUSPICIOUS_STEW);
                SuspiciousStewItem.saveMobEffect(itemStack1, this.hasStewEffect, this.effectDuration);
                this.hasStewEffect = null;
                this.effectDuration = 0;
            } else {
                itemStack1 = new ItemStack(Items.MUSHROOM_STEW);
            }

            ItemStack itemStack2 = ItemUtils.createFilledResult(itemstack, p_230254_1_, itemStack1, false);
            p_230254_1_.setItemInHand(p_230254_2_, itemStack2);
            SoundEvent soundevent;
            if (flag) {
                soundevent = SoundEvents.MOOSHROOM_MILK_SUSPICIOUSLY;
            } else {
                soundevent = SoundEvents.MOOSHROOM_MILK;
            }

            this.playSound(soundevent, 1.0F, 1.0F);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else if (itemstack.getItem().is(ItemTags.SMALL_FLOWERS)) {
            if (this.hasStewEffect != null) {
                for (int i = 0; i < 2; ++i) {
                    this.level.addParticle(ParticleTypes.SMOKE, this.getX() + this.random.nextDouble() / 2.0D, this.getY(0.5D), this.getZ() + this.random.nextDouble() / 2.0D, 0.0D, this.random.nextDouble() / 5.0D, 0.0D);
                }
            } else {
                Optional<Pair<MobEffect, Integer>> optional = this.getStewEffect(itemstack);
                if (!optional.isPresent()) {
                    return InteractionResult.PASS;
                }

                Pair<MobEffect, Integer> pair = optional.get();
                if (!p_230254_1_.abilities.instabuild) {
                    itemstack.shrink(1);
                }

                for (int j = 0; j < 4; ++j) {
                    this.level.addParticle(ParticleTypes.EFFECT, this.getX() + this.random.nextDouble() / 2.0D, this.getY(0.5D), this.getZ() + this.random.nextDouble() / 2.0D, 0.0D, this.random.nextDouble() / 5.0D, 0.0D);
                }

                this.hasStewEffect = pair.getLeft();
                this.effectDuration = pair.getRight();
                this.playSound(SoundEvents.MOOSHROOM_EAT, 2.0F, 1.0F);
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(p_230254_1_, p_230254_2_);
        }
    }

    public void shear(@NotNull SoundSource category) {
        this.level.playSound(null, this, SoundEvents.MOOSHROOM_SHEAR, category, 1.0F, 1.0F);
        if (!this.level.isClientSide()) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5D), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            this.remove();
            Cow cowentity = EntityType.COW.create(this.level);
            Objects.requireNonNull(cowentity).moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
            cowentity.setHealth(this.getHealth());
            cowentity.yBodyRot = this.yBodyRot;
            if (this.hasCustomName()) {
                cowentity.setCustomName(this.getCustomName());
                cowentity.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isPersistenceRequired()) {
                cowentity.setPersistenceRequired();
            }

            cowentity.setInvulnerable(this.isInvulnerable());
            this.level.addFreshEntity(cowentity);

            for (int i = 0; i < 5; ++i) {
                this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(1.0D), this.getZ(), new ItemStack(ModBlocks.BUTTERCUP.get())));
            }
        }

    }

    public boolean readyForShearing() {
        return this.isAlive() && !this.isBaby();
    }

    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.hasStewEffect != null) {
            compound.putByte("EffectId", (byte) MobEffect.getId(this.hasStewEffect));
            compound.putInt("EffectDuration", this.effectDuration);
        }

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("EffectId", 1)) {
            this.hasStewEffect = MobEffect.byId(compound.getByte("EffectId"));
        }

        if (compound.contains("EffectDuration", 3)) {
            this.effectDuration = compound.getInt("EffectDuration");
        }

    }

    private Optional<Pair<MobEffect, Integer>> getStewEffect(ItemStack p_213443_1_) {
        Item item = p_213443_1_.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem) item).getBlock();
            if (block instanceof FlowerBlock) {
                FlowerBlock flowerblock = (FlowerBlock) block;
                return Optional.of(Pair.of(flowerblock.getSuspiciousStewEffect(), flowerblock.getEffectDuration()));
            }
        }

        return Optional.empty();
    }

    public MoobloomEntityOld getBreedOffspring(@NotNull ServerLevel p_241840_1_, @NotNull AgableMob p_241840_2_) {
//        return EntityTypeInit.MOOBLOOM.get().create(p_241840_1_);
        return null;
    }

    @Override
    public boolean isShearable(@javax.annotation.Nonnull ItemStack item, Level dimension, BlockPos pos) {
        return readyForShearing();
    }

    @javax.annotation.Nonnull
    @Override
    public java.util.List<ItemStack> onSheared(@javax.annotation.Nullable Player player, @javax.annotation.Nonnull ItemStack item, Level dimension, BlockPos pos, int fortune) {
        dimension.playSound(null, this, SoundEvents.MOOSHROOM_SHEAR, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
        if (!dimension.isClientSide()) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5D), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            this.remove();
            Cow cowentity = EntityType.COW.create(this.level);
            Objects.requireNonNull(cowentity).moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
            cowentity.setHealth(this.getHealth());
            cowentity.yBodyRot = this.yBodyRot;
            if (this.hasCustomName()) {
                cowentity.setCustomName(this.getCustomName());
                cowentity.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isPersistenceRequired()) {
                cowentity.setPersistenceRequired();
            }

            cowentity.setInvulnerable(this.isInvulnerable());
            this.level.addFreshEntity(cowentity);

            java.util.List<ItemStack> items = new java.util.ArrayList<>();
            for (int i = 0; i < 5; ++i) {
                items.add(new ItemStack(ModBlocks.BUTTERCUP.get()));
            }

            return items;
        }
        return java.util.Collections.emptyList();
    }

    @Deprecated
    static class TargetPredicate implements Predicate<LivingEntity> {
        private final MoobloomEntityOld parentEntity;

        public TargetPredicate(MoobloomEntityOld moobloom) {
            this.parentEntity = moobloom;
        }

        public boolean test(@Nullable LivingEntity p_test_1_) {
            return (p_test_1_ instanceof Zombie) && p_test_1_.distanceToSqr(this.parentEntity) > 9.0D;
        }
    }
}
