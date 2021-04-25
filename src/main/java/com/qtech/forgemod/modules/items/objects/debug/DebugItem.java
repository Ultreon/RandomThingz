package com.qtech.forgemod.modules.items.objects.debug;

import com.qtech.forgemod.util.TextUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class DebugItem extends Item {
    public DebugItem() {
        super(new Properties().maxStackSize(1));
    }

    @Override
    public ActionResultType onUseItem(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null) return ActionResultType.PASS;

        World dimension = context.getDimension();
        BlockPos pos = context.getPos();
        TileEntity tileEntity = dimension.getTileEntity(pos);
        if (tileEntity != null) {
            tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(e -> {
                ITextComponent energyText = TextUtils.energyWithMax(e.getEnergyStored(), e.getMaxEnergyStored());
                player.sendMessage(new StringTextComponent("Energy: ").appendSibling(energyText), Util.DUMMY_UUID);
                player.sendMessage(new StringTextComponent("Receive/Extract: " + e.canReceive() + "/" + e.canExtract()), Util.DUMMY_UUID);
                player.sendMessage(new StringTextComponent(e.getClass().getName()).mergeStyle(TextFormatting.ITALIC), Util.DUMMY_UUID);
            });

            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(e -> {
                ItemStack stackInSlot = e.getStackInSlot(0);
                String text = stackInSlot.getCount() + "x " + stackInSlot.getItem().getRegistryName();
                player.sendMessage(new StringTextComponent("Item Stack: ").appendSibling(new StringTextComponent(text)), Util.DUMMY_UUID);
                player.sendMessage(new StringTextComponent(e.getClass().getName()).mergeStyle(TextFormatting.ITALIC), Util.DUMMY_UUID);
            });

            tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(e -> {
                FluidStack stackInSlot = e.getFluidInTank(0);
                String text = stackInSlot.getAmount() + "L of " + stackInSlot.getFluid().getRegistryName();
                player.sendMessage(new StringTextComponent("Fluid Stack: ").appendSibling(new StringTextComponent(text)), Util.DUMMY_UUID);
                player.sendMessage(new StringTextComponent(e.getClass().getName()).mergeStyle(TextFormatting.ITALIC), Util.DUMMY_UUID);
            });

            for (Direction side : Direction.values()) {
                TileEntity other = dimension.getTileEntity(pos.offset(side));
                if (other != null) {
                    other.getCapability(CapabilityEnergy.ENERGY).ifPresent(e -> {
                        player.sendMessage(new StringTextComponent(side + ": " + other.getClass().getSimpleName()), Util.DUMMY_UUID);
                    });
                }
            }

            player.addStat(Stats.ITEM_USED.get(this));

            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
    }
}
