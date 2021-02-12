package com.qsoftware.forgemod.modules.items.objects.tools;

import com.qsoftware.forgemod.QForgeMod;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QForgeMod.modId)
public class HammerItem extends PickaxeItem {
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

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        Material material = state.getMaterial();
        if (material == Material.IRON || material == Material.ANVIL || material == Material.ROCK) return this.efficiency * 1.7f;
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
            if (heldItem instanceof HammerItem) {
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
        Vector3d lookVec = entityLiving.getLookVec();
        Direction face = Direction.getFacingFromVector(lookVec.x, lookVec.y, lookVec.z);

        if (face.getAxis() == Direction.Axis.Z) {
            destroy(entityLiving, worldIn, pos.east());
            destroy(entityLiving, worldIn, pos.west());
            destroy(entityLiving, worldIn, pos.up());
            destroy(entityLiving, worldIn, pos.down());
            destroy(entityLiving, worldIn, pos.east().up());
            destroy(entityLiving, worldIn, pos.west().up());
            destroy(entityLiving, worldIn, pos.east().down());
            destroy(entityLiving, worldIn, pos.west().down());
        }
        if (face.getAxis() == Direction.Axis.X) {
            destroy(entityLiving, worldIn, pos.north());
            destroy(entityLiving, worldIn, pos.south());
            destroy(entityLiving, worldIn, pos.up());
            destroy(entityLiving, worldIn, pos.down());
            destroy(entityLiving, worldIn, pos.north().up());
            destroy(entityLiving, worldIn, pos.south().up());
            destroy(entityLiving, worldIn, pos.north().down());
            destroy(entityLiving, worldIn, pos.south().down());
        }
        if (face.getAxis() == Direction.Axis.Y) {
            destroy(entityLiving, worldIn, pos.north());
            destroy(entityLiving, worldIn, pos.south());
            destroy(entityLiving, worldIn, pos.east());
            destroy(entityLiving, worldIn, pos.west());
            destroy(entityLiving, worldIn, pos.north().east());
            destroy(entityLiving, worldIn, pos.south().east());
            destroy(entityLiving, worldIn, pos.north().west());
            destroy(entityLiving, worldIn, pos.south().west());
        }
        return true;
    }

    private void destroy(LivingEntity entityLiving, World worldIn, BlockPos pos) {
        worldIn.destroyBlock(pos, true, entityLiving);
    }

    /**
     * Called when this item is used when targetting a Block
     */
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        Direction face = context.getFace();
        ActionResultType result = onItemUse0(world, blockpos, context);

        if (face.getAxis() == Direction.Axis.Z) {
            onItemUse0(world, blockpos.east(), context);
            onItemUse0(world, blockpos.west(), context);
            onItemUse0(world, blockpos.up(), context);
            onItemUse0(world, blockpos.down(), context);
            onItemUse0(world, blockpos.east().up(), context);
            onItemUse0(world, blockpos.west().up(), context);
            onItemUse0(world, blockpos.east().down(), context);
            onItemUse0(world, blockpos.west().down(), context);
        }
        if (face.getAxis() == Direction.Axis.X) {
            onItemUse0(world, blockpos.north(), context);
            onItemUse0(world, blockpos.south(), context);
            onItemUse0(world, blockpos.up(), context);
            onItemUse0(world, blockpos.down(), context);
            onItemUse0(world, blockpos.north().up(), context);
            onItemUse0(world, blockpos.south().up(), context);
            onItemUse0(world, blockpos.north().down(), context);
            onItemUse0(world, blockpos.south().down(), context);
        }
        if (face.getAxis() == Direction.Axis.Y) {
            onItemUse0(world, blockpos.north(), context);
            onItemUse0(world, blockpos.south(), context);
            onItemUse0(world, blockpos.east(), context);
            onItemUse0(world, blockpos.west(), context);
            onItemUse0(world, blockpos.north().east(), context);
            onItemUse0(world, blockpos.south().east(), context);
            onItemUse0(world, blockpos.north().west(), context);
            onItemUse0(world, blockpos.south().west(), context);
        }
        return result;
    }

    private ActionResultType onItemUse0(World world, BlockPos blockpos, ItemUseContext context) {
        BlockState blockstate = world.getBlockState(blockpos);
        if (context.getFace() == Direction.DOWN) {
            return ActionResultType.PASS;
        } else {
            PlayerEntity playerentity = context.getPlayer();
            BlockState blockstate1 = blockstate.getToolModifiedState(world, blockpos, playerentity, context.getItem(), net.minecraftforge.common.ToolType.SHOVEL);
            BlockState blockstate2 = null;
            if (blockstate1 != null && world.isAirBlock(blockpos.up())) {
                world.playSound(playerentity, blockpos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                blockstate2 = blockstate1;
            } else if (blockstate.getBlock() instanceof CampfireBlock && blockstate.get(CampfireBlock.LIT)) {
                if (!world.isRemote()) {
                    world.playEvent((PlayerEntity)null, 1009, blockpos, 0);
                }

                CampfireBlock.extinguish(world, blockpos, blockstate);
                blockstate2 = blockstate.with(CampfireBlock.LIT, Boolean.valueOf(false));
            }

            if (blockstate2 != null) {
                if (!world.isRemote) {
                    world.setBlockState(blockpos, blockstate2, 11);
                    if (playerentity != null) {
                        context.getItem().damageItem(1, playerentity, (player) -> {
                            player.sendBreakAnimation(context.getHand());
                        });
                    }
                }

                return ActionResultType.func_233537_a_(world.isRemote);
            } else {
                return ActionResultType.PASS;
            }
        }
    }
}
