package com.qsoftware.forgemod.objects.items.tools;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * Fire sword item class.
 *
 * @author Qboi123
 */
public class FireSwordItem extends SwordItem {
    public FireSwordItem(ItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties properties) {
        super(tier, attackDamageIn, attackSpeedIn, properties);
    }

    @Override
    public boolean hitEntity(@NotNull ItemStack stack, LivingEntity target, @NotNull LivingEntity attacker) {
        target.setFire(10);
        return super.hitEntity(stack, target, attacker);
    }

    /**
     * Called when this item is used when targetting a Block
     */
    public @NotNull ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos position = context.getPos();
        BlockState state = world.getBlockState(position);
        if (CampfireBlock.canBeLit(state)) {
            world.playSound(player, position, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
            world.setBlockState(position, state.with(BlockStateProperties.LIT, Boolean.TRUE), 11);
            if (player != null) {
                context.getItem().damageItem(1, player, (playerEntity) -> playerEntity.sendBreakAnimation(context.getHand()));
            }

            return ActionResultType.func_233537_a_(world.isRemote());
        } else {
            BlockPos offsetPos = position.offset(context.getFace());
            if (AbstractFireBlock.canLightBlock(world, offsetPos, context.getPlacementHorizontalFacing())) {
                world.playSound(player, offsetPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
                BlockState blockState1 = AbstractFireBlock.getFireForPlacement(world, offsetPos);
                world.setBlockState(offsetPos, blockState1, 11);
                ItemStack stack = context.getItem();
                if (player instanceof ServerPlayerEntity) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, offsetPos, stack);
                    stack.damageItem(1, player, (playerEntity) -> playerEntity.sendBreakAnimation(context.getHand()));
                }

                return ActionResultType.func_233537_a_(world.isRemote());
            } else {
                return ActionResultType.FAIL;
            }
        }
    }
}
