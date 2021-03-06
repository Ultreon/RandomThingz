package com.ultreon.randomthingz.entity;

import com.ultreon.randomthingz.init.ModBlocks;
import com.ultreon.randomthingz.common.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Optional;

/**
 * Moobloom entity.
 *
 * @author Qboi123
 */
@SuppressWarnings("deprecation")
public class Moobloom extends Cow implements Shearable, net.minecraftforge.common.IForgeShearable {
    private static final EntityDataAccessor<String> MOOBLOOM_TYPE = SynchedEntityData.defineId(Moobloom.class, EntityDataSerializers.STRING);
    private MobEffect hasStewEffect;
    private int effectDuration;

    public Moobloom(EntityType<? extends Moobloom> type, Level dimensionIn) {
        super(type, dimensionIn);
    }

    /**
     * @param pos         the block position.
     * @param dimensionIn the dimension reader.
     * @return ...
     */
    public float getWalkTargetValue(BlockPos pos, LevelReader dimensionIn) {
        return dimensionIn.getBlockState(pos.below()).is(Blocks.MYCELIUM) ? 10f : dimensionIn.getBrightness(pos) - .5f;
    }

    /**
     * Register data entries in the data manager.
     */
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MOOBLOOM_TYPE, Moobloom.Type.BUTTERCUP.name);
    }

    /**
     * On initial spawn.
     *
     * @param dimensionIn  the dimension where to spawn.
     * @param difficultyIn the difficulty.
     * @param reason       the spawn reason.
     * @param spawnDataIn  the spawn data.
     * @param dataTag      the data tag.
     * @return the requested spawning entity.
     */
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor dimensionIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (reason == MobSpawnType.SPAWN_EGG || reason == MobSpawnType.MOB_SUMMONED) {
            this.setMoobloomType(Type.values()[this.random.nextInt(Type.values().length)]);
        } else {
            Biome biome = dimensionIn.getBiome(blockPosition());
            if (biome.getBiomeCategory() == Biome.BiomeCategory.FOREST || biome.getBiomeCategory() == Biome.BiomeCategory.PLAINS) {
                int index = random.nextInt(15);
                if (dataTag != null && dataTag.contains("MoobloomType", 3)) {
                    index = dataTag.getInt("MoobloomType");
                }

                switch (index) {
                    case 1 -> this.setMoobloomType(Type.OXEYE_DAISY);
                    case 2 -> this.setMoobloomType(Type.ALLIUM);
                    case 3 -> this.setMoobloomType(Type.AZURE_BLUET);
                    case 4 -> this.setMoobloomType(Type.BLUE_ORCHID);
                    case 5 -> this.setMoobloomType(Type.CORNFLOWER);
                    case 6 -> this.setMoobloomType(Type.DANDELION);
                    case 7 -> this.setMoobloomType(Type.ORANGE_TULIP);
                    case 8 -> this.setMoobloomType(Type.PINK_TULIP);
                    case 9 -> this.setMoobloomType(Type.RED_TULIP);
                    case 10 -> this.setMoobloomType(Type.WHITE_TULIP);
                    case 11 -> this.setMoobloomType(Type.ROSE_BUSH);
                    case 12 -> this.setMoobloomType(Type.PEONY);
                    case 13 -> this.setMoobloomType(Type.LILAC);
                    case 14 -> this.setMoobloomType(Type.POPPY);
                    case 0 -> this.setMoobloomType(Type.BUTTERCUP);
                }
            } else if (biome.getBiomeCategory() == Biome.BiomeCategory.NETHER) {
                switch (random.nextInt(3)) {
                    case 1 -> this.setMoobloomType(Type.CRIMSON_FUNGUS);
                    case 2 -> this.setMoobloomType(Type.WITHER_ROSE);
                    default -> this.setMoobloomType(Type.WARPED_FUNGUS);
                }
            } else if (biome.getBiomeCategory() == Biome.BiomeCategory.JUNGLE) {
                this.setMoobloomType(Type.BAMBOO);
            } else if (biome.getBiomeCategory() == Biome.BiomeCategory.SWAMP) {
                this.setMoobloomType(Type.BLUE_ORCHID);
            } else {
                this.setMoobloomType(Type.BUTTERCUP);
            }
        }
        return super.finalizeSpawn(dimensionIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    /**
     * On entity right click.
     *
     * @param playerIn the player that right clicks.
     * @param handIn   the hand used by the player.
     * @return the action result (type).
     */
    @Override
    public InteractionResult mobInteract(Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (itemstack.getItem() == Items.BOWL && !this.isBaby()) {
            boolean flag = false;
            ItemStack stack1;
            if (this.hasStewEffect != null) {
                flag = true;
                stack1 = new ItemStack(Items.SUSPICIOUS_STEW);
                SuspiciousStewItem.saveMobEffect(stack1, this.hasStewEffect, this.effectDuration);
                this.hasStewEffect = null;
                this.effectDuration = 0;
            } else {
                stack1 = new ItemStack(Items.MUSHROOM_STEW);
            }

            ItemStack stack2 = ItemUtils.createFilledResult(itemstack, playerIn, stack1, false);
            playerIn.setItemInHand(handIn, stack2);
            SoundEvent soundevent;
            if (flag) {
                soundevent = SoundEvents.MOOSHROOM_MILK_SUSPICIOUSLY;
            } else {
                soundevent = SoundEvents.MOOSHROOM_MILK;
            }

            this.playSound(soundevent, 1f, 1f);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else if (this.getMoobloomType().getRenderState().getBlock().asItem().getClass().isAssignableFrom(itemstack.getItem().getClass())) {
            if (this.hasStewEffect != null) {
                for (int i = 0; i < 2; ++i) {
                    this.level.addParticle(ParticleTypes.SMOKE, this.getX() + this.random.nextDouble() / 2.0D, this.getY(0.5D), this.getZ() + this.random.nextDouble() / 2.0D, 0.0D, this.random.nextDouble() / 5.0D, 0.0D);
                }
            } else {
                Optional<Pair<MobEffect, Integer>> optional = this.getStewEffect(itemstack);
                if (optional.isEmpty()) {
                    return InteractionResult.PASS;
                }

                Pair<MobEffect, Integer> pair = optional.get();
                if (!playerIn.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                for (int j = 0; j < 4; ++j) {
                    this.level.addParticle(ParticleTypes.EFFECT, this.getX() + this.random.nextDouble() / 2.0D, this.getY(0.5D), this.getZ() + this.random.nextDouble() / 2.0D, 0.0D, this.random.nextDouble() / 5.0D, 0.0D);
                }

                this.hasStewEffect = pair.getLeft();
                this.effectDuration = pair.getRight();
                this.playSound(SoundEvents.MOOSHROOM_EAT, 2f, 1f);
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(playerIn, handIn);
        }
    }

    /**
     * Shear method.
     *
     * @param category the sound category for shearing.
     */
    @Override
    @SuppressWarnings("deprecation")
    public void shear(SoundSource category) {
        this.level.playSound(null, this, SoundEvents.MOOSHROOM_SHEAR, category, 1f, 1f);
        if (!this.level.isClientSide()) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5D), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            this.discard();
            Cow cow = EntityType.COW.create(this.level);
            assert cow != null;
            cow.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            cow.setHealth(this.getHealth());
            cow.yBodyRot = this.yBodyRot;
            if (this.hasCustomName()) {
                cow.setCustomName(this.getCustomName());
                cow.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isPersistenceRequired()) {
                cow.setPersistenceRequired();
            }

            cow.setInvulnerable(this.isInvulnerable());
            this.level.addFreshEntity(cow);

            for (int i = 0; i < 5; ++i) {
                this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(1.0D), this.getZ(), new ItemStack(this.getMoobloomType().renderState.getBlock())));
            }
        }

    }

    /**
     * Check if ready to be sheared.
     *
     * @return true if the mob can be sheared atm.
     */
    @SuppressWarnings("deprecation")
    public boolean readyForShearing() {
        return this.isAlive() && !this.isBaby();
    }

    /**
     * Write additional data.
     *
     * @param compound the nbt compound.
     */
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Type", this.getMoobloomType().name);
        if (this.hasStewEffect != null) {
            compound.putByte("EffectId", (byte) MobEffect.getId(this.hasStewEffect));
            compound.putInt("EffectDuration", this.effectDuration);
        }

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setMoobloomType(Moobloom.Type.getTypeByName(compound.getString("Type")));
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
            if (block instanceof FlowerBlock flowerblock) {
                return Optional.of(Pair.of(flowerblock.getSuspiciousStewEffect(), flowerblock.getEffectDuration()));
            }
        }

        return Optional.empty();
    }

    private void setMoobloomType(Moobloom.Type typeIn) {
        this.entityData.set(MOOBLOOM_TYPE, typeIn.name);
    }

    public Moobloom.Type getMoobloomType() {
        return Moobloom.Type.getTypeByName(this.entityData.get(MOOBLOOM_TYPE));
    }

    public Moobloom getBreedOffspring(ServerLevel p_241840_1_, AgeableMob p_241840_2_) {
        Moobloom moobloom = ModEntities.MOOBLOOM.get().create(p_241840_1_);
        assert moobloom != null;
        moobloom.setMoobloomType(this.getOffspringType((Moobloom) p_241840_2_));
        return moobloom;
    }

    private Moobloom.Type getOffspringType(Moobloom p_213445_1_) {
        Moobloom.Type currentEntityType = this.getMoobloomType();
        Moobloom.Type overrideEntityType = p_213445_1_.getMoobloomType();
        Moobloom.Type finalEntityType;
        if (currentEntityType == overrideEntityType && this.random.nextInt(1024) == 0) {
            finalEntityType = currentEntityType == Moobloom.Type.SUNFLOWER ? Moobloom.Type.BUTTERCUP : Moobloom.Type.SUNFLOWER;
        } else {
            finalEntityType = this.random.nextBoolean() ? currentEntityType : overrideEntityType;
        }

        return finalEntityType;
    }

    @Override
    public boolean isShearable(@NotNull ItemStack item, Level dimension, BlockPos pos) {
        return readyForShearing();
    }

    @NotNull
    @Override
    public java.util.List<ItemStack> onSheared(@Nullable Player player, @NotNull ItemStack item, Level dimension, BlockPos pos, int fortune) {
        dimension.playSound(null, this, SoundEvents.MOOSHROOM_SHEAR, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1f, 1f);
        if (!dimension.isClientSide()) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5D), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            this.discard();
            Cow cow = EntityType.COW.create(this.level);
            assert cow != null;
            cow.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            cow.setHealth(this.getHealth());
            cow.yBodyRot = this.yBodyRot;
            if (this.hasCustomName()) {
                cow.setCustomName(this.getCustomName());
                cow.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isPersistenceRequired()) {
                cow.setPersistenceRequired();
            }

            cow.setInvulnerable(this.isInvulnerable());
            this.level.addFreshEntity(cow);

            java.util.List<ItemStack> items = new java.util.ArrayList<>();
            for (int i = 0; i < 5; ++i) {
                items.add(new ItemStack(this.getMoobloomType().renderState.getBlock()));
            }

            return items;
        }
        return Collections.emptyList();
    }


    public enum Type {
        BUTTERCUP("buttercup", 0, ModBlocks.BUTTERCUP.asBlockState()),
        SUNFLOWER("sunflower", 1, ModBlocks.SMALL_SUNFLOWER.asBlockState()),
        POPPY("poppy", 2, Blocks.POPPY.defaultBlockState()),
        ALLIUM("allium", 3, Blocks.ALLIUM.defaultBlockState()),
        BLUE_ORCHID("blue_orchid", 4, Blocks.BLUE_ORCHID.defaultBlockState()),
        CORNFLOWER("cornflower", 5, Blocks.CORNFLOWER.defaultBlockState()),
        AZURE_BLUET("azure_bluet", 6, Blocks.AZURE_BLUET.defaultBlockState()),
        WITHER_ROSE("wither_rose", 7, Blocks.WITHER_ROSE.defaultBlockState()),
        DANDELION("dandelion", 8, Blocks.DANDELION.defaultBlockState()),
        OXEYE_DAISY("oxeye_daisy", 9, Blocks.OXEYE_DAISY.defaultBlockState()),
        ORANGE_TULIP("orange_tulip", 10, Blocks.ORANGE_TULIP.defaultBlockState()),
        PINK_TULIP("pink_tulip", 11, Blocks.PINK_TULIP.defaultBlockState()),
        RED_TULIP("red_tulip", 12, Blocks.RED_TULIP.defaultBlockState()),
        WHITE_TULIP("white_tulip", 13, Blocks.WHITE_TULIP.defaultBlockState()),
        ROSE_BUSH("rose_bush", 14, ModBlocks.SMALL_ROSE_BUSH.asBlockState()),
        PEONY("peony", 15, ModBlocks.SMALL_PEONY.asBlockState()),
        LILAC("lilac", 16, ModBlocks.SMALL_LILAC.asBlockState()),
        CRIMSON_FUNGUS("crimson_fungus", 17, Blocks.CRIMSON_FUNGUS.defaultBlockState()),
        WARPED_FUNGUS("warped_fungus", 18, Blocks.WARPED_FUNGUS.defaultBlockState()),
        BAMBOO("bamboo", 19, Blocks.BAMBOO.defaultBlockState()),
        CACTUS("cactus", 20, Blocks.CACTUS.defaultBlockState()),
        CHORUS("chorus", 21, Blocks.CHORUS_FLOWER.defaultBlockState());

        private final String name;
        private final int id;
        private final BlockState renderState;

        Type(String nameIn, int id, BlockState renderStateIn) {
            this.name = nameIn;
            this.id = id;
            this.renderState = renderStateIn;
        }

        @Nullable
        public static Type getFromBlock(Block block) {
            for (Type type : values()) {
                Block fromState = type.renderState.getBlock();
                if (block.equals(fromState)) {
                    return type;
                }
            }
            return null;
        }

        public int getId() {
            return id;
        }


        /**
         * A block state that is rendered on the back of the mushroom.
         */
        @OnlyIn(Dist.CLIENT)
        public BlockState getRenderState() {
            return this.renderState;
        }

        public String getFilename() {
            if (id == 0) {
                return "moobloom.png";
            }

            return "moobloom" + id + ".png";
        }

        private static Moobloom.Type getTypeByName(String nameIn) {
            for (Moobloom.Type moobloomType : values()) {
                if (moobloomType.name.equals(nameIn)) {
                    return moobloomType;
                }
            }

            return BUTTERCUP;
        }
    }
}
