package com.ultreon.randomthingz.block.machines;

import com.ultreon.modlib.api.RedstoneMode;
import com.ultreon.modlib.silentutils.EnumUtils;
import com.ultreon.randomthingz.inventory.slot.MachineUpgradeSlot;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public class BaseMachineBaseContainer<T extends MachineBaseBlockEntity> extends BaseEnergyStorageContainer<T> {
    protected BaseMachineBaseContainer(MenuType<?> type, int id, @Nullable T tileEntityIn, ContainerData fieldsIn) {
        super(type, id, tileEntityIn, fieldsIn);
    }

    protected final void addUpgradeSlots() {
        if (this.tileEntity != null) {
            int upgradeSlots = this.tileEntity.tier.getUpgradeSlots();
            int inventorySize = this.tileEntity.getContainerSize();
            for (int i = 0; i < upgradeSlots; ++i) {
                int index = inventorySize - upgradeSlots + i;
                int xPosition = 6 + 18 * i;
                this.addSlot(new MachineUpgradeSlot(this.tileEntity, index, xPosition, -13));
            }
        }
    }

    public RedstoneMode getRedstoneMode() {
        return EnumUtils.byOrdinal(fields.get(4), RedstoneMode.IGNORED);
    }

    public void setRedstoneMode(RedstoneMode mode) {
        fields.set(4, mode.ordinal());
    }

    public int getUpgradeSlots() {
        return fields.get(5);
    }
}
