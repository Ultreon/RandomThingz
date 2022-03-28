package com.ultreon.randomthingz.item.wand;

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
@Deprecated
public class NatureStaffItem extends Item {
    public NatureStaffItem() {
        super(new Properties().tab(ModCreativeTabs.SPECIALS).rarity(Rarity.RARE));
    }

    @Deprecated //Forge: Use Player/Hand version
    public static boolean applyBonemeal(ItemStack stack, Level level, BlockPos pos) {
        if (level instanceof ServerLevel)
            return applyBonemeal(stack, level, pos, net.minecraftforge.common.util.FakePlayerFactory.getMinecraft((ServerLevel) level));
        return false;
    }

    public static boolean applyBonemeal(ItemStack stack, Level level, BlockPos pos, Player player) {
        BlockState blockstate = level.getBlockState(pos);
        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, level, pos, blockstate, stack);
        if (hook != 0) return hook > 0;
        if (blockstate.getBlock() instanceof BonemealableBlock bonemealable) {
            if (bonemealable.isValidBonemealTarget(level, pos, blockstate, level.isClientSide)) {
                if (level instanceof ServerLevel) {
                    if (bonemealable.isBonemealSuccess(level, level.random, pos, blockstate)) {
                        bonemealable.performBonemeal((ServerLevel) level, level.random, pos, blockstate);
                    }
                }

                return true;
            }
        }

        return false;
    }

    @SuppressWarnings("unused")
    public static boolean growSeagrass(ItemStack stack, Level level, BlockPos pos, @Nullable Direction side) {
        Random random = level.getRandom();
        if (level.getBlockState(pos).is(Blocks.WATER) && level.getFluidState(pos).getAmount() == 8) {
            if (level instanceof ServerLevel) {
                label80: for (int i = 0; i < 128; ++i) {
                    BlockPos blockpos = pos;
                    BlockState blockState = Blocks.SEAGRASS.defaultBlockState();

                    for (int j = 0; j < i / 16; ++j) {
                        blockpos = blockpos.offset(level.getRandom().nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
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
    public static void spawnBonemealParticles(LevelAccessor level, BlockPos posIn, int data) {
        if (data == 0) {
            data = 15;
        }

        Random random = level.getRandom();

        BlockState blockstate = level.getBlockState(posIn);
        if (!blockstate.isAir()) {
            double d0 = 0.5D;
            double d1;
            if (blockstate.is(Blocks.WATER)) {
                data *= 3;
                d1 = 1.0D;
                d0 = 3.0D;
            } else if (blockstate.isSolidRender(level, posIn)) {
                posIn = posIn.above();
                data *= 3;
                d0 = 3.0D;
                d1 = 1.0D;
            } else {
                d1 = blockstate.getShape(level, posIn).max(Direction.Axis.Y);
            }

            level.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) posIn.getX() + 0.5D, (double) posIn.getY() + 0.5D, (double) posIn.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);

            for (int i = 0; i < data; ++i) {
                double d2 = random.nextGaussian() * 0.02D;
                double d3 = random.nextGaussian() * 0.02D;
                double d4 = random.nextGaussian() * 0.02D;
                double d5 = 0.5D - d0;
                double d6 = (double) posIn.getX() + d5 + random.nextDouble() * d0 * 2.0D;
                double d7 = (double) posIn.getY() + random.nextDouble() * d1;
                double d8 = (double) posIn.getZ() + d5 + random.nextDouble() * d0 * 2.0D;
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
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockPos blockPos1 = blockPos.relative(context.getClickedFace());
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.FAIL;
        }

        if (applyBonemeal(context.getItemInHand(), level, blockPos, player)) {
            if (!level.isClientSide) {
                level.levelEvent(2005, blockPos, 0);
                player.awardStat(Stats.ITEM_USED.get(this));
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            BlockState blockstate = level.getBlockState(blockPos);
            boolean flag = blockstate.isFaceSturdy(level, blockPos, context.getClickedFace());
            if (flag && growSeagrass(context.getItemInHand(), level, blockPos1, context.getClickedFace())) {
                if (!level.isClientSide) {
                    level.levelEvent(2005, blockPos1, 0);
                    player.awardStat(Stats.ITEM_USED.get(this));
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }
}
