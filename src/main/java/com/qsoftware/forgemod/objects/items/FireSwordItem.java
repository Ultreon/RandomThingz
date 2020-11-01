package com.qsoftware.forgemod.objects.items;

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
        PlayerEntity playerentity = context.getPlayer();
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockState blockstate = world.getBlockState(blockpos);
        if (CampfireBlock.canBeLit(blockstate)) {
            world.playSound(playerentity, blockpos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
            world.setBlockState(blockpos, blockstate.with(BlockStateProperties.LIT, Boolean.TRUE), 11);
            if (playerentity != null) {
                context.getItem().damageItem(1, playerentity, (p_219999_1_) -> p_219999_1_.sendBreakAnimation(context.getHand()));
            }

            return ActionResultType.func_233537_a_(world.isRemote());
        } else {
            BlockPos blockPos1 = blockpos.offset(context.getFace());
            if (AbstractFireBlock.canLightBlock(world, blockPos1, context.getPlacementHorizontalFacing())) {
                world.playSound(playerentity, blockPos1, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
                BlockState blockState1 = AbstractFireBlock.getFireForPlacement(world, blockPos1);
                world.setBlockState(blockPos1, blockState1, 11);
                ItemStack itemStack = context.getItem();
                if (playerentity instanceof ServerPlayerEntity) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerentity, blockPos1, itemStack);
                    itemStack.damageItem(1, playerentity, (p_219998_1_) -> p_219998_1_.sendBreakAnimation(context.getHand()));
                }

                return ActionResultType.func_233537_a_(world.isRemote());
            } else {
                return ActionResultType.FAIL;
            }
        }
    }
}
