package com.ultreon.randomthingz.block.machines;

import com.ultreon.modlib.silentutils.MathUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public class BaseEnergyStorageContainer<T extends BaseEnergyInventoryBlockEntity> extends AbstractContainerMenu {
    @Nullable
    protected final T tileEntity = null;
    protected final ContainerData fields;

    protected BaseEnergyStorageContainer(MenuType<?> type, int id, ContainerData data) {
        super(type, id);
        this.fields = data;

        addDataSlots(data);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        // TODO
        return true;
    }

    @Nullable
    public T getTileEntity() {
        return null;
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
