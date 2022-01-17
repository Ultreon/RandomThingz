package com.ultreon.randomthingz.item.magic;

import com.ultreon.randomthingz.common.enums.TextColors;
import com.ultreon.randomthingz.common.item.ModCreativeTabs;
import com.ultreon.randomthingz.util.helpers.KeyboardHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;
import java.util.List;

/**
 * Walking staff item class.
 *
 * @author Qboi123
 */
public class WalkingStaffItem extends Item {
    public WalkingStaffItem() {
        super(new Item.Properties().tab(ModCreativeTabs.OVERPOWERED).rarity(Rarity.EPIC));
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        if (KeyboardHelper.isHoldingShift()) {
            tooltip.add(new TextComponent("Test Information"));
        } else {
            tooltip.add(new TextComponent(TextColors.YELLOW.toString() + TextColors.BOLD + "Hold SHIFT for more information."));
        }
        super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
    }

    @Override
    public @NotNull
    InteractionResultHolder<ItemStack> use(@NotNull Level dimensionIn, Player playerIn, @NotNull InteractionHand handIn) {
        playerIn.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 5, 4, false, false));
        return super.use(dimensionIn, playerIn, handIn);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        Level dimension = player.getCommandSenderWorld();
        System.out.println("UsingTick");

        if (dimension.getBlockState(player.blockPosition().below()).getBlock() == Blocks.AIR || dimension.getBlockState(player.blockPosition().below()).getBlock() == Blocks.CAVE_AIR || dimension.getBlockState(player.blockPosition().below()).getBlock() == Blocks.LAVA || dimension.getBlockState(player.blockPosition().below()).getBlock() == Blocks.WATER) {
            if (KeyboardHelper.isHoldingAlt()) {
                if (dimension.getBlockState(player.blockPosition().below().below()).getBlock() == Blocks.AIR || dimension.getBlockState(player.blockPosition().below().below()).getBlock() == Blocks.CAVE_AIR || dimension.getBlockState(player.blockPosition().below()).getBlock() == Blocks.LAVA || dimension.getBlockState(player.blockPosition().below()).getBlock() == Blocks.WATER) {
                    dimension.setBlockAndUpdate(player.blockPosition().below().below(), Blocks.COBBLESTONE.defaultBlockState());
                }
            } else {
                dimension.setBlockAndUpdate(player.blockPosition().below(), Blocks.COBBLESTONE.defaultBlockState());
            }
        }
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level dimensionIn, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof Player) {
            Player player = (Player) entityIn;
            if (isSelected) {
                if (dimensionIn.getBlockState(player.blockPosition().below()).getBlock() == Blocks.AIR || dimensionIn.getBlockState(player.blockPosition().below()).getBlock() == Blocks.CAVE_AIR || dimensionIn.getBlockState(player.blockPosition().below()).getBlock() == Blocks.LAVA || dimensionIn.getBlockState(player.blockPosition().below()).getBlock() == Blocks.WATER) {
                    if (KeyboardHelper.isHoldingAlt()) {
                        if (dimensionIn.getBlockState(player.blockPosition().below().below()).getBlock() == Blocks.AIR || dimensionIn.getBlockState(player.blockPosition().below().below()).getBlock() == Blocks.CAVE_AIR || dimensionIn.getBlockState(player.blockPosition().below()).getBlock() == Blocks.LAVA || dimensionIn.getBlockState(player.blockPosition().below()).getBlock() == Blocks.WATER) {
                            dimensionIn.setBlockAndUpdate(player.blockPosition().below().below(), Blocks.COBBLESTONE.defaultBlockState());
                        }
                    } else {
                        dimensionIn.setBlockAndUpdate(player.blockPosition().below(), Blocks.COBBLESTONE.defaultBlockState());
                    }
                }
            }
        }
        super.inventoryTick(stack, dimensionIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        entity.level.setBlockAndUpdate(entity.blockPosition().below(), Blocks.BEDROCK.defaultBlockState());
        return false;
    }
}
