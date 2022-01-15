package com.ultreon.randomthingz.item;

import com.qsoftware.modlib.api.IFluidContainer;
import com.qsoftware.modlib.silentlib.util.PlayerUtils;
import com.ultreon.randomthingz.common.enums.TextColors;
import com.ultreon.randomthingz.item.energy.EnergyStoringItem;
import com.ultreon.randomthingz.util.EnergyUtils;
import com.ultreon.randomthingz.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
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
        super(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1), MAX_ENERGY, MAX_RECEIVE, ENERGY_PER_OPERATION);
    }

    private static InteractionResult tryExtractFromTank(Player player, IEnergyStorage energy, IFluidHandler fluidHandler) {
        ItemStack emptyContainer = takeFluidContainer(player);
        if (!emptyContainer.isEmpty()) {
            for (int i = 0; i < fluidHandler.getTanks(); ++i) {
                FluidStack fluidInTank = fluidHandler.getFluidInTank(i);
                if (fluidInTank.getAmount() > 999) {
                    FluidStack fluidStack = new FluidStack(fluidInTank.getFluid(), 1000);
                    if (fluidHandler.drain(fluidStack, IFluidHandler.FluidAction.SIMULATE).getAmount() == 1000) {
                        fluidHandler.drain(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                        giveFilledContainer(player, energy, emptyContainer, fluidStack);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    private static void giveFilledContainer(Player player, IEnergyStorage energy, ItemStack emptyContainer, FluidStack fluidStack) {
        ItemStack filledContainer = IFluidContainer.fillBucketOrFluidContainer(emptyContainer, fluidStack);
        energy.extractEnergy(ENERGY_PER_OPERATION, false);
        PlayerUtils.giveItem(player, filledContainer);
    }

    private static ItemStack takeFluidContainer(Player player) {
        for (int i = 0; i < player.inventory.items.size(); ++i) {
            ItemStack stack = player.inventory.getItem(i);
            if (InventoryUtils.isEmptyFluidContainer(stack)) {
                ItemStack split = stack.split(1);
                if (stack.isEmpty()) {
                    player.inventory.setItem(i, ItemStack.EMPTY);
                }
                return split;
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;

        IEnergyStorage energy = EnergyUtils.getEnergy(context.getItemInHand());
        if (energy == null) return InteractionResult.PASS;

        if (energy.getEnergyStored() < ENERGY_PER_OPERATION) {
            player.displayClientMessage(new TextComponent(TextColors.RED + "No energy left!"), true);
            return InteractionResult.FAIL;
        }

        Level dimension = context.getLevel();

        // Try to pull fluid from machines
        BlockPos pos = context.getClickedPos();
        BlockEntity tileEntity = dimension.getBlockEntity(pos);

        if (tileEntity != null) {
            LazyOptional<IFluidHandler> lazyOptional = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
            if (lazyOptional.isPresent()) {
                IFluidHandler fluidHandler = lazyOptional.orElseThrow(IllegalStateException::new);
                return tryExtractFromTank(player, energy, fluidHandler);
            }
        }

        // Or pickup fluids from the dimension
        BlockPos posOpposite = context.getClickedPos().relative(context.getClickedFace());
        BlockState state = dimension.getBlockState(posOpposite);

        if (state.getBlock() instanceof BucketPickup) {
            ItemStack emptyContainer = takeFluidContainer(player);
            if (!emptyContainer.isEmpty()) {
                Fluid fluid = ((BucketPickup) state.getBlock()).takeLiquid(dimension, posOpposite, state);
                FluidStack fluidStack = new FluidStack(fluid, 1000);
                if (!fluidStack.isEmpty()) {
                    giveFilledContainer(player, energy, emptyContainer, fluidStack);
                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
    }
}
