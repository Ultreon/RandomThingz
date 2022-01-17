package com.ultreon.randomthingz.block.machines.generator.diesel;

import com.ultreon.modlib.api.FluidContainer;
import com.ultreon.randomthingz.block.machines.generator.AbstractFluidGeneratorContainer;
import com.ultreon.randomthingz.block.machines.generator.FluidFuelGeneratorBlockEntity;
import com.ultreon.randomthingz.init.ModMachineContainers;
import com.ultreon.randomthingz.util.InventoryUtils;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class DieselGeneratorContainer extends AbstractFluidGeneratorContainer<DieselGeneratorBlockEntity> {
    public DieselGeneratorContainer(int id, Inventory player) {
        this(id, player, new DieselGeneratorBlockEntity(), new SimpleContainerData(FluidFuelGeneratorBlockEntity.FIELDS_COUNT));
    }

    public DieselGeneratorContainer(int id, Inventory player, DieselGeneratorBlockEntity tileEntity, ContainerData fields) {
        super(ModMachineContainers.dieselGenerator, id, tileEntity, fields);

        this.addSlot(new Slot(this.tileEntity, 0, 80, 16));
        this.addSlot(new Slot(this.tileEntity, 1, 80, 59));

        com.ultreon.modlib.embedded.silentlib.util.InventoryUtils.createPlayerSlots(player, 8, 84).forEach(this::addSlot);

        this.addUpgradeSlots();
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }

    @Override
    protected boolean isFuel(ItemStack stack) {
        return InventoryUtils.isFilledFluidContainer(stack)
                && FluidContainer.getBucketOrContainerFluid(stack).getFluid().is(DieselGeneratorBlockEntity.FUEL_TAG);
    }
}
