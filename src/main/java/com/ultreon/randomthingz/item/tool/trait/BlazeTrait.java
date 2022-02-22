package com.ultreon.randomthingz.item.tool.trait;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BlazeTrait extends AbstractTrait {
    public BlazeTrait() {

    }

    @Override
    public boolean onHitEntity(@NotNull ItemStack stack, @NotNull LivingEntity victim, LivingEntity attacker) {
        victim.setSecondsOnFire(10);
        return super.onHitEntity(stack, victim, attacker);
    }

    public TextColor getColor() {
        return TextColor.parseColor("#ff8000");
    }

    /**
     * Called when this item is used when targetting a Block<br>
     * <b>Note: </b><i>Used for override only.</i>
     *
     * @param context the item use context. Containing information about the how, where and what is used the item.
     * @return the result of the item use override.
     */
    @Override
    public @NotNull InteractionResult onUseItem(UseOnContext context) {
        // Get player, dimension, block position and state.
        Player player = context.getPlayer();
        Level dimension = context.getLevel();
        BlockPos position = context.getClickedPos();
        BlockState state = dimension.getBlockState(position); // Block state in dimension of use at position of use.

        // Check if the state is a campfire, and the campfire at the block state can be lit.
        if (CampfireBlock.canLight(state)) {
            // Play sound.
            dimension.playSound(player, position, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1f, new Random().nextFloat() * .4f + .8f);

            // Set block state.
            dimension.setBlock(position, state.setValue(BlockStateProperties.LIT, Boolean.TRUE), 11);

            // Damage item.
            if (player != null) {
                context.getItemInHand().hurtAndBreak(1, player, (playerEntity) -> playerEntity.broadcastBreakEvent(context.getHand()));
            }

            // Return action result.
            return InteractionResult.sidedSuccess(dimension.isClientSide());
        } else {
            // Get position from the offset of the facing from the context.
            BlockPos offsetPos = position.relative(context.getClickedFace());

            // Check if block at the offset can be lit on fire.
            if (BaseFireBlock.canBePlacedAt(dimension, offsetPos, context.getHorizontalDirection())) {
                // Play sound.
                dimension.playSound(player, offsetPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1f, new Random().nextFloat() * .4f + .8f);

                // Create block state for the fire block.
                BlockState blockState1 = BaseFireBlock.getState(dimension, offsetPos);

                // Set block state.
                dimension.setBlock(offsetPos, blockState1, 11);

                // Get item stack from context.
                ItemStack stack = context.getItemInHand();
                if (player instanceof ServerPlayer) {
                    // Placed block criteria trigger.
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, offsetPos, stack);

                    // Damage item by 1.
                    stack.hurtAndBreak(1, player, (playerEntity) -> playerEntity.broadcastBreakEvent(context.getHand()));
                }

                return InteractionResult.sidedSuccess(dimension.isClientSide());
            } else {
                // Block at offset cannot be lit on fire.
                return InteractionResult.FAIL;
            }
        }
    }
}
