package com.ultreon.randomthingz.block.machines.pump;

import com.qsoftware.modlib.api.IFluidContainer;
import com.qsoftware.modlib.api.RedstoneMode;
import com.qsoftware.modlib.silentlib.util.TimeUtils;
import com.qsoftware.modlib.silentutils.EnumUtils;
import com.ultreon.randomthingz.block.entity.ModMachineTileEntities;
import com.ultreon.randomthingz.block.machines.AbstractMachineBaseTileEntity;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.item.upgrade.MachineUpgrades;
import com.ultreon.randomthingz.util.Constants;
import com.ultreon.randomthingz.util.InventoryUtils;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PumpTileEntity extends AbstractMachineBaseTileEntity {
    public static final int ENERGY_PER_BUCKET = 500;
    public static final int PUMP_DELAY = TimeUtils.ticksFromSeconds(5);
    public static final int FIELDS_COUNT = 9;
    private final FluidTank tank = new FluidTank(4000);
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
                case 7:
                    return Registry.FLUID.getId(tank.getFluid().getFluid());
                case 8:
                    return tank.getFluid().getAmount();
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
            }
        }

        @Override
        public int getCount() {
            return FIELDS_COUNT;
        }
    };
    private final LazyOptional<IFluidHandler> fluidCap = LazyOptional.of(() -> tank);

    public PumpTileEntity() {
        super(ModMachineTileEntities.pump, 2, 10_000, 100, 0, MachineTier.STANDARD);
    }

    private int getHorizontalRange() {
        return 3 + getUpgradeCount(MachineUpgrades.RANGE) * Constants.UPGRADE_RANGE_AMOUNT;
    }

    private int getVerticalRange() {
        return 64;
    }

    private int getEnergyPerOperation() {
        return (int) (ENERGY_PER_BUCKET * getUpgradesEnergyMultiplier());
    }

    private int getPumpDelay() {
        int upgrades = getUpgradeCount(MachineUpgrades.PROCESSING_SPEED);
        return (int) (PUMP_DELAY / (1f + upgrades * Constants.UPGRADE_PROCESSING_SPEED_AMOUNT));
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide) return;

        tryFillFluidContainer();

        // Only pump fluids occasionally
        if (!canMachineRun() || level.getGameTime() % getPumpDelay() != 0) return;

        // TODO: Could probably optimize this to not iterate over the entire region each time
        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
        for (int y = worldPosition.getY(); y > Math.max(0, worldPosition.getY() - getVerticalRange()); --y) {
            int range = getHorizontalRange();
            for (int x = worldPosition.getX() - range; x <= worldPosition.getX() + range; ++x) {
                for (int z = worldPosition.getZ() - range; z <= worldPosition.getZ() + range; ++z) {
                    blockPos.set(x, y, z);
                    BlockState state = level.getBlockState(blockPos);
                    if (tryPumpFluid(blockPos, x, y, z, state)) {
                        return;
                    }
                }
            }
        }
    }

    private boolean tryPumpFluid(BlockPos.MutableBlockPos blockPos, int x, int y, int z, BlockState state) {
        if (state.getBlock() instanceof BucketPickup) {
            assert level != null;
            Fluid fluid = ((BucketPickup) state.getBlock()).takeLiquid(level, blockPos, state);
            FluidStack fluidStack = new FluidStack(fluid, 1000);

            if (!fluidStack.isEmpty() && tank.fill(fluidStack, IFluidHandler.FluidAction.SIMULATE) == 1000) {
                tank.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                energy.consumeEnergy(getEnergyPerOperation());
                return true;
            }
        }
        return false;
    }

    private void tryFillFluidContainer() {
        // Fill empty fluid containers with output fluids
        ItemStack input = getItem(0);
        if (input.isEmpty()) return;

        FluidStack fluidInInput = IFluidContainer.getBucketOrContainerFluid(input);
        if (!fluidInInput.isEmpty()) return;

        FluidStack fluidInTank = tank.getFluidInTank(0);
        if (fluidInTank.getAmount() >= 1000) {
            ItemStack filled = IFluidContainer.fillBucketOrFluidContainer(input, fluidInTank);
            if (!filled.isEmpty() && InventoryUtils.mergeItem(this, filled, 1)) {
                tank.drain(1000, IFluidHandler.FluidAction.EXECUTE);
                input.shrink(1);
            }
        }
    }

    private boolean canMachineRun() {
        return level != null
                && getEnergyStored() >= getEnergyPerOperation()
                && tank.getCapacity() - tank.getFluidAmount() >= 1000
                && redstoneMode.shouldRun(level.getBestNeighborSignal(worldPosition) > 0);
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return new int[]{0, 1};
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return index == 0 && InventoryUtils.isEmptyFluidContainer(itemStackIn);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index == 1;
    }

    @Override
    protected Component getDefaultName() {
        return TextUtils.translate("container", "pump");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new PumpContainer(id, player, this, this.fields);
    }

    @Override
    public void load(BlockState state, CompoundTag tags) {
        this.tank.readFromNBT(tags.getCompound("Tank"));
        super.load(state, tags);
    }

    @Override
    public CompoundTag save(CompoundTag tags) {
        tags.put("Tank", this.tank.writeToNBT(new CompoundTag()));
        return super.save(tags);
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (!this.remove && cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.orEmpty(cap, fluidCap.cast());
        }
        return super.getCapability(cap, side);
    }
}
