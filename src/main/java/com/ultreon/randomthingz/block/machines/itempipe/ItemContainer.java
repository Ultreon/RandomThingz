package com.ultreon.randomthingz.block.machines.itempipe;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class ItemContainer implements IItemHandler {
    protected Predicate<ItemStack> validator;
    @NotNull
    protected ItemStack stack = ItemStack.EMPTY;
    protected int capacity;

    public ItemContainer(int capacity) {
        this(capacity, e -> true);
    }

    public ItemContainer(int capacity, Predicate<ItemStack> validator) {
        this.capacity = capacity;
        this.validator = validator;
    }

    public ItemContainer setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public ItemContainer setValidator(Predicate<ItemStack> validator) {
        if (validator != null) {
            this.validator = validator;
        }
        return this;
    }

    public boolean isItemValid(ItemStack stack) {
        return validator.test(stack);
    }

    public int getCapacity() {
        return capacity;
    }

    @NotNull
    public @NotNull ItemStack getStack() {
        return stack;
    }

    public int getFluidAmount() {
        return stack.getCount();
    }

    public ItemContainer readFromNBT(CompoundTag nbt) {
        ItemStack stack = ItemStack.of(nbt);
        setStack(stack);
        return this;
    }

    public CompoundTag writeToNBT(CompoundTag nbt) {
        stack.save(nbt);
        return nbt;
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @NotNull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return getStack();
    }

    @Override
    public int getSlotLimit(int slot) {
        return getCapacity();
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return isItemValid(stack);
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (stack.isEmpty() || !isItemValid(stack)) {
            return ItemStack.EMPTY;
        }
        if (simulate) {
            if (this.stack.isEmpty()) {
                return ItemStack.EMPTY;
            }
            if (!this.stack.sameItem(stack)) {
                return ItemStack.EMPTY;
            }
            return new ItemStack(stack.getItem(), Math.min(capacity - this.stack.getCount(), stack.getCount()));
        }
        if (this.stack.isEmpty()) {
            this.stack = new ItemStack(stack.getItem(), Math.min(capacity, stack.getCount()));
            onContentsChanged();
            return this.stack;
        }
        if (!this.stack.sameItem(stack)) {
            return ItemStack.EMPTY;
        }
        int filled = capacity - this.stack.getCount();

        if (stack.getCount() < filled) {
            this.stack.grow(stack.getCount());
            filled = stack.getCount();
        } else {
            this.stack.setCount(capacity);
        }
        if (filled > 0)
            onContentsChanged();
        return new ItemStack(this.stack.getItem(), filled);
    }

//    @NotNull
//    @Override
//    public FluidStack extractItem(FluidStack resource, IItemHandler.FluidAction action)
//    {
//        if (resource.isEmpty() || !resource.isFluidEqual(stack))
//        {
//            return FluidStack.EMPTY;
//        }
//        return drain(resource.getAmount(), action);
//    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        int drained = amount;
        if (stack.getCount() < drained) {
            drained = stack.getCount();
        }
        ItemStack stack = new ItemStack(this.stack.getItem(), drained);
        if (!simulate && drained > 0) {
            this.stack.shrink(drained);
            onContentsChanged();
        }
        return stack;
    }

    protected void onContentsChanged() {

    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int getSpace() {
        return Math.max(0, capacity - stack.getCount());
    }

}
