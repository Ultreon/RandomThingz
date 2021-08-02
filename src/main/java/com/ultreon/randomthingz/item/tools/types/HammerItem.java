package com.ultreon.randomthingz.item.tools.types;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.commons.IHasToolType;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("SameParameterValue")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
public class HammerItem extends PickaxeItem implements IHasToolType {
    private static final Map<Block, BlockState> smashMappings = new HashMap<>();

    public static void registerSmashMapping(Block source, BlockState state) {
        smashMappings.put(source, state);
    }

    static {
        registerSmashMapping(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS.getDefaultState());
        registerSmashMapping(Blocks.STONE_STAIRS, Blocks.COBBLESTONE_STAIRS.getDefaultState());
        registerSmashMapping(Blocks.STONE_SLAB, Blocks.COBBLESTONE_SLAB.getDefaultState());
        registerSmashMapping(Blocks.STONE, Blocks.COBBLESTONE.getDefaultState());
        registerSmashMapping(Blocks.COBBLESTONE, Blocks.GRAVEL.getDefaultState());
        registerSmashMapping(Blocks.CHISELED_SANDSTONE, Blocks.SANDSTONE.getDefaultState());
        registerSmashMapping(Blocks.SMOOTH_SANDSTONE, Blocks.SANDSTONE.getDefaultState());
        registerSmashMapping(Blocks.CUT_SANDSTONE, Blocks.SANDSTONE.getDefaultState());
        registerSmashMapping(Blocks.SANDSTONE, Blocks.SAND.getDefaultState());
        registerSmashMapping(Blocks.CHISELED_RED_SANDSTONE, Blocks.RED_SANDSTONE.getDefaultState());
        registerSmashMapping(Blocks.SMOOTH_RED_SANDSTONE, Blocks.RED_SANDSTONE.getDefaultState());
        registerSmashMapping(Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE.getDefaultState());
        registerSmashMapping(Blocks.RED_SANDSTONE, Blocks.RED_SAND.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_ANDESITE_STAIRS, Blocks.ANDESITE_STAIRS.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_GRANITE_STAIRS, Blocks.GRANITE_STAIRS.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_DIORITE_STAIRS, Blocks.DIORITE_STAIRS.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_ANDESITE_SLAB, Blocks.ANDESITE_SLAB.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_GRANITE_SLAB, Blocks.GRANITE_SLAB.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_DIORITE_SLAB, Blocks.DIORITE_SLAB.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_ANDESITE, Blocks.ANDESITE.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_GRANITE, Blocks.GRANITE.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_DIORITE, Blocks.DIORITE.getDefaultState());
    }

    public HammerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder.defaultMaxDamage((int) (tier.getMaxUses() * 1.8)));
    }

    /**
     * Check whether this Item can harvest the given Block
     */
    public boolean canHarvestBlock(BlockState blockIn) {
        int i = this.getTier().getHarvestLevel();
        if (blockIn.getHarvestTool() == net.minecraftforge.common.ToolType.PICKAXE) {
            return i >= blockIn.getHarvestLevel();
        }
        Material material = blockIn.getMaterial();
        return material == Material.ROCK || material == Material.IRON || material == Material.ANVIL;
    }

    public float getMiningSpeed(ItemStack stack, BlockState state) {
        Material material = state.getMaterial();
        if (material == Material.IRON || material == Material.ANVIL || material == Material.ROCK)
            return this.efficiency * 1.7f;
        if (getToolTypes(stack).stream().anyMatch(state::isToolEffective)) return efficiency;
        return this.effectiveBlocks.contains(state.getBlock()) ? this.efficiency : 1.0F;
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClick(InputEvent.ClickInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.getKeyBinding() == mc.gameSettings.keyBindAttack && event.isAttack() && mc.playerController != null && mc.player != null && mc.dimension != null) {
            ClientPlayerEntity player = mc.player;
            ClientWorld dimension = mc.dimension;
            ItemStack stack = mc.player.getHeldItem(Hand.MAIN_HAND);
            Item heldItem = stack.getItem();
            if (heldItem instanceof HammerItem) {
                CompoundNBT modTag = stack.getOrCreateChildTag(RandomThingz.MOD_ID);
                if (modTag.contains("knownFacing", 3)) {
                    Direction knownFacing = Direction.values()[modTag.getInt("knownFacing")];

                    Vector3d lookVec = player.getLookVec();
                    Direction currentFacing = Direction.getFacingFromVector(lookVec.x, lookVec.y, lookVec.z);

                    if (knownFacing != currentFacing) {
                        player.stopActiveHand();

                        PlayerController playerController = Minecraft.getInstance().playerController;
                        if (playerController != null) {
                            playerController.resetBlockRemoving();
                        }
                    } else {
                        boolean leftClick = mc.gameSettings.keyBindAttack.isKeyDown();
                        if (leftClick && mc.objectMouseOver != null && mc.objectMouseOver.getType() == RayTraceResult.Type.BLOCK) {
                            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) mc.objectMouseOver;
                            BlockPos pos = blockraytraceresult.getPos();
                            damageBlock(dimension, pos, mc, player, blockraytraceresult);

                            if (currentFacing.getAxis() == Direction.Axis.Z) {
                                damageBlock(dimension, pos.east(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.west(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.up(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.down(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.east().up(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.west().up(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.east().down(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.west().down(), mc, player, blockraytraceresult);
                            }
                            if (currentFacing.getAxis() == Direction.Axis.X) {
                                damageBlock(dimension, pos.north(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.south(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.up(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.down(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.north().up(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.south().up(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.north().down(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.south().down(), mc, player, blockraytraceresult);
                            }
                            if (currentFacing.getAxis() == Direction.Axis.Y) {
                                damageBlock(dimension, pos.north(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.south(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.east(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.west(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.north().east(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.south().east(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.north().west(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.south().west(), mc, player, blockraytraceresult);
                            }
                        } else {
                            mc.playerController.resetBlockRemoving();
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static void damageBlock(World dimensionIn, BlockPos blockpos, Minecraft mc, ClientPlayerEntity player, BlockRayTraceResult blockraytraceresult) {
        if (!dimensionIn.isAirBlock(blockpos)) {
            net.minecraftforge.client.event.InputEvent.ClickInputEvent inputEvent = net.minecraftforge.client.ForgeHooksClient.onClickInput(0, mc.gameSettings.keyBindAttack, Hand.MAIN_HAND);
            if (inputEvent.isCanceled()) {
                if (inputEvent.shouldSwingHand()) {
                    mc.particles.addBlockHitEffects(blockpos, blockraytraceresult);
                    player.swingArm(Hand.MAIN_HAND);
                }
                return;
            }
            Direction direction = blockraytraceresult.getFace();
            if (mc.playerController.onPlayerDamageBlock(blockpos, direction)) {
                if (inputEvent.shouldSwingHand()) {
                    mc.particles.addBlockHitEffects(blockpos, blockraytraceresult);
                    player.swingArm(Hand.MAIN_HAND);
                }
            }
        }
    }

    @Override
    public ToolType getToolType() {
        return ToolType.PICKAXE;
    }

    @Override
    public boolean onBlockBroken(ItemStack stack, World dimensionIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (stack.getItem() != this) {
            return true;
        }

        Vector3d lookVec = entityLiving.getLookVec();
        Direction face = Direction.getFacingFromVector(lookVec.x, lookVec.y, lookVec.z);

        destroyAround(entityLiving, pos, face);

        return true;
    }

    /**
     * Block start break handler. Here's where the magic of 3x3 begins.
     *
     * @param itemstack the item stack used for breaking a block.
     * @param pos       the block's position.
     * @param player    the player that's trying to break a block.
     * @return false, so the block can be broken.
     */
    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player) {
        if (player.isCreative()) {
            Vector3d lookVec = player.getLookVec();
            Direction face = Direction.getFacingFromVector(lookVec.x, lookVec.y, lookVec.z);

            destroyAround(player, pos, face, false);
        }
        return false;
    }

    protected void destroyAround(LivingEntity entity, BlockPos pos, Direction direction) {
        destroyAround(entity, entity.getEntityDimension(), pos, direction);
    }

    protected void destroyAround(LivingEntity entity, BlockPos pos, Direction direction, boolean dropBlock) {
        destroyAround(entity, entity.getEntityDimension(), pos, direction, dropBlock);
    }

    protected void destroyAround(LivingEntity entity, World dimension, BlockPos pos, Direction direction) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            float blockHardness = dimension.getBlockState(pos).getPlayerRelativeBlockHardness(player, dimension, pos);
            for (BlockPos position : getPositionsAround(pos, direction)) {
                if (dimension.getBlockState(position).getPlayerRelativeBlockHardness(player, dimension, pos) <= blockHardness) {
                    destroy(entity, dimension, position);
                }
            }
        } else {
            float blockHardness = dimension.getBlockState(pos).getBlockHardness(dimension, pos);
            for (BlockPos position : getPositionsAround(pos, direction)) {
                if (dimension.getBlockState(position).getBlockHardness(dimension, pos) <= blockHardness) {
                    destroy(entity, dimension, position);
                }
            }
        }
    }

    protected void destroyAround(LivingEntity entity, World dimension, BlockPos pos, Direction direction, boolean dropBlock) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            float blockHardness = dimension.getBlockState(pos).getPlayerRelativeBlockHardness(player, dimension, pos);
            for (BlockPos position : getPositionsAround(pos, direction)) {
                if (dimension.getBlockState(position).getPlayerRelativeBlockHardness(player, dimension, pos) <= blockHardness) {
                    destroy(entity, dimension, position, dropBlock);
                }
            }
        } else {
            float blockHardness = dimension.getBlockState(pos).getBlockHardness(dimension, pos);
            for (BlockPos position : getPositionsAround(pos, direction)) {
                if (dimension.getBlockState(position).getBlockHardness(dimension, pos) <= blockHardness) {
                    destroy(entity, dimension, position, dropBlock);
                }
            }
        }
    }

    protected BlockPos[] getPositionsAround(BlockPos pos, Direction direction) {
        return this.getPositionsAround(pos, direction.getAxis());
    }

    protected BlockPos[] getPositionsAround(BlockPos pos, Direction.Axis axis) {
        List<BlockPos> positions = new ArrayList<>();

        switch (axis) {
            case Z:
                positions.add(pos.east());
                positions.add(pos.west());
                positions.add(pos.up());
                positions.add(pos.down());
                positions.add(pos.east().up());
                positions.add(pos.west().up());
                positions.add(pos.east().down());
                positions.add(pos.west().down());
                break;
            case X:
                positions.add(pos.north());
                positions.add(pos.south());
                positions.add(pos.up());
                positions.add(pos.down());
                positions.add(pos.north().up());
                positions.add(pos.south().up());
                positions.add(pos.north().down());
                positions.add(pos.south().down());
                break;
            case Y:
                positions.add(pos.north());
                positions.add(pos.south());
                positions.add(pos.east());
                positions.add(pos.west());
                positions.add(pos.north().east());
                positions.add(pos.south().east());
                positions.add(pos.north().west());
                positions.add(pos.south().west());
                break;
        }

        return positions.toArray(new BlockPos[]{});
    }

    private void destroy(LivingEntity entityLiving, World dimensionIn, BlockPos pos) {
        dimensionIn.destroyBlock(pos, true, entityLiving);
    }

    private void destroy(LivingEntity entityLiving, World dimensionIn, BlockPos pos, boolean dropBlock) {
        dimensionIn.destroyBlock(pos, dropBlock, entityLiving);
    }

    /**
     * Called when this item is used when targeting a Block
     */
    public ActionResultType onUseItem(ItemUseContext context) {
        World dimension = context.getDimension();
        BlockPos pos = context.getPos();
        Direction face = context.getFace();
        ActionResultType result = useItem(dimension, pos, context);

        for (BlockPos position : getPositionsAround(pos, face)) {
            useItem(dimension, position, context);
        }
        return result;
    }

    private ActionResultType useItem(World dimension, BlockPos pos, ItemUseContext context) {
        BlockState blockstate = dimension.getBlockState(pos);
        if (context.getFace() == Direction.DOWN) {
            return ActionResultType.PASS;
        } else {
            PlayerEntity playerentity = context.getPlayer();
            BlockState toolModifiedState = blockstate.getToolModifiedState(dimension, pos, playerentity, context.getItem(), ModToolTypes.HAMMER);
            if (toolModifiedState == null) {
                toolModifiedState = blockstate.getToolModifiedState(dimension, pos, playerentity, context.getItem(), ToolType.PICKAXE);
            }
            if (toolModifiedState == null) {
                toolModifiedState = smashMappings.get(dimension.getBlockState(pos).getBlock());
            }
            BlockState finalBlockState = null;
            if (toolModifiedState != null && dimension.isAirBlock(pos.up())) {
                dimension.playSound(playerentity, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                finalBlockState = toolModifiedState;
            } else if (blockstate.getBlock() instanceof CampfireBlock && blockstate.get(CampfireBlock.LIT)) {
                if (!dimension.isClientSided()) {
                    dimension.playEvent(null, 1009, pos, 0);
                }

                CampfireBlock.extinguish(dimension, pos, blockstate);
                finalBlockState = blockstate.with(CampfireBlock.LIT, Boolean.FALSE);
            }

            if (finalBlockState != null) {
                if (!dimension.isClientSided) {
                    dimension.setBlockState(pos, finalBlockState, 11);
                    if (playerentity != null) {
                        context.getItem().damageItem(1, playerentity, (player) -> player.sendBreakAnimation(context.getHand()));
                    }
                }

                return ActionResultType.func_233537_a_(dimension.isClientSided);
            } else {
                return ActionResultType.PASS;
            }
        }
    }
}
