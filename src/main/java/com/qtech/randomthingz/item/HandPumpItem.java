package com.qtech.randomthingz.item;

import com.qsoftware.modlib.api.IFluidContainer;
import com.qsoftware.modlib.silentlib.util.PlayerUtils;
import com.qtech.randomthingz.commons.enums.TextColors;
import com.qtech.randomthingz.item.energy.EnergyStoringItem;
import com.qtech.randomthingz.util.EnergyUtils;
import com.qtech.randomthingz.util.InventoryUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class HandPumpItem extends EnergyStoringItem {
    private static final int MAX_ENERGY = 100_000;
    private static final int MAX_RECEIVE = 100;
    private static final int ENERGY_PER_OPERATION = 500;

    public HandPumpItem() {
        super(new Properties().group(ItemGroup.TOOLS).maxStackSize(1), MAX_ENERGY, MAX_RECEIVE, ENERGY_PER_OPERATION);
    }

    private static ActionResultType tryExtractFromTank(PlayerEntity player, IEnergyStorage energy, IFluidHandler fluidHandler) {
        ItemStack emptyContainer = takeFluidContainer(player);
        if (!emptyContainer.isEmpty()) {
            for (int i = 0; i < fluidHandler.getTanks(); ++i) {
                FluidStack fluidInTank = fluidHandler.getFluidInTank(i);
                if (fluidInTank.getAmount() > 999) {
                    FluidStack fluidStack = new FluidStack(fluidInTank.getFluid(), 1000);
                    if (fluidHandler.drain(fluidStack, IFluidHandler.FluidAction.SIMULATE).getAmount() == 1000) {
                        fluidHandler.drain(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                        giveFilledContainer(player, energy, emptyContainer, fluidStack);
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        }
        return ActionResultType.PASS;
    }

    private static void giveFilledContainer(PlayerEntity player, IEnergyStorage energy, ItemStack emptyContainer, FluidStack fluidStack) {
        ItemStack filledContainer = IFluidContainer.fillBucketOrFluidContainer(emptyContainer, fluidStack);
        energy.extractEnergy(ENERGY_PER_OPERATION, false);
        PlayerUtils.giveItem(player, filledContainer);
    }

    private static ItemStack takeFluidContainer(PlayerEntity player) {
        for (int i = 0; i < player.inventory.mainInventory.size(); ++i) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (InventoryUtils.isEmptyFluidContainer(stack)) {
                ItemStack split = stack.split(1);
                if (stack.isEmpty()) {
                    player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                }
                return split;
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public ActionResultType onUseItem(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null) return ActionResultType.PASS;

        IEnergyStorage energy = EnergyUtils.getEnergy(context.getItem());
        if (energy == null) return ActionResultType.PASS;

        if (energy.getEnergyStored() < ENERGY_PER_OPERATION) {
            player.sendStatusMessage(new StringTextComponent(TextColors.RED + "No energy left!"), true);
            return ActionResultType.FAIL;
        }

        World dimension = context.getDimension();

        // Try to pull fluid from machines
        BlockPos pos = context.getPos();
        TileEntity tileEntity = dimension.getTileEntity(pos);

        if (tileEntity != null) {
            LazyOptional<IFluidHandler> lazyOptional = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
            if (lazyOptional.isPresent()) {
                IFluidHandler fluidHandler = lazyOptional.orElseThrow(IllegalStateException::new);
                return tryExtractFromTank(player, energy, fluidHandler);
            }
        }

        // Or pickup fluids from the dimension
        BlockPos posOpposite = context.getPos().offset(context.getFace());
        BlockState state = dimension.getBlockState(posOpposite);

        if (state.getBlock() instanceof IBucketPickupHandler) {
            ItemStack emptyContainer = takeFluidContainer(player);
            if (!emptyContainer.isEmpty()) {
                Fluid fluid = ((IBucketPickupHandler) state.getBlock()).pickupFluid(dimension, posOpposite, state);
                FluidStack fluidStack = new FluidStack(fluid, 1000);
                if (!fluidStack.isEmpty()) {
                    giveFilledContainer(player, energy, emptyContainer, fluidStack);
                    player.addStat(Stats.ITEM_USED.get(this));
                    return ActionResultType.SUCCESS;
                }
            }
        }

        return ActionResultType.PASS;
    }
}
