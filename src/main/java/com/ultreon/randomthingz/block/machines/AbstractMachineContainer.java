package com.ultreon.randomthingz.block.machines;

import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;

public abstract class AbstractMachineContainer<T extends AbstractMachineTileEntity<?>> extends AbstractMachineBaseContainer<T> {
    protected AbstractMachineContainer(MenuType<?> containerTypeIn, int id, T tileEntityIn, ContainerData fieldsIn) {
        super(containerTypeIn, id, tileEntityIn, fieldsIn);
    }

    public int getProgress() {
        return fields.get(5);
    }

    public int getProcessTime() {
        return fields.get(6);
    }
}
