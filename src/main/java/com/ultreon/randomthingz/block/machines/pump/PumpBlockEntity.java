package com.ultreon.randomthingz.block.machines.pump;

import com.ultreon.modlib.api.FluidContainer;
import com.ultreon.modlib.api.RedstoneMode;
import com.ultreon.modlib.silentlib.util.TimeUtils;
import com.ultreon.modlib.silentutils.EnumUtils;
import com.ultreon.randomthingz.block.entity.ModMachines;
import com.ultreon.randomthingz.block.machines.MachineBaseBlockEntity;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.init.ModMachineUpgrades;
import com.ultreon.randomthingz.item.upgrade.MachineUpgrade;
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
import net.minecraft.world.item.BucketItem;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PumpBlockEntity extends MachineBaseBlockEntity {
    public static final int ENERGY_PER_BUCKET = 500;
    public static final int PUMP_DELAY = TimeUtils.ticksFromSeconds(5);
    public static final int FIELDS_COUNT = 9;
    private final FluidTank tank = new FluidTank(4000);
    protected final ContainerData fields = new ContainerData() {
        @SuppressWarnings("deprecation") // Use of Registry
        @Override
        public int get(int index) {
            return switch (index) {
                //Minecraft actually sends fields as shorts, so we need to split energy into 2 fields
                case 0 ->
                        // Energy lower bytes
                        getEnergyStored() & 0xFFFF;
                case 1 ->
                        // Energy upper bytes
                        (getEnergyStored() >> 16) & 0xFFFF;
                case 2 -> getMaxEnergyStored() & 0xFFFF;
                case 3 -> (getMaxEnergyStored() >> 16) & 0xFFFF;
                case 4 -> redstoneMode.ordinal();
                case 5 -> tier.ordinal();
                case 7 -> Registry.FLUID.getId(tank.getFluid().getFluid());
                case 8 -> tank.getFluid().getAmount();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            if (index == 4) {
                redstoneMode = EnumUtils.byOrdinal(value, RedstoneMode.IGNORED);
            }
        }

        @Override
        public int getCount() {
            return FIELDS_COUNT;
        }
    };
    private final LazyOptional<IFluidHandler> fluidCap = LazyOptional.of(() -> tank);

    public PumpBlockEntity(BlockPos pos, BlockState state) {
        super(ModMachines.PUMP.get(), pos, state, 2, 10_000, 100, 0, MachineTier.STANDARD);
    }

    private int getHorizontalRange() {
        return 3 + getUpgradeCount(ModMachineUpgrades.RANGE) * Constants.UPGRADE_RANGE_AMOUNT;
    }

    private int getVerticalRange() {
        return 64;
    }

    private int getEnergyPerOperation() {
        return (int) (ENERGY_PER_BUCKET * getUpgradesEnergyMultiplier());
    }

    private int getPumpDelay() {
        int upgrades = getUpgradeCount(ModMachineUpgrades.PROCESSING_SPEED);
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
            Fluid fluid = ((BucketItem) ((BucketPickup) state.getBlock()).pickupBlock(level, blockPos, state).getItem()).getFluid();
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

        FluidStack fluidInInput = FluidContainer.getBucketOrContainerFluid(input);
        if (!fluidInInput.isEmpty()) return;

        FluidStack fluidInTank = tank.getFluidInTank(0);
        if (fluidInTank.getAmount() >= 1000) {
            ItemStack filled = FluidContainer.fillBucketOrFluidContainer(input, fluidInTank);
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
        return new PumpContainer(id, player, inventory, worldPosition, fields);
    }

    @Override
    public void load(CompoundTag tags) {
        this.tank.readFromNBT(tags.getCompound("Tank"));
        super.load(tags);
    }

    @Override
    public void saveAdditional(CompoundTag tags) {
        tags.put("Tank", this.tank.writeToNBT(new CompoundTag()));
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!this.remove && cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.orEmpty(cap, fluidCap.cast());
        }
        return super.getCapability(cap, side);
    }
}
