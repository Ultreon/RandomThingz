package com.ultreon.randomthingz.block.machines.generator.diesel;

import com.ultreon.modlib.api.FluidContainer;
import com.ultreon.modlib.block.entity.UmlItemStackHandler;
import com.ultreon.randomthingz.block.machines.generator.AbstractFluidGeneratorContainer;
import com.ultreon.randomthingz.block.machines.generator.FluidFuelGeneratorBlockEntity;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.init.ModMachineContainers;
import com.ultreon.randomthingz.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class DieselGeneratorContainer extends AbstractFluidGeneratorContainer<DieselGeneratorBlockEntity> {
    public DieselGeneratorContainer(int id, Inventory inv) {
        this(id, inv, new UmlItemStackHandler(2 + MachineTier.STANDARD.getUpgradeSlots()), BlockPos.ZERO, new SimpleContainerData(FluidFuelGeneratorBlockEntity.FIELDS_COUNT));
    }

    public DieselGeneratorContainer(int id, Inventory inv, UmlItemStackHandler itemHandler, BlockPos pos, ContainerData fields) {
        super(ModMachineContainers.dieselGenerator, id, inv, itemHandler, pos, fields);

        this.addSlot(new SlotItemHandler(this.stackHandler, 0, 80, 16));
        this.addSlot(new SlotItemHandler(this.stackHandler, 1, 80, 59));

        com.ultreon.modlib.silentlib.util.InventoryUtils.createPlayerSlots(inv, 8, 84).forEach(this::addSlot);

        this.addUpgradeSlots();
    }

    @Override
    public MachineTier getTier() {
        return MachineTier.STANDARD;
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
