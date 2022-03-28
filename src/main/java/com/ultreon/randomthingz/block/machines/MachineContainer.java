package com.ultreon.randomthingz.block.machines;

import com.ultreon.modlib.block.entity.UmlItemStackHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;

public abstract class MachineContainer<T extends MachineBlockEntity<?>> extends BaseMachineContainer<T> {
    protected MachineContainer(MenuType<?> containerTypeIn, int id, Inventory inv, UmlItemStackHandler itemHandler, BlockPos pos, ContainerData fieldsIn) {
        super(containerTypeIn, id, inv, itemHandler, pos, fieldsIn);
    }

    public int getProgress() {
        return fields.get(6);
    }

    public int getProcessTime() {
        return fields.get(7);
    }
}
