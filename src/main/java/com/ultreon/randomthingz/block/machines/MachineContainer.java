package com.ultreon.randomthingz.block.machines;

import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public abstract class MachineContainer<T extends MachineBlockEntity<?>> extends BaseMachineBaseContainer<T> {
    protected MachineContainer(MenuType<?> containerTypeIn, int id, @Nullable T tileEntityIn, ContainerData fieldsIn) {
        super(containerTypeIn, id, tileEntityIn, fieldsIn);
    }

    public int getProgress() {
        return fields.get(6);
    }

    public int getProcessTime() {
        return fields.get(7);
    }
}
