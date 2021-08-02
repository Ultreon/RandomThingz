package com.ultreon.randomthingz.block.machines;

import com.qsoftware.modlib.api.RedstoneMode;
import com.qsoftware.modlib.silentutils.EnumUtils;
import com.ultreon.randomthingz.capability.EnergyStorageImpl;
import com.ultreon.randomthingz.commons.enums.MachineTier;
import com.ultreon.randomthingz.item.upgrades.MachineUpgrades;
import com.ultreon.randomthingz.util.Constants;
import com.ultreon.randomthingz.util.InventoryUtils;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;

import javax.annotation.Nullable;
import java.util.Collection;

public abstract class AbstractMachineTileEntity<R extends IRecipe<?>> extends AbstractMachineBaseTileEntity implements IMachineInventory {
    public static final int FIELDS_COUNT = 7;

    protected float progress;
    protected int processTime;

    protected final IIntArray fields = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index) {
                //Minecraft actually sends fields as shorts, so we need to split energy into 2 fields
                case 0:
                    // Energy lower bytes
                    return AbstractMachineTileEntity.this.getEnergyStored() & 0xFFFF;
                case 1:
                    // Energy upper bytes
                    return (AbstractMachineTileEntity.this.getEnergyStored() >> 16) & 0xFFFF;
                case 2:
                    // Max energy lower bytes
                    return AbstractMachineTileEntity.this.getMaxEnergyStored() & 0xFFFF;
                case 3:
                    // Max energy upper bytes
                    return (AbstractMachineTileEntity.this.getMaxEnergyStored() >> 16) & 0xFFFF;
                case 4:
                    return AbstractMachineTileEntity.this.redstoneMode.ordinal();
                case 5:
                    return (int) AbstractMachineTileEntity.this.progress;
                case 6:
                    return AbstractMachineTileEntity.this.processTime;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 4:
                    AbstractMachineTileEntity.this.redstoneMode = EnumUtils.byOrdinal(value, RedstoneMode.IGNORED);
                    break;
                case 5:
                    AbstractMachineTileEntity.this.progress = value;
                    break;
                case 6:
                    AbstractMachineTileEntity.this.processTime = value;
                    break;
            }
        }

        @Override
        public int size() {
            return FIELDS_COUNT;
        }
    };

    protected AbstractMachineTileEntity(TileEntityType<?> typeIn, int inventorySize, MachineTier tier) {
        super(typeIn, inventorySize, tier.getEnergyCapacity(), 500, 0, tier);
    }

    @Override
    public EnergyStorageImpl getEnergyImpl() {
        return super.getEnergyImpl();
    }

    protected abstract int getEnergyUsedPerTick();

    protected BlockState getActiveState(BlockState currentState) {
        return currentState.with(AbstractFurnaceBlock.LIT, true);
    }

    protected BlockState getInactiveState(BlockState currentState) {
        return currentState.with(AbstractFurnaceBlock.LIT, false);
    }

    /**
     * Indexes of output slots. Recipe outputs will be merged into these slots.
     *
     * @return The output slots
     */
    protected abstract int[] getOutputSlots();

    /**
     * Get the recipe that matches the current inventory.
     *
     * @return The recipe to process, or null if there is no matching recipe
     */
    @Nullable
    protected abstract R getRecipe();

    /**
     * Get the base time (in ticks) to process the given recipe.
     *
     * @param recipe The recipe
     * @return Process time in ticks
     */
    protected abstract int getProcessTime(R recipe);

    /**
     * Get the processing speed. This is added to processing progress every tick. A speed of 1 would
     * process a 200 tick recipe in 200 ticks, speed 2 would be 100 ticks. Should account for speed
     * upgrades.
     *
     * @return The processing speed
     */
    protected float getProcessSpeed() {
        int speedUpgrades = getUpgradeCount(MachineUpgrades.PROCESSING_SPEED);
        return tier.getProcessingSpeed() * (1f + speedUpgrades * Constants.UPGRADE_PROCESSING_SPEED_AMOUNT);
    }

    /**
     * Get the results of the recipe.
     *
     * @param recipe The recipe
     * @return The results of the processing operation
     */
    protected abstract Collection<ItemStack> getProcessResults(R recipe);

    /**
     * Get all possible results of processing this recipe. Override if recipes can contain a
     * variable number of outputs.
     *
     * @param recipe The recipe
     * @return All possible results of the processing operation
     */
    protected Collection<ItemStack> getPossibleProcessResult(R recipe) {
        return getProcessResults(recipe);
    }

    @Override
    public int getInputSlotCount() {
        return 1;
    }

    protected void sendUpdate(BlockState newState) {
        if (dimension == null) return;
        BlockState oldState = dimension.getBlockState(pos);
        if (oldState != newState) {
            dimension.setBlockState(pos, newState, 3);
            dimension.notifyBlockUpdate(pos, oldState, newState, 3);
        }
    }

    protected void setInactiveState() {
        if (dimension == null) return;
        sendUpdate(getInactiveState(dimension.getBlockState(pos)));
    }

    @Override
    public void tick() {
        if (dimension == null || dimension.isClientSided) return;

        R recipe = getRecipe();
        if (recipe != null && canMachineRun(recipe)) {
            // Process
            processTime = getProcessTime(recipe);
            progress += getProcessSpeed();
            energy.consumeEnergy((int) (getEnergyUsedPerTick() * getUpgradesEnergyMultiplier()));

            if (progress >= processTime) {
                // Create result
                getProcessResults(recipe).forEach(this::storeResultItem);
                consumeIngredients(recipe);
                progress = 0;

                if (getRecipe() == null) {
                    setInactiveState();
                }
            } else {
                sendUpdate(getActiveState(dimension.getBlockState(pos)));
            }
        } else {
            if (recipe == null) {
                progress = 0;
            }
            setInactiveState();
        }
    }

    private boolean canMachineRun(R recipe) {
        return dimension != null
                && getEnergyStored() >= getEnergyUsedPerTick()
                && hasRoomInOutput(getPossibleProcessResult(recipe))
                && redstoneMode.shouldRun(dimension.getRedstonePowerFromNeighbors(pos) > 0);
    }

    protected boolean hasRoomInOutput(Iterable<ItemStack> results) {
        for (ItemStack stack : results) {
            if (!hasRoomForOutputItem(stack)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasRoomForOutputItem(ItemStack stack) {
        for (int i : getOutputSlots()) {
            ItemStack output = getStackInSlot(i);
            if (InventoryUtils.canItemsStack(stack, output)) {
                return true;
            }
        }
        return false;
    }

    protected void storeResultItem(ItemStack stack) {
        // Merge the item into any output slot it can fit in
        for (int i : getOutputSlots()) {
            ItemStack output = getStackInSlot(i);
            if (InventoryUtils.canItemsStack(stack, output)) {
                if (output.isEmpty()) {
                    setInventorySlotContents(i, stack);
                } else {
                    output.setCount(output.getCount() + stack.getCount());
                }
                return;
            }
        }
    }

    protected void consumeIngredients(R recipe) {
        decrStackSize(0, 1);
    }

    @Override
    public IIntArray getFields() {
        return fields;
    }

    @Override
    public void read(BlockState state, CompoundNBT tags) {
        super.read(state, tags);
        this.progress = tags.getInt("Progress");
        this.processTime = tags.getInt("ProcessTime");
    }

    @Override
    public CompoundNBT write(CompoundNBT tags) {
        super.write(tags);
        tags.putInt("Progress", (int) this.progress);
        tags.putInt("ProcessTime", this.processTime);
        return tags;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
        super.onDataPacket(net, packet);
        CompoundNBT tags = packet.getNbt();
        this.progress = tags.getInt("Progress");
        this.processTime = tags.getInt("ProcessTime");
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT tags = super.getUpdateTag();
        tags.putInt("Progress", (int) this.progress);
        tags.putInt("ProcessTime", this.processTime);
        return tags;
    }
}
