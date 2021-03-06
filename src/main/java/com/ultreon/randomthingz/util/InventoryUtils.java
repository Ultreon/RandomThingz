package com.ultreon.randomthingz.util;

import com.ultreon.randomthingz.block.machines.refinery.RefineryBlockEntity;
import com.ultreon.randomthingz.item.CanisterItem;
import lombok.experimental.UtilityClass;
import net.minecraft.world.Container;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.function.Predicate;

@UtilityClass
public final class InventoryUtils {
    /**
     * Gets the total number of matching items in all slots in the inventory.
     *
     * @param inventory  The inventory
     * @param ingredient The items to match ({@linkplain net.minecraft.world.item.crafting.Ingredient}, etc.)
     * @return The number of items in all matching item stacks
     */
    public static int getTotalCount(Container inventory, Predicate<ItemStack> ingredient) {
        int total = 0;
        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && ingredient.test(stack)) {
                total += stack.getCount();
            }
        }
        return total;
    }

    /**
     * Consumes (removes) items from the inventory. This is useful for machines, which may have
     * multiple input slots and recipes that consume multiple of one item.
     *
     * @param inventory  The inventory
     * @param ingredient The items to match ({@linkplain net.minecraft.world.item.crafting.Ingredient}, etc.)
     * @param amount     The total number of items to remove
     */
    public static void consumeItems(Container inventory, Predicate<ItemStack> ingredient, int amount) {
        int amountLeft = amount;
        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && ingredient.test(stack)) {
                int toRemove = Math.min(amountLeft, stack.getCount());

                stack.shrink(toRemove);
                if (stack.isEmpty()) {
                    inventory.setItem(i, ItemStack.EMPTY);
                }

                amountLeft -= toRemove;
                if (amountLeft == 0) {
                    return;
                }
            }
        }
    }

    public static boolean canItemsStack(ItemStack a, ItemStack b) {
        // Determine if the item stacks can be merged
        if (a.isEmpty() || b.isEmpty()) return true;
        return ItemHandlerHelper.canItemStacksStack(a, b) && a.getCount() + b.getCount() <= a.getMaxStackSize();
    }

    public static boolean mergeItem(Container inventory, ItemStack stack, int slot) {
        ItemStack current = inventory.getItem(slot);
        if (current.isEmpty()) {
            inventory.setItem(slot, stack);
            return true;
        } else if (canItemsStack(stack, current)) {
            current.grow(stack.getCount());
            return true;
        }
        return false;
    }

    public static boolean isFilledFluidContainer(ItemStack stack) {
        Item item = stack.getItem();
        return (item instanceof BucketItem && ((BucketItem) item).getFluid() != Fluids.EMPTY)
                || (item instanceof CanisterItem && !((CanisterItem) item).getFluid(stack).isEmpty());
    }

    public static boolean isEmptyFluidContainer(ItemStack stack) {
        Item item = stack.getItem();
        return (item instanceof BucketItem && ((BucketItem) item).getFluid() == Fluids.EMPTY)
                || (item instanceof CanisterItem && ((CanisterItem) item).getFluid(stack).isEmpty());
    }

    public static boolean canFluidsStack(FluidStack stack, FluidStack output) {
        return output.isEmpty() || (output.isFluidEqual(stack) && output.getAmount() + stack.getAmount() <= RefineryBlockEntity.TANK_CAPACITY);
    }
}
