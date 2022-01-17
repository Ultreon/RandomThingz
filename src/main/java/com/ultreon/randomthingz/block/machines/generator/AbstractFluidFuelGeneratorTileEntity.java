package com.ultreon.randomthingz.block.machines.generator;

import com.ultreon.modlib.api.FluidContainer;
import com.ultreon.modlib.api.RedstoneMode;
import com.ultreon.modlib.embedded.silentutils.EnumUtils;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.config.Config;
import com.ultreon.randomthingz.util.InventoryUtils;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
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

public abstract class AbstractFluidFuelGeneratorTileEntity extends AbstractGeneratorTileEntity {
    public static final int FIELDS_COUNT = 9;

    protected final FluidTank tank;
    protected final ContainerData fields = new ContainerData() {
        @SuppressWarnings("deprecation") // Use of Registry.FLUID
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
                    return burnTime;
                case 6:
                    return totalBurnTime;
                case 7:
                    return Registry.FLUID.getId(tank.getFluid().getFluid());
                case 8:
                    return tank.getFluidAmount();
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 4:
                    redstoneMode = EnumUtils.byOrdinal(value, RedstoneMode.IGNORED);
                    break;
                case 5:
                    burnTime = value;
                    break;
                case 6:
                    totalBurnTime = value;
                    break;
            }
        }

        @Override
        public int getCount() {
            return FIELDS_COUNT;
        }
    };
    private final LazyOptional<IFluidHandler> fluidHandlerCap;

    protected AbstractFluidFuelGeneratorTileEntity(BlockEntityType<?> typeIn, int inventorySize, int maxEnergy, int maxReceive, int maxExtract, FluidTank tankIn) {
        super(typeIn, inventorySize, maxEnergy, maxReceive, maxExtract, MachineTier.STANDARD);
        this.tank = tankIn;
        this.fluidHandlerCap = LazyOptional.of(() -> tank);
    }

    protected abstract int getFuelBurnTime(FluidStack fluid);

    protected void tryFillTank(ItemStack item) {
        FluidStack fluid = FluidContainer.getBucketOrContainerFluid(item);
        if (canAcceptFluidContainer(item, fluid)) {
            tank.fill(fluid, IFluidHandler.FluidAction.EXECUTE);

            ItemStack output = getItem(1);
            if (output.isEmpty()) {
                setItem(1, item.getContainerItem());
            } else {
                output.grow(1);
            }

            item.shrink(1);
        }
    }

    private boolean canAcceptFluidContainer(ItemStack item, FluidStack fluid) {
        ItemStack output = getItem(1);
        return !fluid.isEmpty()
                && tank.isFluidValid(0, fluid)
                && tank.fill(fluid, IFluidHandler.FluidAction.SIMULATE) == fluid.getAmount()
                && (output.isEmpty() || InventoryUtils.canItemsStack(item.getContainerItem(), output))
                && (output.isEmpty() || output.getCount() < output.getMaxStackSize());
    }

    @Override
    protected void consumeFuel() {
        FluidStack fluid = tank.drain(Config.fluidGeneratorInjectionVolume.get(), IFluidHandler.FluidAction.EXECUTE);
        burnTime = totalBurnTime = getFuelBurnTime(fluid);
    }

    @Override
    public void tick() {
        // Drain fluid containers into internal tank
        if (tank.getFluidAmount() < tank.getTankCapacity(0) - 999) {
            ItemStack stack = getItem(0);
            if (!stack.isEmpty()) {
                tryFillTank(stack);
            }
        }
        super.tick();
    }

    @Override
    public void load(BlockState state, CompoundTag tags) {
        super.load(state, tags);
        if (tags.contains("FluidTank")) {
            this.tank.setFluid(FluidStack.loadFluidStackFromNBT(tags.getCompound("FluidTank")));
        }
    }

    @Override
    public CompoundTag save(CompoundTag tags) {
        tags.put("FluidTank", this.tank.writeToNBT(new CompoundTag()));
        return super.save(tags);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        super.onDataPacket(net, packet);
        CompoundTag tags = packet.getTag();
        if (tags.contains("FluidTank")) {
            this.tank.setFluid(FluidStack.loadFluidStackFromNBT(tags.getCompound("FluidTank")));
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tags = super.getUpdateTag();
        tags.put("FluidTank", this.tank.writeToNBT(new CompoundTag()));
        return tags;
    }

    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidHandlerCap.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        fluidHandlerCap.invalidate();
    }
}
