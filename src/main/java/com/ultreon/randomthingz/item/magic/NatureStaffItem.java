package com.ultreon.randomthingz.item.magic;

import com.ultreon.randomthingz.init.ModCreativeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * Nature staff item class.
 *
 * @author Qboi123
 */
public class NatureStaffItem extends Item {
    public NatureStaffItem() {
        super(new Item.Properties().tab(ModCreativeTabs.SPECIALS).rarity(Rarity.RARE));
    }

    @Deprecated //Forge: Use Player/Hand version
    public static boolean applyGrowth(ItemStack stack, Level dimensionIn, BlockPos pos) {
        if (dimensionIn instanceof net.minecraft.server.level.ServerLevel)
            return applyGrowth(stack, dimensionIn, pos, net.minecraftforge.common.util.FakePlayerFactory.getMinecraft((net.minecraft.server.level.ServerLevel) dimensionIn));
        return false;
    }

    public static boolean applyGrowth(ItemStack stack, Level dimensionIn, BlockPos pos, net.minecraft.world.entity.player.Player player) {
        BlockState blockstate = dimensionIn.getBlockState(pos);
        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, dimensionIn, pos, blockstate, stack);
        if (hook != 0) return hook > 0;
        if (blockstate.getBlock() instanceof BonemealableBlock block) {
            if (block.isValidBonemealTarget(dimensionIn, pos, blockstate, dimensionIn.isClientSide)) {
                if (dimensionIn instanceof ServerLevel) {
                    if (block.isBonemealSuccess(dimensionIn, dimensionIn.random, pos, blockstate)) {
                        block.performBonemeal((ServerLevel) dimensionIn, dimensionIn.random, pos, blockstate);
                    }
                }

                return true;
            }
        }

        return false;
    }

    @SuppressWarnings("unused")
    public static boolean growWaterPlant(ItemStack stack, Level level, BlockPos pos, @Nullable Direction side) {
        if (level.getBlockState(pos).is(Blocks.WATER) && level.getFluidState(pos).getAmount() == 8) {
            if (level instanceof ServerLevel) {
                Random random = level.getRandom();

                label80:
                for (int i = 0; i < 128; ++i) {
                    BlockPos blockpos = pos;
                    BlockState blockState = Blocks.SEAGRASS.defaultBlockState();

                    for (int j = 0; j < i / 16; ++j) {
                        blockpos = blockpos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                        if (level.getBlockState(blockpos).isCollisionShapeFullBlock(level, blockpos)) {
                            continue label80;
                        }
                    }

                    Optional<ResourceKey<Biome>> optional = level.getBiomeName(blockpos);
                    if (Objects.equals(optional, Optional.of(Biomes.WARM_OCEAN)) || Objects.equals(optional, Optional.of(Biomes.DEEP_LUKEWARM_OCEAN))) {
                        if (i == 0 && side != null && side.getAxis().isHorizontal()) {
                            blockState = BlockTags.WALL_CORALS.getRandomElement(level.random).defaultBlockState().setValue(BaseCoralWallFanBlock.FACING, side);
                        } else if (random.nextInt(4) == 0) {
                            blockState = BlockTags.UNDERWATER_BONEMEALS.getRandomElement(random).defaultBlockState();
                        }
                    }

                    if (blockState.is(BlockTags.WALL_CORALS)) {
                        for (int k = 0; !blockState.canSurvive(level, blockpos) && k < 4; ++k) {
                            blockState = blockState.setValue(BaseCoralWallFanBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random));
                        }
                    }

                    if (blockState.canSurvive(level, blockpos)) {
                        BlockState blockState1 = level.getBlockState(blockpos);
                        if (blockState1.is(Blocks.WATER) && level.getFluidState(blockpos).getAmount() == 8) {
                            level.setBlock(blockpos, blockState, 3);
                        } else if (blockState1.is(Blocks.SEAGRASS) && random.nextInt(10) == 0) {
                            ((BonemealableBlock) Blocks.SEAGRASS).performBonemeal((ServerLevel) level, random, blockpos, blockState1);
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void spawnParticles(LevelAccessor level, BlockPos pos, int data) {
        if (data == 0) {
            data = 15;
        }

        BlockState blockstate = level.getBlockState(pos);
        if (!blockstate.isAir()) {
            double d0 = 0.5D;
            double d1;
            if (blockstate.is(Blocks.WATER)) {
                data *= 3;
                d1 = 1.0D;
                d0 = 3.0D;
            } else if (blockstate.isSolidRender(level, pos)) {
                pos = pos.above();
                data *= 3;
                d0 = 3.0D;
                d1 = 1.0D;
            } else {
                d1 = blockstate.getShape(level, pos).max(Direction.Axis.Y);
            }

            level.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);

            for (int i = 0; i < data; ++i) {
                double d2 = level.getRandom().nextGaussian() * 0.02D;
                double d3 = level.getRandom().nextGaussian() * 0.02D;
                double d4 = level.getRandom().nextGaussian() * 0.02D;
                double d5 = 0.5D - d0;
                double d6 = (double) pos.getX() + d5 + level.getRandom().nextDouble() * d0 * 2.0D;
                double d7 = (double) pos.getY() + level.getRandom().nextDouble() * d1;
                double d8 = (double) pos.getZ() + d5 + level.getRandom().nextDouble() * d0 * 2.0D;
                if (!level.getBlockState((new BlockPos(d6, d7, d8)).below()).isAir()) {
                    level.addParticle(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, d2, d3, d4);
                }
            }

        }
    }

    /**
     * Called when this item is used when targeting a Block
     */
    public @NotNull
    InteractionResult useOn(UseOnContext context) {
        Level dimension = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockPos blockPos1 = blockPos.relative(context.getClickedFace());
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.FAIL;
        }

        if (applyGrowth(context.getItemInHand(), dimension, blockPos, player)) {
            if (!dimension.isClientSide) {
                dimension.levelEvent(2005, blockPos, 0);
                player.awardStat(Stats.ITEM_USED.get(this));
            }

            return InteractionResult.sidedSuccess(dimension.isClientSide);
        } else {
            BlockState blockstate = dimension.getBlockState(blockPos);
            boolean flag = blockstate.isFaceSturdy(dimension, blockPos, context.getClickedFace());
            if (flag && growWaterPlant(context.getItemInHand(), dimension, blockPos1, context.getClickedFace())) {
                if (!dimension.isClientSide) {
                    dimension.levelEvent(2005, blockPos1, 0);
                    player.awardStat(Stats.ITEM_USED.get(this));
                }

                return InteractionResult.sidedSuccess(dimension.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }
}
