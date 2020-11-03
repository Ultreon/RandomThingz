package com.qsoftware.forgemod.objects.items;

import com.qsoftware.forgemod.groups.Groups;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DeadCoralWallFanBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public class NatureStaffItem extends Item {
    public NatureStaffItem() {
        super(new Item.Properties().group(Groups.SPECIALS));
    }

    /**
     * Called when this item is used when targeting a Block
     */
    public @NotNull ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getPos();
        BlockPos blockPos1 = blockPos.offset(context.getFace());
        if (applyBonemeal(context.getItem(), world, blockPos, context.getPlayer())) {
            if (!world.isRemote) {
                world.playEvent(2005, blockPos, 0);
            }

            return ActionResultType.func_233537_a_(world.isRemote);
        } else {
            BlockState blockstate = world.getBlockState(blockPos);
            boolean flag = blockstate.isSolidSide(world, blockPos, context.getFace());
            if (flag && growSeagrass(context.getItem(), world, blockPos1, context.getFace())) {
                if (!world.isRemote) {
                    world.playEvent(2005, blockPos1, 0);
                }

                return ActionResultType.func_233537_a_(world.isRemote);
            } else {
                return ActionResultType.PASS;
            }
        }
    }

    @Deprecated //Forge: Use Player/Hand version
    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos pos) {
        if (worldIn instanceof net.minecraft.world.server.ServerWorld)
            return applyBonemeal(stack, worldIn, pos, net.minecraftforge.common.util.FakePlayerFactory.getMinecraft((net.minecraft.world.server.ServerWorld)worldIn));
        return false;
    }

    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos pos, net.minecraft.entity.player.PlayerEntity player) {
        BlockState blockstate = worldIn.getBlockState(pos);
        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, worldIn, pos, blockstate, stack);
        if (hook != 0) return hook > 0;
        if (blockstate.getBlock() instanceof IGrowable) {
            IGrowable igrowable = (IGrowable)blockstate.getBlock();
            if (igrowable.canGrow(worldIn, pos, blockstate, worldIn.isRemote)) {
                if (worldIn instanceof ServerWorld) {
                    if (igrowable.canUseBonemeal(worldIn, worldIn.rand, pos, blockstate)) {
                        igrowable.grow((ServerWorld)worldIn, worldIn.rand, pos, blockstate);
                    }
                }

                return true;
            }
        }

        return false;
    }

    @SuppressWarnings("unused")
    public static boolean growSeagrass(ItemStack stack, World worldIn, BlockPos pos, @Nullable Direction side) {
        if (worldIn.getBlockState(pos).isIn(Blocks.WATER) && worldIn.getFluidState(pos).getLevel() == 8) {
            if (worldIn instanceof ServerWorld) {
                label80:
                for (int i = 0; i < 128; ++i) {
                    BlockPos blockpos = pos;
                    BlockState blockState = Blocks.SEAGRASS.getDefaultState();

                    for (int j = 0; j < i / 16; ++j) {
                        blockpos = blockpos.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                        if (worldIn.getBlockState(blockpos).hasOpaqueCollisionShape(worldIn, blockpos)) {
                            continue label80;
                        }
                    }

                    Optional<RegistryKey<Biome>> optional = worldIn.func_242406_i(blockpos);
                    if (Objects.equals(optional, Optional.of(Biomes.WARM_OCEAN)) || Objects.equals(optional, Optional.of(Biomes.DEEP_WARM_OCEAN))) {
                        if (i == 0 && side != null && side.getAxis().isHorizontal()) {
                            blockState = BlockTags.WALL_CORALS.getRandomElement(worldIn.rand).getDefaultState().with(DeadCoralWallFanBlock.FACING, side);
                        } else if (random.nextInt(4) == 0) {
                            blockState = BlockTags.UNDERWATER_BONEMEALS.getRandomElement(random).getDefaultState();
                        }
                    }

                    if (blockState.getBlock().isIn(BlockTags.WALL_CORALS)) {
                        for (int k = 0; !blockState.isValidPosition(worldIn, blockpos) && k < 4; ++k) {
                            blockState = blockState.with(DeadCoralWallFanBlock.FACING, Direction.Plane.HORIZONTAL.random(random));
                        }
                    }

                    if (blockState.isValidPosition(worldIn, blockpos)) {
                        BlockState blockState1 = worldIn.getBlockState(blockpos);
                        if (blockState1.isIn(Blocks.WATER) && worldIn.getFluidState(blockpos).getLevel() == 8) {
                            worldIn.setBlockState(blockpos, blockState, 3);
                        } else if (blockState1.isIn(Blocks.SEAGRASS) && random.nextInt(10) == 0) {
                            ((IGrowable) Blocks.SEAGRASS).grow((ServerWorld) worldIn, random, blockpos, blockState1);
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
    public static void spawnBonemealParticles(IWorld worldIn, BlockPos posIn, int data) {
        if (data == 0) {
            data = 15;
        }

        BlockState blockstate = worldIn.getBlockState(posIn);
        //noinspection deprecation
        if (!blockstate.isAir(worldIn, posIn)) {
            double d0 = 0.5D;
            double d1;
            if (blockstate.isIn(Blocks.WATER)) {
                data *= 3;
                d1 = 1.0D;
                d0 = 3.0D;
            } else if (blockstate.isOpaqueCube(worldIn, posIn)) {
                posIn = posIn.up();
                data *= 3;
                d0 = 3.0D;
                d1 = 1.0D;
            } else {
                d1 = blockstate.getShape(worldIn, posIn).getEnd(Direction.Axis.Y);
            }

            worldIn.addParticle(ParticleTypes.HAPPY_VILLAGER, (double)posIn.getX() + 0.5D, (double)posIn.getY() + 0.5D, (double)posIn.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);

            for(int i = 0; i < data; ++i) {
                double d2 = random.nextGaussian() * 0.02D;
                double d3 = random.nextGaussian() * 0.02D;
                double d4 = random.nextGaussian() * 0.02D;
                double d5 = 0.5D - d0;
                double d6 = (double)posIn.getX() + d5 + random.nextDouble() * d0 * 2.0D;
                double d7 = (double)posIn.getY() + random.nextDouble() * d1;
                double d8 = (double)posIn.getZ() + d5 + random.nextDouble() * d0 * 2.0D;
                //noinspection deprecation
                if (!worldIn.getBlockState((new BlockPos(d6, d7, d8)).down()).isAir()) {
                    worldIn.addParticle(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, d2, d3, d4);
                }
            }

        }
    }
}
