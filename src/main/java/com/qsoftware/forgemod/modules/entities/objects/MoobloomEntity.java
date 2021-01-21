package com.qsoftware.forgemod.modules.entities.objects;

import java.util.Optional;
import java.util.UUID;

import com.qsoftware.forgemod.modules.blocks.ModBlocks;
import com.qsoftware.forgemod.modules.entities.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;

/**
 * Moobloom entity.
 * 
 * @author Qboi123
 */
public class MoobloomEntity extends CowEntity implements IShearable, net.minecraftforge.common.IForgeShearable {
   private static final DataParameter<String> MOOBLOOM_TYPE = EntityDataManager.createKey(MoobloomEntity.class, DataSerializers.STRING);
   private Effect hasStewEffect;
   private int effectDuration;
   /** Stores the UUID of the most recent lightning bolt to strike */
   private UUID lightningUUID;

   public MoobloomEntity(EntityType<? extends MoobloomEntity> type, World worldIn) {
      super(type, worldIn);
   }

   /**
    * 
    * 
    * @param pos the block position.
    * @param worldIn the world reader.
    * @return ...
    */
   public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
      return worldIn.getBlockState(pos.down()).isIn(Blocks.MYCELIUM) ? 10.0F : worldIn.getBrightness(pos) - 0.5F;
   }

//   public static boolean func_223318_c(EntityType<MoobloomEntity> p_223318_0_, IWorld p_223318_1_, SpawnReason p_223318_2_, BlockPos p_223318_3_, Random p_223318_4_) {
//      return p_223318_1_.getBlockState(p_223318_3_.down()).isIn(Blocks.MYCELIUM) && p_223318_1_.getLightSubtracted(p_223318_3_, 0) > 8;
//   }

//   public void func_241841_a(ServerWorld p_241841_1_, LightningBoltEntity p_241841_2_) {
//      UUID uuid = p_241841_2_.getUniqueID();
//      if (!uuid.equals(this.lightningUUID)) {
//         this.setMoobloomType(this.getMoobloomType() == MooshroomEntity.Type.RED ? MooshroomEntity.Type.BROWN : MooshroomEntity.Type.RED);
//         this.lightningUUID = uuid;
////         this.playSound(SoundEvents.ENTITY_MOOSHROOM_CONVERT, 2.0F, 1.0F);
//      }
//
//   }

   /**
    * Register data entries in the data manager.
    */
   protected void registerData() {
      super.registerData();
      this.dataManager.register(MOOBLOOM_TYPE, MoobloomEntity.Type.BUTTERCUP.name);
   }

   /**
    * On initial spawn.
    * 
    * @param worldIn the world where to spawn.
    * @param difficultyIn the difficulty.
    * @param reason the spawn reason.
    * @param spawnDataIn the spawn data.
    * @param dataTag the data tag.
    * @return the requested spawning entity.
    */
   @Nullable
   public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
      if (reason == SpawnReason.SPAWN_EGG || reason == SpawnReason.MOB_SUMMONED) {
         this.setMoobloomType(Type.values()[this.rand.nextInt(Type.values().length)]);
      } else {
         Biome biome = worldIn.getBiome(getPosition());
         if (biome.getCategory() == Biome.Category.FOREST || biome.getCategory() == Biome.Category.PLAINS) {
            switch (rand.nextInt(15)) {
               case 1: {
                  this.setMoobloomType(Type.OXEYE_DAISY);
                  break;
               } case 2: {
                  this.setMoobloomType(Type.ALLIUM);
                  break;
               } case 3: {
                  this.setMoobloomType(Type.AZURE_BLUET);
                  break;
               } case 4: {
                  this.setMoobloomType(Type.BLUE_ORCHID);
                  break;
               } case 5: {
                  this.setMoobloomType(Type.CORNFLOWER);
                  break;
               } case 6: {
                  this.setMoobloomType(Type.DANDELION);
                  break;
               } case 7: {
                  this.setMoobloomType(Type.ORANGE_TULIP);
                  break;
               } case 8: {
                  this.setMoobloomType(Type.PINK_TULIP);
                  break;
               } case 9: {
                  this.setMoobloomType(Type.RED_TULIP);
                  break;
               } case 10: {
                  this.setMoobloomType(Type.WHITE_TULIP);
                  break;
               } case 11: {
                  this.setMoobloomType(Type.ROSE_BUSH);
                  break;
               } case 12: {
                  this.setMoobloomType(Type.PEONY);
                  break;
               } case 13: {
                  this.setMoobloomType(Type.LILAC);
                  break;
               } case 14: {
                  this.setMoobloomType(Type.POPPY);
                  break;
               } default: {
                  this.setMoobloomType(Type.BUTTERCUP);
                  break;
               }
            }
         } else if (biome.getCategory() == Biome.Category.NETHER) {
            switch (rand.nextInt(3)) {
               case 1: {
                  this.setMoobloomType(Type.CRIMSON_FUNGUS);
                  break;
               } case 2: {
                  this.setMoobloomType(Type.WITHER_ROSE);
                  break;
               } default: {
                  this.setMoobloomType(Type.WARPED_FUNGUS);
                  break;
               }
            }
         } else if (biome.getCategory() == Biome.Category.JUNGLE) {
            this.setMoobloomType(Type.BAMBOO);
         } else if (biome.getCategory() == Biome.Category.SWAMP) {
            this.setMoobloomType(Type.BLUE_ORCHID);
         } else {
            this.setMoobloomType(Type.BUTTERCUP);
         }
      };
      return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
   }

   /**
    * On entity right click.
    * 
    * @param playerIn the player that right clicks.
    * @param handIn the hand used by the player.
    * @return the action result (type).
    */
   @Override
   public ActionResultType func_230254_b_(PlayerEntity playerIn, Hand handIn) {
      ItemStack itemstack = playerIn.getHeldItem(handIn);
      if (itemstack.getItem() == Items.BOWL && !this.isChild()) {
         boolean flag = false;
         ItemStack itemstack1;
         if (this.hasStewEffect != null) {
            flag = true;
            itemstack1 = new ItemStack(Items.SUSPICIOUS_STEW);
            SuspiciousStewItem.addEffect(itemstack1, this.hasStewEffect, this.effectDuration);
            this.hasStewEffect = null;
            this.effectDuration = 0;
         } else {
            itemstack1 = new ItemStack(Items.MUSHROOM_STEW);
         }

         ItemStack itemstack2 = DrinkHelper.fill(itemstack, playerIn, itemstack1, false);
         playerIn.setHeldItem(handIn, itemstack2);
         SoundEvent soundevent;
         if (flag) {
            soundevent = SoundEvents.ENTITY_MOOSHROOM_SUSPICIOUS_MILK;
         } else {
            soundevent = SoundEvents.ENTITY_MOOSHROOM_MILK;
         }

         this.playSound(soundevent, 1.0F, 1.0F);
         return ActionResultType.func_233537_a_(this.world.isRemote);
      } else if (this.getMoobloomType().getRenderState().getBlock().asItem().getClass().isAssignableFrom(itemstack.getItem().getClass())) {
         if (this.hasStewEffect != null) {
            for(int i = 0; i < 2; ++i) {
               this.world.addParticle(ParticleTypes.SMOKE, this.getPosX() + this.rand.nextDouble() / 2.0D, this.getPosYHeight(0.5D), this.getPosZ() + this.rand.nextDouble() / 2.0D, 0.0D, this.rand.nextDouble() / 5.0D, 0.0D);
            }
         } else {
            Optional<Pair<Effect, Integer>> optional = this.getStewEffect(itemstack);
            if (!optional.isPresent()) {
               return ActionResultType.PASS;
            }

            Pair<Effect, Integer> pair = optional.get();
            if (!playerIn.abilities.isCreativeMode) {
               itemstack.shrink(1);
            }

            for(int j = 0; j < 4; ++j) {
               this.world.addParticle(ParticleTypes.EFFECT, this.getPosX() + this.rand.nextDouble() / 2.0D, this.getPosYHeight(0.5D), this.getPosZ() + this.rand.nextDouble() / 2.0D, 0.0D, this.rand.nextDouble() / 5.0D, 0.0D);
            }

            this.hasStewEffect = pair.getLeft();
            this.effectDuration = pair.getRight();
            this.playSound(SoundEvents.ENTITY_MOOSHROOM_EAT, 2.0F, 1.0F);
         }

         return ActionResultType.func_233537_a_(this.world.isRemote);
      } else {
         return super.func_230254_b_(playerIn, handIn);
      }
   }

   /**
    * Shear method.
    *
    * @param category
    */
   @Override
   @SuppressWarnings("deprecation")
   public void shear(SoundCategory category) {
      this.world.playMovingSound(null, this, SoundEvents.ENTITY_MOOSHROOM_SHEAR, category, 1.0F, 1.0F);
      if (!this.world.isRemote()) {
         ((ServerWorld)this.world).spawnParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosYHeight(0.5D), this.getPosZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
         this.remove();
         CowEntity cowentity = EntityType.COW.create(this.world);
         assert cowentity != null;
         cowentity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
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

         for(int i = 0; i < 5; ++i) {
            this.world.addEntity(new ItemEntity(this.world, this.getPosX(), this.getPosYHeight(1.0D), this.getPosZ(), new ItemStack(this.getMoobloomType().renderState.getBlock())));
         }
      }

   }

   /**
    * Check if shearable.
    *
    * @return true if shearable.
    */
   @SuppressWarnings("deprecation")
   public boolean isShearable() {
      return this.isAlive() && !this.isChild();
   }

   /**
    * Write additional data.
    *
    * @param compound the nbt compound.
    */
   public void writeAdditional(CompoundNBT compound) {
      super.writeAdditional(compound);
      compound.putString("Type", this.getMoobloomType().name);
      if (this.hasStewEffect != null) {
         compound.putByte("EffectId", (byte)Effect.getId(this.hasStewEffect));
         compound.putInt("EffectDuration", this.effectDuration);
      }

   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      this.setMoobloomType(MoobloomEntity.Type.getTypeByName(compound.getString("Type")));
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
         Block block = ((BlockItem)item).getBlock();
         if (block instanceof FlowerBlock) {
            FlowerBlock flowerblock = (FlowerBlock)block;
            return Optional.of(Pair.of(flowerblock.getStewEffect(), flowerblock.getStewEffectDuration()));
         }
      }

      return Optional.empty();
   }

   private void setMoobloomType(MoobloomEntity.Type typeIn) {
      this.dataManager.set(MOOBLOOM_TYPE, typeIn.name);
   }

   public MoobloomEntity.Type getMoobloomType() {
      return MoobloomEntity.Type.getTypeByName(this.dataManager.get(MOOBLOOM_TYPE));
   }

   public MoobloomEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
      MoobloomEntity mooshroomentity = ModEntities.MOOBLOOM.get().create(p_241840_1_);
      assert mooshroomentity != null;
      mooshroomentity.setMoobloomType(this.func_213445_a((MoobloomEntity)p_241840_2_));
      return mooshroomentity;
   }

   private MoobloomEntity.Type func_213445_a(MoobloomEntity p_213445_1_) {
      MoobloomEntity.Type mooshroomentity$type = this.getMoobloomType();
      MoobloomEntity.Type mooshroomentity$type1 = p_213445_1_.getMoobloomType();
      MoobloomEntity.Type mooshroomentity$type2;
      if (mooshroomentity$type == mooshroomentity$type1 && this.rand.nextInt(1024) == 0) {
         mooshroomentity$type2 = mooshroomentity$type == MoobloomEntity.Type.SUNFLOWER ? MoobloomEntity.Type.BUTTERCUP : MoobloomEntity.Type.SUNFLOWER;
      } else {
         mooshroomentity$type2 = this.rand.nextBoolean() ? mooshroomentity$type : mooshroomentity$type1;
      }

      return mooshroomentity$type2;
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
         ((ServerWorld)this.world).spawnParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosYHeight(0.5D), this.getPosZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
         this.remove();
         CowEntity cowentity = EntityType.COW.create(this.world);
         assert cowentity != null;
         cowentity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
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
            items.add(new ItemStack(this.getMoobloomType().renderState.getBlock()));
         }

         return items;
      }
      return java.util.Collections.emptyList();
   }


   public enum Type {
      BUTTERCUP("buttercup", 0, ModBlocks.BUTTERCUP.asBlockState()),
      SUNFLOWER("sunflower", 1, ModBlocks.SMALL_SUNFLOWER.asBlockState()),
      POPPY("poppy", 2, Blocks.POPPY.getDefaultState()),
      ALLIUM("allium", 3, Blocks.ALLIUM.getDefaultState()),
      BLUE_ORCHID("blue_orchid", 4, Blocks.BLUE_ORCHID.getDefaultState()),
      CORNFLOWER("cornflower", 5, Blocks.CORNFLOWER.getDefaultState()),
      AZURE_BLUET("azure_bluet", 6, Blocks.AZURE_BLUET.getDefaultState()),
      WITHER_ROSE("wither_rose", 7, Blocks.WITHER_ROSE.getDefaultState()),
      DANDELION("dandelion", 8, Blocks.DANDELION.getDefaultState()),
      OXEYE_DAISY("oxeye_daisy", 9, Blocks.OXEYE_DAISY.getDefaultState()),
      ORANGE_TULIP("orange_tulip", 10, Blocks.ORANGE_TULIP.getDefaultState()),
      PINK_TULIP("pink_tulip", 11, Blocks.PINK_TULIP.getDefaultState()),
      RED_TULIP("red_tulip", 12, Blocks.RED_TULIP.getDefaultState()),
      WHITE_TULIP("white_tulip", 13, Blocks.WHITE_TULIP.getDefaultState()),
      ROSE_BUSH("rose_bush", 14, ModBlocks.SMALL_ROSE_BUSH.asBlockState()),
      PEONY("peony", 15, ModBlocks.SMALL_PEONY.asBlockState()),
      LILAC("lilac", 16, ModBlocks.SMALL_LILAC.asBlockState()),
      CRIMSON_FUNGUS("crimson_fungus", 17, Blocks.CRIMSON_FUNGUS.getDefaultState()),
      WARPED_FUNGUS("warped_fungus", 18, Blocks.WARPED_FUNGUS.getDefaultState()),
      BAMBOO("bamboo", 19, Blocks.BAMBOO.getDefaultState()),
      CACTUS("cactus", 20, Blocks.CACTUS.getDefaultState()),
      CHORUS("chorus", 21, Blocks.CHORUS_FLOWER.getDefaultState());

      private final String name;
      private final int id;
      private final BlockState renderState;

      Type(String nameIn, int id, BlockState renderStateIn) {
         this.name = nameIn;
         this.id = id;
         this.renderState = renderStateIn;
      }

      public int getId() {
         return id;
      }



      /**
       * A block state that is rendered on the back of the mooshroom.
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

      private static MoobloomEntity.Type getTypeByName(String nameIn) {
         for(MoobloomEntity.Type mooshroomentity$type : values()) {
            if (mooshroomentity$type.name.equals(nameIn)) {
               return mooshroomentity$type;
            }
         }

         return BUTTERCUP;
      }
   }
}
