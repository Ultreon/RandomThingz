package com.ultreon.randomthingz.item.tools.types;

import com.google.common.collect.Sets;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.commons.IHasToolType;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
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
import java.util.List;
import java.util.Set;

@SuppressWarnings("SameParameterValue")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
public class LumberAxeItem extends AxeItem implements IHasToolType {
    private static final Set<Material> EFFECTIVE_ON_MATERIALS = Sets.newHashSet(Material.WOOD, Material.NETHER_WOOD, Material.PLANTS, Material.TALL_PLANTS, Material.BAMBOO, Material.GOURD);

    public LumberAxeItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder.defaultMaxDamage((int) (tier.getMaxUses() * 1.7)));
    }

    public float getMiningSpeed(ItemStack stack, BlockState state) {
        Material material = state.getMaterial();
        if (EFFECTIVE_ON_MATERIALS.contains(material)) return this.efficiency * 1.6f;
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
            if (heldItem instanceof LumberAxeItem) {
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
     * @param pos the block's position.
     * @param player the player that's trying to break a block.
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

        positions.add(pos.east());
        positions.add(pos.west());
        positions.add(pos.up());
        positions.add(pos.down());
        positions.add(pos.north());
        positions.add(pos.south());
        positions.add(pos.east().up());
        positions.add(pos.west().up());
        positions.add(pos.east().down());
        positions.add(pos.west().down());
        positions.add(pos.north().up());
        positions.add(pos.south().up());
        positions.add(pos.north().down());
        positions.add(pos.south().down());
        positions.add(pos.north().east());
        positions.add(pos.south().east());
        positions.add(pos.north().west());
        positions.add(pos.south().west());
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

    private ActionResultType useItem(World dimension, BlockPos blockpos, ItemUseContext context) {
        BlockState dimensionBlockState = dimension.getBlockState(blockpos);
        BlockState toolModifiedState = dimensionBlockState.getToolModifiedState(dimension, blockpos, context.getPlayer(), context.getItem(), ModToolTypes.LUMBER_AXE);
        if (toolModifiedState == null) {
            toolModifiedState = dimensionBlockState.getToolModifiedState(dimension, blockpos, context.getPlayer(), context.getItem(), ToolType.AXE);
        }
        if (toolModifiedState != null) {
            PlayerEntity player = context.getPlayer();
            dimension.playSound(player, blockpos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!dimension.isClientSided) {
                dimension.setBlockState(blockpos, toolModifiedState, 11);
                if (player != null) {
                    context.getItem().damageItem(1, player, (p_220040_1_) -> p_220040_1_.sendBreakAnimation(context.getHand()));
                }
            }

            return ActionResultType.func_233537_a_(dimension.isClientSided);
        } else {
            return ActionResultType.PASS;
        }
    }

    @Override
    public ToolType getToolType() {
        return ToolType.AXE;
    }
}
