package com.ultreon.randomthingz.block.machines.generator.diesel;

import com.qsoftware.modlib.api.IFluidContainer;
import com.ultreon.randomthingz.block.machines.generator.AbstractFluidFuelGeneratorTileEntity;
import com.ultreon.randomthingz.block.machines.generator.AbstractFluidGeneratorContainer;
import com.ultreon.randomthingz.init.ModMachineContainers;
import com.ultreon.randomthingz.util.InventoryUtils;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class DieselGeneratorContainer extends AbstractFluidGeneratorContainer<DieselGeneratorTileEntity> {
    public DieselGeneratorContainer(int id, Inventory player) {
        this(id, player, new DieselGeneratorTileEntity(), new SimpleContainerData(AbstractFluidFuelGeneratorTileEntity.FIELDS_COUNT));
    }

    public DieselGeneratorContainer(int id, Inventory player, DieselGeneratorTileEntity tileEntity, ContainerData fields) {
        super(ModMachineContainers.dieselGenerator, id, tileEntity, fields);

        this.addSlot(new Slot(this.tileEntity, 0, 80, 16));
        this.addSlot(new Slot(this.tileEntity, 1, 80, 59));

        com.qsoftware.modlib.silentlib.util.InventoryUtils.createPlayerSlots(player, 8, 84).forEach(this::addSlot);

        this.addUpgradeSlots();
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }

    @Override
    protected boolean isFuel(ItemStack stack) {
        return InventoryUtils.isFilledFluidContainer(stack)
                && IFluidContainer.getBucketOrContainerFluid(stack).getFluid().is(DieselGeneratorTileEntity.FUEL_TAG);
    }
}
