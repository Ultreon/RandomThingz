package com.ultreon.randomthingz.block.machines;

import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractMachineContainer<T extends AbstractMachineBlockEntity<?>> extends BaseMachineBaseContainer<T> {
    protected AbstractMachineContainer(MenuType<?> containerTypeIn, int id, @Nullable T tileEntityIn, ContainerData fieldsIn) {
        super(containerTypeIn, id, tileEntityIn, fieldsIn);
    }

    public int getProgress() {
        return fields.get(5);
    }

    public int getProcessTime() {
        return fields.get(6);
    }
}
