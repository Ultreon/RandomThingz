package com.ultreon.randomthingz.block.machines;

import com.ultreon.modlib.api.RedstoneMode;
import com.ultreon.modlib.embedded.silentutils.EnumUtils;
import com.ultreon.randomthingz.capability.EnergyStorageImpl;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.item.upgrade.MachineUpgrades;
import com.ultreon.randomthingz.util.Constants;
import com.ultreon.randomthingz.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public abstract class AbstractMachineTileEntity<R extends Recipe<?>> extends AbstractMachineBaseTileEntity implements IMachineInventory {
    public static final int FIELDS_COUNT = 7;

    protected float progress;
    protected int processTime;

    protected final ContainerData fields = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                //Minecraft actually sends fields as shorts, so we need to split energy into 2 fields
                case 0 ->
                        // Energy lower bytes
                        AbstractMachineTileEntity.this.getEnergyStored() & 0xFFFF;
                case 1 ->
                        // Energy upper bytes
                        (AbstractMachineTileEntity.this.getEnergyStored() >> 16) & 0xFFFF;
                case 2 ->
                        // Max energy lower bytes
                        AbstractMachineTileEntity.this.getMaxEnergyStored() & 0xFFFF;
                case 3 ->
                        // Max energy upper bytes
                        (AbstractMachineTileEntity.this.getMaxEnergyStored() >> 16) & 0xFFFF;
                case 4 -> AbstractMachineTileEntity.this.redstoneMode.ordinal();
                case 5 -> (int) AbstractMachineTileEntity.this.progress;
                case 6 -> AbstractMachineTileEntity.this.processTime;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 4 -> AbstractMachineTileEntity.this.redstoneMode = EnumUtils.byOrdinal(value, RedstoneMode.IGNORED);
                case 5 -> AbstractMachineTileEntity.this.progress = value;
                case 6 -> AbstractMachineTileEntity.this.processTime = value;
            }
        }

        @Override
        public int getCount() {
            return FIELDS_COUNT;
        }
    };

    protected AbstractMachineTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state, int inventorySize, MachineTier tier) {
        super(typeIn, pos, state, inventorySize, tier.getEnergyCapacity(), 500, 0, tier);
    }

    @Override
    public EnergyStorageImpl getEnergyImpl() {
        return super.getEnergyImpl();
    }

    protected abstract int getEnergyUsedPerTick();

    protected BlockState getActiveState(BlockState currentState) {
        return currentState.setValue(AbstractFurnaceBlock.LIT, true);
    }

    protected BlockState getInactiveState(BlockState currentState) {
        return currentState.setValue(AbstractFurnaceBlock.LIT, false);
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
        if (level == null) return;
        BlockState oldState = level.getBlockState(worldPosition);
        if (oldState != newState) {
            level.setBlock(worldPosition, newState, 3);
            level.sendBlockUpdated(worldPosition, oldState, newState, 3);
        }
    }

    protected void setInactiveState() {
        if (level == null) return;
        sendUpdate(getInactiveState(level.getBlockState(worldPosition)));
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
                getProcessResults(recipe).forEach(this::storeResultItem);
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
                && hasRoomInOutput(getPossibleProcessResult(recipe))
                && redstoneMode.shouldRun(level.getBestNeighborSignal(worldPosition) > 0);
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
            ItemStack output = getItem(i);
            if (InventoryUtils.canItemsStack(stack, output)) {
                return true;
            }
        }
        return false;
    }

    protected void storeResultItem(ItemStack stack) {
        // Merge the item into any output slot it can fit in
        for (int i : getOutputSlots()) {
            ItemStack output = getItem(i);
            if (InventoryUtils.canItemsStack(stack, output)) {
                if (output.isEmpty()) {
                    setItem(i, stack);
                } else {
                    output.setCount(output.getCount() + stack.getCount());
                }
                return;
            }
        }
    }

    protected void consumeIngredients(R recipe) {
        removeItem(0, 1);
    }

    @Override
    public ContainerData getFields() {
        return fields;
    }

    @Override
    public void load(CompoundTag tags) {
        super.load(tags);
        this.progress = tags.getInt("Progress");
        this.processTime = tags.getInt("ProcessTime");
    }

    @Override
    public CompoundTag save(CompoundTag tags) {
        super.save(tags);
        tags.putInt("Progress", (int) this.progress);
        tags.putInt("ProcessTime", this.processTime);
        return tags;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        super.onDataPacket(net, packet);
        CompoundTag tags = packet.getTag();
        this.progress = tags.getInt("Progress");
        this.processTime = tags.getInt("ProcessTime");
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tags = super.getUpdateTag();
        tags.putInt("Progress", (int) this.progress);
        tags.putInt("ProcessTime", this.processTime);
        return tags;
    }
}
