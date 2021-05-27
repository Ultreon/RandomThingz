package com.qtech.randomthingz.item.wand;

import com.qtech.randomthingz.commons.enums.TextColors;
import com.qtech.randomthingz.modules.ui.ModItemGroups;
import com.qtech.randomthingz.util.helpers.KeyboardHelper;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Walking staff item class.
 *
 * @author Qboi123
 */
public class WalkingStaffItem extends Item {
    public WalkingStaffItem() {
        super(new Item.Properties().group(ModItemGroups.OVERPOWERED).rarity(Rarity.EPIC));
    }

    @Override
    public boolean hasEnchantmentGlint(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable World dimensionIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
        if (KeyboardHelper.isHoldingShift()) {
            tooltip.add(new StringTextComponent("Test Information"));
        } else {
            tooltip.add(new StringTextComponent(TextColors.YELLOW.toString() + TextColors.BOLD + "Hold SHIFT for more information."));
        }
        super.addInformation(stack, dimensionIn, tooltip, flagIn);
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World dimensionIn, PlayerEntity playerIn, @NotNull Hand handIn) {
        playerIn.addPotionEffect(new EffectInstance(Effects.SPEED, 5, 4, false, false));
        return super.onItemRightClick(dimensionIn, playerIn, handIn);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        World dimension = player.getEntityDimension();
        System.out.println("UsingTick");

        if (dimension.getBlockState(player.getPosition().down()).getBlock() == Blocks.AIR || dimension.getBlockState(player.getPosition().down()).getBlock() == Blocks.CAVE_AIR || dimension.getBlockState(player.getPosition().down()).getBlock() == Blocks.LAVA || dimension.getBlockState(player.getPosition().down()).getBlock() == Blocks.WATER) {
            if (KeyboardHelper.isHoldingAlt()) {
                if (dimension.getBlockState(player.getPosition().down().down()).getBlock() == Blocks.AIR || dimension.getBlockState(player.getPosition().down().down()).getBlock() == Blocks.CAVE_AIR || dimension.getBlockState(player.getPosition().down()).getBlock() == Blocks.LAVA || dimension.getBlockState(player.getPosition().down()).getBlock() == Blocks.WATER) {
                    dimension.setBlockState(player.getPosition().down().down(), Blocks.COBBLESTONE.getDefaultState());
                }
            } else {
                dimension.setBlockState(player.getPosition().down(), Blocks.COBBLESTONE.getDefaultState());
            }
        }
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull World dimensionIn, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityIn;
            if (isSelected) {
                if (dimensionIn.getBlockState(player.getPosition().down()).getBlock() == Blocks.AIR || dimensionIn.getBlockState(player.getPosition().down()).getBlock() == Blocks.CAVE_AIR || dimensionIn.getBlockState(player.getPosition().down()).getBlock() == Blocks.LAVA || dimensionIn.getBlockState(player.getPosition().down()).getBlock() == Blocks.WATER) {
                    if (KeyboardHelper.isHoldingAlt()) {
                        if (dimensionIn.getBlockState(player.getPosition().down().down()).getBlock() == Blocks.AIR || dimensionIn.getBlockState(player.getPosition().down().down()).getBlock() == Blocks.CAVE_AIR || dimensionIn.getBlockState(player.getPosition().down()).getBlock() == Blocks.LAVA || dimensionIn.getBlockState(player.getPosition().down()).getBlock() == Blocks.WATER) {
                            dimensionIn.setBlockState(player.getPosition().down().down(), Blocks.COBBLESTONE.getDefaultState());
                        }
                    } else {
                        dimensionIn.setBlockState(player.getPosition().down(), Blocks.COBBLESTONE.getDefaultState());
                    }
                }
            }
        }
        super.inventoryTick(stack, dimensionIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        entity.dimension.setBlockState(entity.getPosition().down(), Blocks.BEDROCK.getDefaultState());
        return false;
    }
}
