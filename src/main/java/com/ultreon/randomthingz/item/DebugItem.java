package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class DebugItem extends Item {
    public DebugItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;

        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockEntity tileEntity = level.getBlockEntity(pos);
        if (tileEntity != null) {
            tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(e -> {
                Component energyText = TextUtils.energyWithMax(e.getEnergyStored(), e.getMaxEnergyStored());
                player.sendMessage(new TextComponent("Energy: ").append(energyText), Util.NIL_UUID);
                player.sendMessage(new TextComponent("Receive/Extract: " + e.canReceive() + "/" + e.canExtract()), Util.NIL_UUID);
                player.sendMessage(new TextComponent(e.getClass().getName()).withStyle(ChatFormatting.ITALIC), Util.NIL_UUID);
            });

            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(e -> {
                ItemStack stackInSlot = e.getStackInSlot(0);
                String text = stackInSlot.getCount() + "x " + stackInSlot.getItem().getRegistryName();
                player.sendMessage(new TextComponent("Item Stack: ").append(new TextComponent(text)), Util.NIL_UUID);
                player.sendMessage(new TextComponent(e.getClass().getName()).withStyle(ChatFormatting.ITALIC), Util.NIL_UUID);
            });

            tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(e -> {
                FluidStack stackInSlot = e.getFluidInTank(0);
                String text = stackInSlot.getAmount() + "L of " + stackInSlot.getFluid().getRegistryName();
                player.sendMessage(new TextComponent("Fluid Stack: ").append(new TextComponent(text)), Util.NIL_UUID);
                player.sendMessage(new TextComponent(e.getClass().getName()).withStyle(ChatFormatting.ITALIC), Util.NIL_UUID);
            });

            for (Direction side : Direction.values()) {
                BlockEntity other = level.getBlockEntity(pos.relative(side));
                if (other != null) {
                    other.getCapability(CapabilityEnergy.ENERGY).ifPresent(e -> player.sendMessage(new TextComponent(side + ": " + other.getClass().getSimpleName()), Util.NIL_UUID));
                }
            }

            player.awardStat(Stats.ITEM_USED.get(this));

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
