package com.qsoftware.forgemod.objects.block.generator.diesel;

import com.qsoftware.forgemod.api.IFluidContainer;
import com.qsoftware.forgemod.init.ModContainers;
import com.qsoftware.forgemod.objects.block.generator.AbstractFluidFuelGeneratorTileEntity;
import com.qsoftware.forgemod.objects.block.generator.AbstractFluidGeneratorContainer;
import com.qsoftware.forgemod.util.InventoryUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class DieselGeneratorContainer extends AbstractFluidGeneratorContainer<DieselGeneratorTileEntity> {
    public DieselGeneratorContainer(int id, PlayerInventory player) {
        this(id, player, new DieselGeneratorTileEntity(), new IntArray(AbstractFluidFuelGeneratorTileEntity.FIELDS_COUNT));
    }

    public DieselGeneratorContainer(int id, PlayerInventory player, DieselGeneratorTileEntity tileEntity, IIntArray fields) {
        super(ModContainers.dieselGenerator, id, tileEntity, fields);

        this.addSlot(new Slot(this.tileEntity, 0, 80, 16));
        this.addSlot(new Slot(this.tileEntity, 1, 80, 59));

        com.qsoftware.silent.lib.util.InventoryUtils.createPlayerSlots(player, 8, 84).forEach(this::addSlot);

        this.addUpgradeSlots();
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    protected boolean isFuel(ItemStack stack) {
        return InventoryUtils.isFilledFluidContainer(stack)
                && IFluidContainer.getBucketOrContainerFluid(stack).getFluid().isIn(DieselGeneratorTileEntity.FUEL_TAG);
    }
}
