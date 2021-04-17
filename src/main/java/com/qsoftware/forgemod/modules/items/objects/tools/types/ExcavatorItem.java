package com.qsoftware.forgemod.modules.items.objects.tools.types;

import com.google.common.collect.ImmutableSet;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.common.IHasToolType;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
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
import java.util.List;
import java.util.Set;

@SuppressWarnings("SameParameterValue")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod.EventBusSubscriber(modid = QForgeMod.modId)
public class ExcavatorItem extends ShovelItem implements IHasToolType {
    private static final Set<Material> EFFECTIVE_ON_MATERIAL = ImmutableSet.of(Material.SNOW, Material.SNOW_BLOCK, Material.SAND, Material.EARTH, Material.CLAY);

    public ExcavatorItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder.defaultMaxDamage((int) (tier.getMaxUses() * 1.7)));
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        Material material = state.getMaterial();
        if (EFFECTIVE_ON_MATERIAL.contains(material)) return this.efficiency * 1.6f;
        if (getToolTypes(stack).stream().anyMatch(state::isToolEffective)) return efficiency;
        return this.effectiveBlocks.contains(state.getBlock()) ? this.efficiency : 1.0F;
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClick(InputEvent.ClickInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.getKeyBinding() == mc.gameSettings.keyBindAttack && event.isAttack() && mc.playerController != null && mc.player != null && mc.world != null) {
            ClientPlayerEntity player = mc.player;
            ClientWorld world = mc.world;
            ItemStack stack = mc.player.getHeldItem(Hand.MAIN_HAND);
            Item heldItem = stack.getItem();
            if (heldItem instanceof ExcavatorItem) {
                CompoundNBT modTag = stack.getOrCreateChildTag(QForgeMod.modId);
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
                            damageBlock(world, pos, mc, player, blockraytraceresult);

                            if (currentFacing.getAxis() == Direction.Axis.Z) {
                                damageBlock(world, pos.east(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.west(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.up(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.down(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.east().up(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.west().up(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.east().down(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.west().down(), mc, player, blockraytraceresult);
                            }
                            if (currentFacing.getAxis() == Direction.Axis.X) {
                                damageBlock(world, pos.north(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.south(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.up(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.down(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.north().up(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.south().up(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.north().down(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.south().down(), mc, player, blockraytraceresult);
                            }
                            if (currentFacing.getAxis() == Direction.Axis.Y) {
                                damageBlock(world, pos.north(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.south(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.east(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.west(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.north().east(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.south().east(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.north().west(), mc, player, blockraytraceresult);
                                damageBlock(world, pos.south().west(), mc, player, blockraytraceresult);
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
    private static void damageBlock(World worldIn, BlockPos blockpos, Minecraft mc, ClientPlayerEntity player, BlockRayTraceResult blockraytraceresult) {
        if (!worldIn.isAirBlock(blockpos)) {
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
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
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
        destroyAround(entity, entity.getEntityWorld(), pos, direction);
    }

    protected void destroyAround(LivingEntity entity, BlockPos pos, Direction direction, boolean dropBlock) {
        destroyAround(entity, entity.getEntityWorld(), pos, direction, dropBlock);
    }

    protected void destroyAround(LivingEntity entity, World world, BlockPos pos, Direction direction) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            float blockHardness = world.getBlockState(pos).getPlayerRelativeBlockHardness(player, world, pos);
            for (BlockPos position : getPositionsAround(pos, direction)) {
                if (world.getBlockState(position).getPlayerRelativeBlockHardness(player, world, pos) <= blockHardness) {
                    destroy(entity, world, position);
                }
            }
        } else {
            float blockHardness = world.getBlockState(pos).getBlockHardness(world, pos);
            for (BlockPos position : getPositionsAround(pos, direction)) {
                if (world.getBlockState(position).getBlockHardness(world, pos) <= blockHardness) {
                    destroy(entity, world, position);
                }
            }
        }
    }

    protected void destroyAround(LivingEntity entity, World world, BlockPos pos, Direction direction, boolean dropBlock) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            float blockHardness = world.getBlockState(pos).getPlayerRelativeBlockHardness(player, world, pos);
            for (BlockPos position : getPositionsAround(pos, direction)) {
                if (world.getBlockState(position).getPlayerRelativeBlockHardness(player, world, pos) <= blockHardness) {
                    destroy(entity, world, position, dropBlock);
                }
            }
        } else {
            float blockHardness = world.getBlockState(pos).getBlockHardness(world, pos);
            for (BlockPos position : getPositionsAround(pos, direction)) {
                if (world.getBlockState(position).getBlockHardness(world, pos) <= blockHardness) {
                    destroy(entity, world, position, dropBlock);
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

    private void destroy(LivingEntity entityLiving, World worldIn, BlockPos pos) {
        worldIn.destroyBlock(pos, true, entityLiving);
    }

    private void destroy(LivingEntity entityLiving, World worldIn, BlockPos pos, boolean dropBlock) {
        worldIn.destroyBlock(pos, dropBlock, entityLiving);
    }

    /**
     * Called when this item is used when targeting a Block
     */
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        Direction face = context.getFace();
        ActionResultType result = useItem(world, pos, context);

        for (BlockPos position : getPositionsAround(pos, face)) {
            useItem(world, position, context);
        }
        return result;
    }

    private ActionResultType useItem(World world, BlockPos blockpos, ItemUseContext context) {
        BlockState blockstate = world.getBlockState(blockpos);
        if (context.getFace() == Direction.DOWN) {
            return ActionResultType.PASS;
        } else {
            PlayerEntity playerentity = context.getPlayer();
            BlockState toolModifiedState = blockstate.getToolModifiedState(world, blockpos, playerentity, context.getItem(), ModToolTypes.EXCAVATOR);
            if (toolModifiedState == null) {
                toolModifiedState = blockstate.getToolModifiedState(world, blockpos, playerentity, context.getItem(), ToolType.SHOVEL);
            }
            BlockState finalBlockState = null;
            if (toolModifiedState != null && world.isAirBlock(blockpos.up())) {
                world.playSound(playerentity, blockpos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                finalBlockState = toolModifiedState;
            } else if (blockstate.getBlock() instanceof CampfireBlock && blockstate.get(CampfireBlock.LIT)) {
                if (!world.isRemote()) {
                    world.playEvent(null, 1009, blockpos, 0);
                }

                CampfireBlock.extinguish(world, blockpos, blockstate);
                finalBlockState = blockstate.with(CampfireBlock.LIT, Boolean.FALSE);
            }

            if (finalBlockState != null) {
                if (!world.isRemote) {
                    world.setBlockState(blockpos, finalBlockState, 11);
                    if (playerentity != null) {
                        context.getItem().damageItem(1, playerentity, (player) -> player.sendBreakAnimation(context.getHand()));
                    }
                }

                return ActionResultType.func_233537_a_(world.isRemote);
            } else {
                return ActionResultType.PASS;
            }
        }
    }

    @Override
    public ToolType getToolType() {
        return ToolType.SHOVEL;
    }
}
