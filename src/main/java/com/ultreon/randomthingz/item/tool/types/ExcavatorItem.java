package com.ultreon.randomthingz.item.tool.types;

import com.google.common.collect.ImmutableSet;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.IHasToolType;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
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
@Deprecated
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
public class ExcavatorItem extends ShovelItem implements IHasToolType {
    private static final Set<Material> EFFECTIVE_ON_MATERIAL = ImmutableSet.of(Material.TOP_SNOW, Material.SNOW, Material.SAND, Material.DIRT, Material.CLAY);

    public ExcavatorItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder.tab(null).defaultDurability((int) (tier.getUses() * 1.7)));
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        Material material = state.getMaterial();
        if (EFFECTIVE_ON_MATERIAL.contains(material)) return this.speed * 1.6f;
        if (getToolTypes(stack).stream().anyMatch(state::isToolEffective)) return speed;
        return this.blocks.contains(state.getBlock()) ? this.speed : 1.0F;
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClick(InputEvent.ClickInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.getKeyBinding() == mc.options.keyAttack && event.isAttack() && mc.gameMode != null && mc.player != null && mc.level != null) {
            LocalPlayer player = mc.player;
            ClientLevel dimension = mc.level;
            ItemStack stack = mc.player.getItemInHand(InteractionHand.MAIN_HAND);
            Item heldItem = stack.getItem();
            if (heldItem instanceof ExcavatorItem) {
                CompoundTag modTag = stack.getOrCreateTagElement(RandomThingz.MOD_ID);
                if (modTag.contains("knownFacing", 3)) {
                    Direction knownFacing = Direction.values()[modTag.getInt("knownFacing")];

                    Vec3 lookVec = player.getLookAngle();
                    Direction currentFacing = Direction.getNearest(lookVec.x, lookVec.y, lookVec.z);

                    if (knownFacing != currentFacing) {
                        player.releaseUsingItem();

                        MultiPlayerGameMode playerController = Minecraft.getInstance().gameMode;
                        if (playerController != null) {
                            playerController.stopDestroyBlock();
                        }
                    } else {
                        boolean leftClick = mc.options.keyAttack.isDown();
                        if (leftClick && mc.hitResult != null && mc.hitResult.getType() == HitResult.Type.BLOCK) {
                            BlockHitResult blockraytraceresult = (BlockHitResult) mc.hitResult;
                            BlockPos pos = blockraytraceresult.getBlockPos();
                            damageBlock(dimension, pos, mc, player, blockraytraceresult);

                            if (currentFacing.getAxis() == Direction.Axis.Z) {
                                damageBlock(dimension, pos.east(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.west(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.above(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.below(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.east().above(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.west().above(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.east().below(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.west().below(), mc, player, blockraytraceresult);
                            }
                            if (currentFacing.getAxis() == Direction.Axis.X) {
                                damageBlock(dimension, pos.north(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.south(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.above(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.below(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.north().above(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.south().above(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.north().below(), mc, player, blockraytraceresult);
                                damageBlock(dimension, pos.south().below(), mc, player, blockraytraceresult);
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
                            mc.gameMode.stopDestroyBlock();
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static void damageBlock(Level dimensionIn, BlockPos blockpos, Minecraft mc, LocalPlayer player, BlockHitResult blockraytraceresult) {
        if (!dimensionIn.isEmptyBlock(blockpos)) {
            net.minecraftforge.client.event.InputEvent.ClickInputEvent inputEvent = net.minecraftforge.client.ForgeHooksClient.onClickInput(0, mc.options.keyAttack, InteractionHand.MAIN_HAND);
            if (inputEvent.isCanceled()) {
                if (inputEvent.shouldSwingHand()) {
                    mc.particleEngine.addBlockHitEffects(blockpos, blockraytraceresult);
                    player.swing(InteractionHand.MAIN_HAND);
                }
                return;
            }
            Direction direction = blockraytraceresult.getDirection();
            if (mc.gameMode.continueDestroyBlock(blockpos, direction)) {
                if (inputEvent.shouldSwingHand()) {
                    mc.particleEngine.addBlockHitEffects(blockpos, blockraytraceresult);
                    player.swing(InteractionHand.MAIN_HAND);
                }
            }
        }
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level dimensionIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (stack.getItem() != this) {
            return true;
        }

        Vec3 lookVec = entityLiving.getLookAngle();
        Direction face = Direction.getNearest(lookVec.x, lookVec.y, lookVec.z);

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
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        if (player.isCreative()) {
            Vec3 lookVec = player.getLookAngle();
            Direction face = Direction.getNearest(lookVec.x, lookVec.y, lookVec.z);

            destroyAround(player, pos, face, false);
        }
        return false;
    }

    protected void destroyAround(LivingEntity entity, BlockPos pos, Direction direction) {
        destroyAround(entity, entity.getCommandSenderWorld(), pos, direction);
    }

    protected void destroyAround(LivingEntity entity, BlockPos pos, Direction direction, boolean dropBlock) {
        destroyAround(entity, entity.getCommandSenderWorld(), pos, direction, dropBlock);
    }

    protected void destroyAround(LivingEntity entity, Level dimension, BlockPos pos, Direction direction) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            float blockHardness = dimension.getBlockState(pos).getDestroyProgress(player, dimension, pos);
            for (BlockPos position : getPositionsAround(pos, direction)) {
                if (dimension.getBlockState(position).getDestroyProgress(player, dimension, pos) <= blockHardness) {
                    destroy(entity, dimension, position);
                }
            }
        } else {
            float blockHardness = dimension.getBlockState(pos).getDestroySpeed(dimension, pos);
            for (BlockPos position : getPositionsAround(pos, direction)) {
                if (dimension.getBlockState(position).getDestroySpeed(dimension, pos) <= blockHardness) {
                    destroy(entity, dimension, position);
                }
            }
        }
    }

    protected void destroyAround(LivingEntity entity, Level dimension, BlockPos pos, Direction direction, boolean dropBlock) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            float blockHardness = dimension.getBlockState(pos).getDestroyProgress(player, dimension, pos);
            for (BlockPos position : getPositionsAround(pos, direction)) {
                if (dimension.getBlockState(position).getDestroyProgress(player, dimension, pos) <= blockHardness) {
                    destroy(entity, dimension, position, dropBlock);
                }
            }
        } else {
            float blockHardness = dimension.getBlockState(pos).getDestroySpeed(dimension, pos);
            for (BlockPos position : getPositionsAround(pos, direction)) {
                if (dimension.getBlockState(position).getDestroySpeed(dimension, pos) <= blockHardness) {
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
                positions.add(pos.above());
                positions.add(pos.below());
                positions.add(pos.east().above());
                positions.add(pos.west().above());
                positions.add(pos.east().below());
                positions.add(pos.west().below());
                break;
            case X:
                positions.add(pos.north());
                positions.add(pos.south());
                positions.add(pos.above());
                positions.add(pos.below());
                positions.add(pos.north().above());
                positions.add(pos.south().above());
                positions.add(pos.north().below());
                positions.add(pos.south().below());
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

    private void destroy(LivingEntity entityLiving, Level dimensionIn, BlockPos pos) {
        dimensionIn.destroyBlock(pos, true, entityLiving);
    }

    private void destroy(LivingEntity entityLiving, Level dimensionIn, BlockPos pos, boolean dropBlock) {
        dimensionIn.destroyBlock(pos, dropBlock, entityLiving);
    }

    /**
     * Called when this item is used when targeting a Block
     */
    public InteractionResult useOn(UseOnContext context) {
        Level dimension = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction face = context.getClickedFace();
        InteractionResult result = useItem(dimension, pos, context);

        for (BlockPos position : getPositionsAround(pos, face)) {
            useItem(dimension, position, context);
        }
        return result;
    }

    private InteractionResult useItem(Level dimension, BlockPos blockpos, UseOnContext context) {
        BlockState blockstate = dimension.getBlockState(blockpos);
        if (context.getClickedFace() == Direction.DOWN) {
            return InteractionResult.PASS;
        } else {
            Player playerentity = context.getPlayer();
            BlockState toolModifiedState = blockstate.getToolModifiedState(dimension, blockpos, playerentity, context.getItemInHand(), ModToolTypes.EXCAVATOR);
            if (toolModifiedState == null) {
                toolModifiedState = blockstate.getToolModifiedState(dimension, blockpos, playerentity, context.getItemInHand(), ToolType.SHOVEL);
            }
            BlockState finalBlockState = null;
            if (toolModifiedState != null && dimension.isEmptyBlock(blockpos.above())) {
                dimension.playSound(playerentity, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                finalBlockState = toolModifiedState;
            } else if (blockstate.getBlock() instanceof CampfireBlock && blockstate.getValue(CampfireBlock.LIT)) {
                if (!dimension.isClientSide()) {
                    dimension.levelEvent(null, 1009, blockpos, 0);
                }

                CampfireBlock.dowse(dimension, blockpos, blockstate);
                finalBlockState = blockstate.setValue(CampfireBlock.LIT, Boolean.FALSE);
            }

            if (finalBlockState != null) {
                if (!dimension.isClientSide) {
                    dimension.setBlock(blockpos, finalBlockState, 11);
                    if (playerentity != null) {
                        context.getItemInHand().hurtAndBreak(1, playerentity, (player) -> player.broadcastBreakEvent(context.getHand()));
                    }
                }

                return InteractionResult.sidedSuccess(dimension.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    @Override
    public ToolType getToolType() {
        return ToolType.SHOVEL;
    }
}
