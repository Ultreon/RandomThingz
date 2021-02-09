package com.qsoftware.forgemod.modules.environment.entities;

import com.qsoftware.forgemod.modules.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
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
 * @deprecated Use {@link MoobloomEntity} instead.
 */
@Deprecated
@SuppressWarnings("ALL")
public class MoobloomEntityOld extends CowEntity implements IShearable, net.minecraftforge.common.IForgeShearable {
    private Effect hasStewEffect;
    private int effectDuration;

    /**
     * Stores the UUID of the most recent lightning bolt to strike
     */

    public MoobloomEntityOld(EntityType<? extends CowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 12.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.26D);
    }

    @SuppressWarnings("unused")
    public static boolean func_223318_c(EntityType<MoobloomEntityOld> p_223318_0_, IWorld p_223318_1_, SpawnReason p_223318_2_, BlockPos p_223318_3_, Random p_223318_4_) {
        return p_223318_1_.getBlockState(p_223318_3_.down()).isIn(Blocks.MYCELIUM) && p_223318_1_.getLightSubtracted(p_223318_3_, 0) > 8;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(Items.WHEAT), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, new TargetPredicate(this)));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
        return worldIn.getBlockState(pos.down()).isIn(Blocks.MYCELIUM) ? 10.0F : worldIn.getBrightness(pos) - 0.5F;
    }

    protected void registerData() {
        super.registerData();
    }

    public @NotNull ActionResultType func_230254_b_(PlayerEntity p_230254_1_, @NotNull Hand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
        if (itemstack.getItem() == Items.BOWL && !this.isChild()) {
            boolean flag = false;
            ItemStack itemStack1;
            if (this.hasStewEffect != null) {
                flag = true;
                itemStack1 = new ItemStack(Items.SUSPICIOUS_STEW);
                SuspiciousStewItem.addEffect(itemStack1, this.hasStewEffect, this.effectDuration);
                this.hasStewEffect = null;
                this.effectDuration = 0;
            } else {
                itemStack1 = new ItemStack(Items.MUSHROOM_STEW);
            }

            ItemStack itemStack2 = DrinkHelper.fill(itemstack, p_230254_1_, itemStack1, false);
            p_230254_1_.setHeldItem(p_230254_2_, itemStack2);
            SoundEvent soundevent;
            if (flag) {
                soundevent = SoundEvents.ENTITY_MOOSHROOM_SUSPICIOUS_MILK;
            } else {
                soundevent = SoundEvents.ENTITY_MOOSHROOM_MILK;
            }

            this.playSound(soundevent, 1.0F, 1.0F);
            return ActionResultType.func_233537_a_(this.world.isRemote);
        } else if (itemstack.getItem().isIn(ItemTags.SMALL_FLOWERS)) {
            if (this.hasStewEffect != null) {
                for (int i = 0; i < 2; ++i) {
                    this.world.addParticle(ParticleTypes.SMOKE, this.getPosX() + this.rand.nextDouble() / 2.0D, this.getPosYHeight(0.5D), this.getPosZ() + this.rand.nextDouble() / 2.0D, 0.0D, this.rand.nextDouble() / 5.0D, 0.0D);
                }
            } else {
                Optional<Pair<Effect, Integer>> optional = this.getStewEffect(itemstack);
                if (!optional.isPresent()) {
                    return ActionResultType.PASS;
                }

                Pair<Effect, Integer> pair = optional.get();
                if (!p_230254_1_.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                }

                for (int j = 0; j < 4; ++j) {
                    this.world.addParticle(ParticleTypes.EFFECT, this.getPosX() + this.rand.nextDouble() / 2.0D, this.getPosYHeight(0.5D), this.getPosZ() + this.rand.nextDouble() / 2.0D, 0.0D, this.rand.nextDouble() / 5.0D, 0.0D);
                }

                this.hasStewEffect = pair.getLeft();
                this.effectDuration = pair.getRight();
                this.playSound(SoundEvents.ENTITY_MOOSHROOM_EAT, 2.0F, 1.0F);
            }

            return ActionResultType.func_233537_a_(this.world.isRemote);
        } else {
            return super.func_230254_b_(p_230254_1_, p_230254_2_);
        }
    }

    public void shear(@NotNull SoundCategory category) {
        this.world.playMovingSound(null, this, SoundEvents.ENTITY_MOOSHROOM_SHEAR, category, 1.0F, 1.0F);
        if (!this.world.isRemote()) {
            ((ServerWorld) this.world).spawnParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosYHeight(0.5D), this.getPosZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            this.remove();
            CowEntity cowentity = EntityType.COW.create(this.world);
            Objects.requireNonNull(cowentity).setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            cowentity.setHealth(this.getHealth());
            cowentity.renderYawOffset = this.renderYawOffset;
            if (this.hasCustomName()) {
                cowentity.setCustomName(this.getCustomName());
                cowentity.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isNoDespawnRequired()) {
                cowentity.enablePersistence();
            }

            cowentity.setInvulnerable(this.isInvulnerable());
            this.world.addEntity(cowentity);

            for (int i = 0; i < 5; ++i) {
                this.world.addEntity(new ItemEntity(this.world, this.getPosX(), this.getPosYHeight(1.0D), this.getPosZ(), new ItemStack(ModBlocks.BUTTERCUP.get())));
            }
        }

    }

    public boolean isShearable() {
        return this.isAlive() && !this.isChild();
    }

    public void writeAdditional(@NotNull CompoundNBT compound) {
        super.writeAdditional(compound);
        if (this.hasStewEffect != null) {
            compound.putByte("EffectId", (byte) Effect.getId(this.hasStewEffect));
            compound.putInt("EffectDuration", this.effectDuration);
        }

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(@NotNull CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("EffectId", 1)) {
            this.hasStewEffect = Effect.get(compound.getByte("EffectId"));
        }

        if (compound.contains("EffectDuration", 3)) {
            this.effectDuration = compound.getInt("EffectDuration");
        }

    }

    private Optional<Pair<Effect, Integer>> getStewEffect(ItemStack p_213443_1_) {
        Item item = p_213443_1_.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem) item).getBlock();
            if (block instanceof FlowerBlock) {
                FlowerBlock flowerblock = (FlowerBlock) block;
                return Optional.of(Pair.of(flowerblock.getStewEffect(), flowerblock.getStewEffectDuration()));
            }
        }

        return Optional.empty();
    }

    public MoobloomEntityOld func_241840_a(@NotNull ServerWorld p_241840_1_, @NotNull AgeableEntity p_241840_2_) {
//        return EntityTypeInit.MOOBLOOM.get().create(p_241840_1_);
        return null;
    }

    @Override
    public boolean isShearable(@javax.annotation.Nonnull ItemStack item, World world, BlockPos pos) {
        return isShearable();
    }

    @javax.annotation.Nonnull
    @Override
    public java.util.List<ItemStack> onSheared(@javax.annotation.Nullable PlayerEntity player, @javax.annotation.Nonnull ItemStack item, World world, BlockPos pos, int fortune) {
        world.playMovingSound(null, this, SoundEvents.ENTITY_MOOSHROOM_SHEAR, player == null ? SoundCategory.BLOCKS : SoundCategory.PLAYERS, 1.0F, 1.0F);
        if (!world.isRemote()) {
            ((ServerWorld) this.world).spawnParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosYHeight(0.5D), this.getPosZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            this.remove();
            CowEntity cowentity = EntityType.COW.create(this.world);
            Objects.requireNonNull(cowentity).setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            cowentity.setHealth(this.getHealth());
            cowentity.renderYawOffset = this.renderYawOffset;
            if (this.hasCustomName()) {
                cowentity.setCustomName(this.getCustomName());
                cowentity.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isNoDespawnRequired()) {
                cowentity.enablePersistence();
            }

            cowentity.setInvulnerable(this.isInvulnerable());
            this.world.addEntity(cowentity);

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
            return (p_test_1_ instanceof ZombieEntity) && p_test_1_.getDistanceSq(this.parentEntity) > 9.0D;
        }
    }
}
