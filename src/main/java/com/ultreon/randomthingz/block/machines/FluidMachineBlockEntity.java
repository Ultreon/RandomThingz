package com.ultreon.randomthingz.block.machines;

import com.ultreon.modlib.api.RedstoneMode;
import com.ultreon.modlib.api.crafting.recipe.fluid.FluidInventory;
import com.ultreon.modlib.api.crafting.recipe.fluid.FluidRecipe;
import com.ultreon.modlib.api.crafting.recipe.fluid.FluidIngredient;
import com.ultreon.modlib.silentutils.EnumUtils;
import com.ultreon.randomthingz.common.enums.MachineTier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.IntStream;

public abstract class FluidMachineBlockEntity<R extends FluidRecipe<?>> extends MachineBlockEntity<R> implements FluidInventory {
    protected final FluidTank[] tanks;
    protected final ContainerData fields = new ContainerData() {
        @SuppressWarnings("deprecation") // Use of Registry
        @Override
        public int get(int index) {
            switch (index) {
                //Minecraft actually sends fields as shorts, so we need to split energy into 2 fields
                case 0:
                    // Energy lower bytes
                    return getEnergyStored() & 0xFFFF;
                case 1:
                    // Energy upper bytes
                    return (getEnergyStored() >> 16) & 0xFFFF;
                case 2:
                    return getMaxEnergyStored() & 0xFFFF;
                case 3:
                    return (getMaxEnergyStored() >> 16) & 0xFFFF;
                case 4:
                    return redstoneMode.ordinal();
                case 5:
                    return tier.getUpgradeSlots();
                case 6:
                    return (int) progress;
                case 7:
                    return processTime;
                default:
                    int tankIndex = (index - 7) / 2;
                    if (tankIndex >= tanks.length) {
                        return 0;
                    } else if (index % 2 == 1) {
                        return Registry.FLUID.getId(tanks[tankIndex].getFluid().getFluid());
                    } else {
                        return tanks[tankIndex].getFluidAmount();
                    }
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 4 -> redstoneMode = EnumUtils.byOrdinal(value, RedstoneMode.IGNORED);
                case 6 -> progress = value;
                case 7 -> processTime = value;
            }
        }

        @Override
        public int getCount() {
            return 8 + 2 * tanks.length;
        }
    };
    private final LazyOptional<IFluidHandler> fluidHandlerCap;

    protected FluidMachineBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state, int inventorySize, int tankCount, int tankCapacity, MachineTier tier) {
        super(typeIn, pos, state, inventorySize, tier);
        this.tanks = IntStream.range(0, tankCount).mapToObj(k -> new FluidTank(tankCapacity)).toArray(FluidTank[]::new);
        this.fluidHandlerCap = LazyOptional.of(() -> this);
    }

    protected abstract int getInputTanks();

    protected abstract int getOutputTanks();

    protected abstract Collection<FluidStack> getFluidResults(R recipe);

    /**
     * Get all possible results of processing this recipe. Override if recipes can contain a
     * variable number of outputs.
     *
     * @param recipe The recipe
     * @return All possible results of the processing operation
     */
    protected Collection<FluidStack> getPossibleFluidResults(R recipe) {
        return getFluidResults(recipe);
    }

    @Override
    protected Collection<ItemStack> getProcessResults(R recipe) {
        return Collections.emptyList();
    }

    @Override
    protected int[] getOutputSlots() {
        return new int[0];
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide) return;

        R recipe = getRecipe();
        if (recipe != null && canMachineRun(recipe)) {
            // Process
            processTime = getProcessTime(recipe);
            progress += getProcessSpeed();
            energy.consumeEnergy((int) (getEnergyUsedPerTick() * getUpgradesEnergyMultiplier()));

            if (progress >= processTime) {
                // Create result
                getFluidResults(recipe).forEach(this::storeResultFluid);
                getProcessResults(recipe).forEach(this::storeResultItem);
                consumeFeedstock(recipe);
                consumeIngredients(recipe);
                progress = 0;

                if (getRecipe() == null) {
                    setInactiveState();
                }
            } else {
                sendUpdate(getActiveState(level.getBlockState(worldPosition)));
            }
        } else {
            if (recipe == null) {
                progress = 0;
            }
            setInactiveState();
        }
    }

    private boolean canMachineRun(R recipe) {
        return level != null
                && getEnergyStored() >= getEnergyUsedPerTick()
                && hasRoomInOutputTank(getPossibleFluidResults(recipe))
                && hasRoomInOutput(getPossibleProcessResult(recipe))
                && redstoneMode.shouldRun(level.getBestNeighborSignal(worldPosition) > 0);
    }

    private boolean hasRoomInOutputTank(Iterable<FluidStack> results) {
        for (FluidStack stack : results) {
            if (!hasRoomForOutputFluid(stack)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasRoomForOutputFluid(FluidStack stack) {
        final int inputs = getInputTanks();
        final int outputs = getOutputTanks();
        for (int i = inputs; i < inputs + outputs; ++i) {
            if (tanks[i].fill(stack, IFluidHandler.FluidAction.SIMULATE) == stack.getAmount()) {
                return true;
            }
        }
        return false;
    }

    private void storeResultFluid(FluidStack stack) {
        final int inputs = getInputTanks();
        final int outputs = getOutputTanks();
        // Merge the fluid into any output tank it can fit in
        for (int i = inputs; i < inputs + outputs; ++i) {
            if (tanks[i].fill(stack, IFluidHandler.FluidAction.SIMULATE) == stack.getAmount()) {
                tanks[i].fill(stack, IFluidHandler.FluidAction.EXECUTE);
                break;
            }
        }
    }

    private void consumeFeedstock(R recipe) {
        for (FluidIngredient ingredient : recipe.getFluidIngredients()) {
            for (int i = 0; i < getInputTanks(); ++i) {
                if (ingredient.test(getFluidInTank(i))) {
                    tanks[i].drain(ingredient.getAmount(), IFluidHandler.FluidAction.EXECUTE);
                    break;
                }
            }
        }
    }

    @Override
    protected abstract void consumeIngredients(R recipe);

    @Override
    public void setRemoved() {
        super.setRemoved();
        fluidHandlerCap.invalidate();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!this.remove && cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.orEmpty(cap, fluidHandlerCap.cast());
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void load(CompoundTag tags) {
        ListTag list = tags.getList("Tanks", 10);
        for (int i = 0; i < tanks.length && i < list.size(); ++i) {
            Tag nbt = list.get(i);
            tanks[i].setFluid(FluidStack.loadFluidStackFromNBT((CompoundTag) nbt));
        }
        super.load(tags);
    }

    @Override
    public CompoundTag save(CompoundTag tags) {
        ListTag list = new ListTag();
        for (FluidTank tank : tanks) {
            list.add(tank.writeToNBT(new CompoundTag()));
        }
        tags.put("Tanks", list);
        return super.save(tags);
    }

    @Override
    public int getTanks() {
        return tanks.length;
    }

    @NotNull
    @Override
    public FluidStack getFluidInTank(int tank) {
        if (tank < 0 || tank >= tanks.length) {
            return FluidStack.EMPTY;
        }
        return tanks[tank].getFluid();
    }

    @Override
    public int getTankCapacity(int tank) {
        if (tank < 0 || tank >= tanks.length) {
            return 0;
        }
        return tanks[tank].getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        if (tank < 0 || tank >= tanks.length) {
            return false;
        }
        return tanks[tank].isFluidValid(stack);
    }

    @Override
    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        for (int i = 0; i < getInputTanks(); ++i) {
            FluidStack fluidInTank = tanks[i].getFluid();
            if (isFluidValid(i, resource) && (fluidInTank.isEmpty() || resource.isFluidEqual(fluidInTank))) {
                return tanks[i].fill(resource, action);
            }
        }
        return 0;
    }

    @NotNull
    @Override
    public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        if (resource.isEmpty()) {
            return FluidStack.EMPTY;
        }

        for (int i = getInputTanks(); i < getInputTanks() + getOutputTanks(); ++i) {
            if (resource.isFluidEqual(tanks[i].getFluid())) {
                return tanks[i].drain(resource, action);
            }
        }

        return FluidStack.EMPTY;
    }

    @NotNull
    @Override
    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        for (int i = getInputTanks(); i < getInputTanks() + getOutputTanks(); ++i) {
            if (tanks[i].getFluidAmount() > 0) {
                return tanks[i].drain(maxDrain, action);
            }
        }

        return FluidStack.EMPTY;
    }
}
