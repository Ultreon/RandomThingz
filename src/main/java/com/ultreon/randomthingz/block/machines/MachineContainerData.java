package com.ultreon.randomthingz.block.machines;

import net.minecraft.world.inventory.SimpleContainerData;

/**
 * Base container data for machines.
 *
 * @author Qboi123
 * @param <T> machine block entity.
 */
public abstract class MachineContainerData<T extends MachineBlockEntity<?>> extends SimpleContainerData {
    public static final int UPGRADE_SLOT_COUNT = 0;

    private final T blockEntity;

    /**
     * @param blockEntity block entity for container data transfer.
     * @param count the entry count.
     */
    public MachineContainerData(T blockEntity, int count) {
        super(count);
        this.blockEntity = blockEntity;
    }

    /**
     * Gets a value from index.<br>
     * For overrides: use an if statement, if the index is lower or equal than the max index used here then call the super method.
     *
     * @param index value index.
     * @return the value requested.
     */
    @Override
    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    public int get(int index) {
        return switch (index) {
            case 0 -> blockEntity.tier.getUpgradeSlots();
            default -> throw new UnsupportedOperationException("The index " + index + " doesn't map to any value.");
        };
    }

    /**
     * Sets a value based on the index.<br>
     * For overrides: use an if statement, if the index is lower or equal than the max index used here then call the super method.
     *
     * @param index value index.
     * @param value the value to set.
     */
    @Override
    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    public void set(int index, int value) {
        switch (index) {
            case 0 -> throw new IllegalStateException("Readonly entry: " + index);
        }
    }

    /**
     * Getter for block entity property.
     *
     * @return the block entity used for the container data.
     */
    public T getBlockEntity() {
        return blockEntity;
    }
}
