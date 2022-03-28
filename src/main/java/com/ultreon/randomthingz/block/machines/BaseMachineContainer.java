package com.ultreon.randomthingz.block.machines;

import com.ultreon.modlib.api.RedstoneMode;
import com.ultreon.modlib.block.entity.UmlItemStackHandler;
import com.ultreon.modlib.silentutils.EnumUtils;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.inventory.slot.MachineUpgradeSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class BaseMachineContainer<T extends MachineBaseBlockEntity> extends BaseEnergyStorageContainer<T> {
    protected final UmlItemStackHandler stackHandler;
    protected final ContainerLevelAccess containerAccess;

    protected BaseMachineContainer(MenuType<?> type, int id, Inventory inv, UmlItemStackHandler stackHandler, BlockPos pos, ContainerData data) {
        super(type, id, data);
        int slots = stackHandler.getSlots();
        System.out.println("[000001] slots[0] = " + slots);
        System.out.println("[000001] slots[1] = " + inv.getContainerSize());
        System.out.println("[000001] slots[2] = " + (slots + inv.getContainerSize()));
        System.out.println("[000001] slots[3] = " );
        System.out.println("[000002] isClient = " + inv.player.level.isClientSide);
//        stackHandler.setSize(stackHandler.getSlots() + getUpgradeSlots());
        this.stackHandler = stackHandler;
        this.containerAccess = ContainerLevelAccess.create(inv.player.level, pos);
    }

    @Override
    protected Slot addSlot(Slot p_38898_) {
        Slot slot = super.addSlot(p_38898_);
        System.out.println("slots.size() = " + slots.size());
        return slot;
    }

    protected final void addUpgradeSlots() {
        if (this.stackHandler != null) {
            int upgradeSlots = getUpgradeSlots();
            int inventorySize = stackHandler.getSlots();
            for (int i = 0; i < upgradeSlots; ++i) {
                int index = inventorySize - upgradeSlots + i;
                int xPosition = 6 + 18 * i;
                this.addSlot(new MachineUpgradeSlot(this.stackHandler, index, xPosition, -13));
            }
        }
    }

    public RedstoneMode getRedstoneMode() {
        return EnumUtils.byOrdinal(fields.get(4), RedstoneMode.IGNORED);
    }

    public void setRedstoneMode(RedstoneMode mode) {
        fields.set(4, mode.ordinal());
    }

    public MachineTier getTier() {
        return MachineTier.values()[fields.get(5)];
    }

    public int getUpgradeSlots() {
        return getTier().getUpgradeSlots();
    }
}
