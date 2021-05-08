package com.qtech.randomthingz.block.fluid;

import com.qtech.randomthingz.block.common.ModBlocks;
import com.qtech.randomthingz.block.fluid.common.ModFluids;
import com.qtech.randomthingz.item.common.ModItems;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particles.IParticleData;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidAttributes;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class OilFluid extends FlowingFluid {
   @Override
   public Fluid getFlowingFluid() {
      return ModFluids.FLOWING_OIL;
   }

   @Override
   public Fluid getStillFluid() {
      return ModFluids.OIL;
   }

   @Override
   public Item getFilledBucket() {
      return ModItems.OIL_BUCKET.get();
   }

   @Override
   public void randomTick(World dimension, BlockPos pos, FluidState state, Random random) {

   }

   @Override
   protected FluidAttributes createAttributes() {
      return FluidAttributes.builder(
              new ResourceLocation("randomthingz:blocks/oil_still"),
              new ResourceLocation("randomthingz:blocks/oil_flowing"))
              .translationKey("block.randomthingz.oil")
              .luminosity(0).density(5_000).viscosity(10_000).temperature(0)
              .sound(SoundEvents.ITEM_BUCKET_FILL, SoundEvents.ITEM_BUCKET_EMPTY)
              .build(this);
   }

   private boolean isSurroundingBlockFlammable(IWorldReader dimensionIn, BlockPos pos) {
      return false;
   }

   private boolean canBlockBurn(IWorldReader dimensionIn, BlockPos pos) {
      return false;
   }

   @Override
   protected int getLevelDecreasePerBlock(IWorldReader dimensionIn) {
      return 2;
   }

   /**
    * Todo: add particle for oil.
    */
   @Nullable
   @OnlyIn(Dist.CLIENT)
   @Override
   public IParticleData getDripParticleData() {
//      return ParticleTypes.DRIPPING_LAVA;
      return null;
   }

   @Override
   protected void beforeReplacingBlock(IWorld dimensionIn, BlockPos pos, BlockState state) {
      this.triggerEffects(dimensionIn, pos);
   }

   @Override
   public int getSlopeFindDistance(IWorldReader dimensionIn) {
      return dimensionIn.getDimensionType().isUltrawarm() ? 4 : 2;
   }

   @Override
   public BlockState getBlockState(FluidState state) {
      return ModBlocks.OIL.get().getDefaultState().with(FlowingFluidBlock.LEVEL, getLevelFromState(state));
   }

   @Override
   public boolean isEquivalentTo(Fluid fluidIn) {
      return fluidIn == ModFluids.OIL || fluidIn == ModFluids.FLOWING_OIL;
   }

   @Override
   public boolean canDisplace(FluidState fluidState, IBlockReader blockReader, BlockPos pos, Fluid fluid, Direction direction) {
      return false;
   }

   @Override
   public int getTickRate(IWorldReader p_205569_1_) {
      return 30;
   }

   @Override
   public int func_215667_a(World dimension, BlockPos pos, FluidState p_215667_3_, FluidState p_215667_4_) {
      int i = this.getTickRate(dimension);
      if (!p_215667_3_.isEmpty() && !p_215667_4_.isEmpty() && !p_215667_3_.get(FALLING) && !p_215667_4_.get(FALLING) && p_215667_4_.getActualHeight(dimension, pos) > p_215667_3_.getActualHeight(dimension, pos) && dimension.getRandom().nextInt(4) != 0) {
         i *= 4;
      }

      return i;
   }

   private void triggerEffects(IWorld dimension, BlockPos pos) {
      dimension.playEvent(1501, pos, 0);
   }

   @Override
   protected boolean canSourcesMultiply() {
      return false;
   }

   @Override
   protected void flowInto(IWorld dimensionIn, BlockPos pos, BlockState blockStateIn, Direction direction, FluidState fluidStateIn) {
      super.flowInto(dimensionIn, pos, blockStateIn, direction, fluidStateIn);
   }

   protected boolean ticksRandomly() {
      return true;
   }

   protected float getExplosionResistance() {
      return 100.0F;
   }

   public static class Flowing extends OilFluid {
      protected void fillStateContainer(StateContainer.Builder<Fluid, FluidState> builder) {
         super.fillStateContainer(builder);
         builder.add(LEVEL_1_8);
      }

      public int getLevel(FluidState state) {
         return state.get(LEVEL_1_8);
      }

      public boolean isSource(FluidState state) {
         return false;
      }
   }

   public static class Source extends OilFluid {
      public int getLevel(FluidState state) {
         return 8;
      }

      public boolean isSource(FluidState state) {
         return true;
      }
   }
}
