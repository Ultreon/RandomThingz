package com.ultreon.randomthingz.util;

import lombok.experimental.UtilityClass;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

@UtilityClass
public final class ItemCapabilityUtils {
    public static void trySendItems(int maxSend, IItemHandler source, IItemHandler dest) {
        ItemStack toSend = source.extractItem(0, maxSend, false);
        int slot = 1;
        while (toSend.isEmpty()) {
            if (slot >= source.getSlots()) {
                break;
            }
            toSend = source.extractItem(slot, maxSend, true);
            slot++;
        }

        if (toSend.isEmpty()) {
            return;
        }

        ItemStack stack = toSend;
        for (int i = 0; i < dest.getSlots(); i++) {
            if (dest.isItemValid(i, stack)) {
                stack = dest.insertItem(i, stack, false);
            }
            if (!stack.isEmpty()) {
                source.extractItem(slot, stack.getCount(), false);
            }
        }
    }
    
    /**
     * Gets the energy capability for the block at the given position. If it does not have an energy
     * capability, or the block is not a tile entity, this returns null.
     *
     * @param dimension The dimension
     * @param pos   The position to check
     * @return The energy capability, or null if not present
     */
    @SuppressWarnings("ConstantConditions")
    @Nullable
    public static IItemHandler getItemHandler(IWorldReader dimension, BlockPos pos) {
        if (!dimension.isAreaLoaded(pos, 1)) return null;
        TileEntity tileEntity = dimension.getTileEntity(pos);
        return tileEntity != null ? tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null) : null;
    }

    /**
     * Gets the energy capability of the object (item, etc), or null if it does not have one.
     *
     * @param provider The capability provider
     * @return The energy capability, or null if not present
     */
    @SuppressWarnings("ConstantConditions")
    @Nullable
    public static IItemHandler getItemHandler(ICapabilityProvider provider) {
        return provider.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
    }

    /**
     * Gets the energy capability of the object (item, etc), or null if it does not have one. Tries
     * to get the capability for the given side first, then null side.
     *
     * @param provider The capability provider
     * @param side     The side being accessed
     * @return The energy capability, or null if not present
     */
    @SuppressWarnings("ConstantConditions")
    @Nullable
    public static IItemHandler getItemHandlerFromSideOrNull(ICapabilityProvider provider, Direction side) {
        return provider.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side).orElse(provider.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null));
    }
}
