package com.qtech.forgemod.item.tools.trait;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BlazeTrait extends AbstractTrait {
    public BlazeTrait() {

    }

    @Override
    public boolean onHitEntity(@NotNull ItemStack stack, @NotNull LivingEntity victim, LivingEntity attacker) {
        victim.setFire(10);
        return super.onHitEntity(stack, victim, attacker);
    }

    /**
     * Called when this item is used when targetting a Block<br>
     * <b>Note: </b><i>Used for override only.</i>
     *
     * @param context the item use context. Containing information about the how, where and what is used the item.
     * @return the result of the item use override.
     */
    @Override
    public @NotNull ActionResultType onUseItem(ItemUseContext context) {
        // Get player, dimension, block position and state.
        PlayerEntity player = context.getPlayer();
        World dimension = context.getDimension();
        BlockPos position = context.getPos();
        BlockState state = dimension.getBlockState(position); // Block state in dimension of use at position of use.

        // Check if the state is a campfire, and the campfire at the block state can be lit.
        if (CampfireBlock.canBeLit(state)) {
            // Play sound.
            dimension.playSound(player, position, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, new Random().nextFloat() * 0.4F + 0.8F);

            // Set block state.
            dimension.setBlockState(position, state.with(BlockStateProperties.LIT, Boolean.TRUE), 11);

            // Damage item.
            if (player != null) {
                context.getItem().damageItem(1, player, (playerEntity) -> playerEntity.sendBreakAnimation(context.getHand()));
            }

            // Return action result.
            return ActionResultType.func_233537_a_(dimension.isClientSided());
        } else {
            // Get position from the offset of the facing from the context.
            BlockPos offsetPos = position.offset(context.getFace());

            // Check if block at the offset can be lit on fire.
            if (AbstractFireBlock.canLightBlock(dimension, offsetPos, context.getPlacementHorizontalFacing())) {
                // Play sound.
                dimension.playSound(player, offsetPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, new Random().nextFloat() * 0.4F + 0.8F);

                // Create block state for the fire block.
                BlockState blockState1 = AbstractFireBlock.getFireForPlacement(dimension, offsetPos);

                // Set block state.
                dimension.setBlockState(offsetPos, blockState1, 11);

                // Get item stack from context.
                ItemStack stack = context.getItem();
                if (player instanceof ServerPlayerEntity) {
                    // Placed block criteria trigger.
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, offsetPos, stack);

                    // Damage item by 1.
                    stack.damageItem(1, player, (playerEntity) -> playerEntity.sendBreakAnimation(context.getHand()));
                }

                return ActionResultType.func_233537_a_(dimension.isClientSided());
            } else {
                // Block at offset cannot be lit on fire.
                return ActionResultType.FAIL;
            }
        }
    }
}
