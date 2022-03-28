package com.ultreon.randomthingz.block.fluid;

import com.ultreon.randomthingz.init.ModBlocks;
import com.ultreon.randomthingz.block.fluid.common.ModFluids;
import com.ultreon.randomthingz.init.ModItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidAttributes;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class OilFluid extends FlowingFluid {
    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_OIL;
    }

    @Override
    public Fluid getSource() {
        return ModFluids.OIL;
    }

    @Override
    public Item getBucket() {
        return ModItems.OIL_BUCKET.get();
    }

    @Override
    public void randomTick(Level dimension, BlockPos pos, FluidState state, Random random) {

    }

    @Override
    protected FluidAttributes createAttributes() {
        return FluidAttributes.builder(
                        new ResourceLocation("randomthingz:block/oil_still"),
                        new ResourceLocation("randomthingz:block/oil_flowing"))
                .translationKey("block.randomthingz.oil")
                .luminosity(0).density(5_000).viscosity(10_000).temperature(0)
                .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
                .build(this);
    }

    private boolean isSurroundingBlockFlammable(LevelReader dimensionIn, BlockPos pos) {
        return false;
    }

    private boolean canBlockBurn(LevelReader dimensionIn, BlockPos pos) {
        return false;
    }

    @Override
    protected int getDropOff(LevelReader dimensionIn) {
        return 2;
    }

    /**
     * Todo: add particle for oil.
     */
    @Nullable
    @OnlyIn(Dist.CLIENT)
    @Override
    public ParticleOptions getDripParticle() {
//      return ParticleTypes.DRIPPING_LAVA;
        return null;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor dimensionIn, BlockPos pos, BlockState state) {
        this.triggerEffects(dimensionIn, pos);
    }

    @Override
    public int getSlopeFindDistance(LevelReader dimensionIn) {
        return dimensionIn.dimensionType().ultraWarm() ? 4 : 2;
    }

    @Override
    public BlockState createLegacyBlock(FluidState state) {
        return ModBlocks.OIL.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
    }

    @Override
    public boolean isSame(Fluid fluidIn) {
        return fluidIn == ModFluids.OIL || fluidIn == ModFluids.FLOWING_OIL;
    }

    @Override
    public boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockReader, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    public int getTickDelay(LevelReader p_205569_1_) {
        return 30;
    }

    @Override
    public int getSpreadDelay(Level dimension, BlockPos pos, FluidState p_215667_3_, FluidState p_215667_4_) {
        int i = this.getTickDelay(dimension);
        if (!p_215667_3_.isEmpty() && !p_215667_4_.isEmpty() && !p_215667_3_.getValue(FALLING) && !p_215667_4_.getValue(FALLING) && p_215667_4_.getHeight(dimension, pos) > p_215667_3_.getHeight(dimension, pos) && dimension.getRandom().nextInt(4) != 0) {
            i *= 4;
        }

        return i;
    }

    private void triggerEffects(LevelAccessor dimension, BlockPos pos) {
        dimension.levelEvent(1501, pos, 0);
    }

    @Override
    protected boolean canConvertToSource() {
        return false;
    }

    @Override
    protected void spreadTo(LevelAccessor dimensionIn, BlockPos pos, BlockState blockStateIn, Direction direction, FluidState fluidStateIn) {
        super.spreadTo(dimensionIn, pos, blockStateIn, direction, fluidStateIn);
    }

    protected boolean isRandomlyTicking() {
        return true;
    }

    protected float getExplosionResistance() {
        return 100f;
    }

    public static class Flowing extends OilFluid {
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Source extends OilFluid {
        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }
}
