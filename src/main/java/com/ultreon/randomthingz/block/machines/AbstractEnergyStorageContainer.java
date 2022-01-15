package com.ultreon.randomthingz.block.machines;

import com.qsoftware.modlib.silentutils.MathUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;

public class AbstractEnergyStorageContainer<T extends AbstractEnergyInventoryTileEntity> extends AbstractContainerMenu {
    protected final T tileEntity;
    protected final ContainerData fields;

    protected AbstractEnergyStorageContainer(MenuType<?> type, int id, T tileEntityIn, ContainerData fieldsIn) {
        super(type, id);
        this.tileEntity = tileEntityIn;
        this.fields = fieldsIn;

        addDataSlots(this.fields);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        // TODO
        return true;
    }

    public T getTileEntity() {
        return tileEntity;
    }

    public ContainerData getFields() {
        return fields;
    }

    public int getEnergyStored() {
        int upper = fields.get(1) & 0xFFFF;
        int lower = fields.get(0) & 0xFFFF;
        return (upper << 16) + lower;
    }

    public int getMaxEnergyStored() {
        int upper = fields.get(3) & 0xFFFF;
        int lower = fields.get(2) & 0xFFFF;
        return (upper << 16) + lower;
    }

    public int getEnergyBarHeight() {
        int max = getMaxEnergyStored();
        int energyClamped = MathUtils.clamp(getEnergyStored(), 0, max);
        return max > 0 ? 50 * energyClamped / max : 0;
    }
}
