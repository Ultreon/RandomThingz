package com.qsoftware.forgemod.objects.items;

import com.qsoftware.forgemod.groups.Groups;
import com.qsoftware.forgemod.common.TextColors;
import com.qsoftware.forgemod.util.helpers.KeyboardHelper;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

public class WandItem extends Item {
    public WandItem() {
        super(new Item.Properties().group(Groups.OVERPOWERED));
    }

    @Override
    public boolean hasEffect(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
        if (KeyboardHelper.isHoldingShift()) {
            tooltip.add(new StringTextComponent("Test Information"));
        } else {
            tooltip.add(new StringTextComponent(TextColors.YELLOW.toString() + TextColors.BOLD + "Hold SHIFT for more information."));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, PlayerEntity playerIn, @NotNull Hand handIn) {
        playerIn.addPotionEffect(new EffectInstance(Effects.SPEED, 5, 4, false, false));
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        World world = player.getEntityWorld();
        System.out.println("UsingTick");

        if (world.getBlockState(player.getPosition().down()).getBlock() == Blocks.AIR || world.getBlockState(player.getPosition().down()).getBlock() == Blocks.CAVE_AIR || world.getBlockState(player.getPosition().down()).getBlock() == Blocks.LAVA || world.getBlockState(player.getPosition().down()).getBlock() == Blocks.WATER) {
            if (KeyboardHelper.isHoldingAlt()) {
                if (world.getBlockState(player.getPosition().down().down()).getBlock() == Blocks.AIR || world.getBlockState(player.getPosition().down().down()).getBlock() == Blocks.CAVE_AIR || world.getBlockState(player.getPosition().down()).getBlock() == Blocks.LAVA || world.getBlockState(player.getPosition().down()).getBlock() == Blocks.WATER) {
                    world.setBlockState(player.getPosition().down().down(), Blocks.COBBLESTONE.getDefaultState());
                }
            } else {
                world.setBlockState(player.getPosition().down(), Blocks.COBBLESTONE.getDefaultState());
            }
        }
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull World worldIn, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityIn;
            if (isSelected) {
                if (worldIn.getBlockState(player.getPosition().down()).getBlock() == Blocks.AIR || worldIn.getBlockState(player.getPosition().down()).getBlock() == Blocks.CAVE_AIR || worldIn.getBlockState(player.getPosition().down()).getBlock() == Blocks.LAVA || worldIn.getBlockState(player.getPosition().down()).getBlock() == Blocks.WATER) {
                    if (KeyboardHelper.isHoldingAlt()) {
                        if (worldIn.getBlockState(player.getPosition().down().down()).getBlock() == Blocks.AIR || worldIn.getBlockState(player.getPosition().down().down()).getBlock() == Blocks.CAVE_AIR || worldIn.getBlockState(player.getPosition().down()).getBlock() == Blocks.LAVA || worldIn.getBlockState(player.getPosition().down()).getBlock() == Blocks.WATER) {
                            worldIn.setBlockState(player.getPosition().down().down(), Blocks.COBBLESTONE.getDefaultState());
                        }
                    } else {
                        worldIn.setBlockState(player.getPosition().down(), Blocks.COBBLESTONE.getDefaultState());
                    }
                }
            }
        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        entity.world.setBlockState(entity.getPosition().down(), Blocks.BEDROCK.getDefaultState());
        return false;
    }
}
