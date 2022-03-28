package com.ultreon.randomthingz.block.machines.arcaneescalator;

import com.ultreon.modlib.block.entity.UmlItemStackHandler;
import com.ultreon.modlib.inventory.OutputSlot;
import com.ultreon.modlib.silentlib.util.InventoryUtils;
import com.ultreon.randomthingz.block.machines.MachineType;
import com.ultreon.randomthingz.block.machines.MachineBlockEntity;
import com.ultreon.randomthingz.block.machines.MachineContainer;
import com.ultreon.randomthingz.common.enums.MachineTier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

@SuppressWarnings("ConstantConditions")
public class ArcaneEscalatorContainer extends MachineContainer<ArcaneEscalatorBlockEntity> {
    private final MachineTier tier;

    public ArcaneEscalatorContainer(int id, Inventory inv, MachineTier tier) {
        this(id, inv, tier, new UmlItemStackHandler(ArcaneEscalatorBlockEntity.INPUT_SLOT_COUNT + 1), BlockPos.ZERO, new SimpleContainerData(MachineBlockEntity.FIELDS_COUNT));
    }

    protected ArcaneEscalatorContainer(int id, Inventory inv, MachineTier tier, UmlItemStackHandler itemHandler, BlockPos pos, ContainerData fieldsIn) {
        super(MachineType.ARCANE_ESCALATOR.getContainerType(tier), id, inv, itemHandler, pos, fieldsIn);

        this.tier = tier;

        for (int i = 0; i < ArcaneEscalatorBlockEntity.INPUT_SLOT_COUNT; ++i) {
            this.addSlot(new SlotItemHandler(itemHandler, i, 17 + 18 * i, 35));
        }
        this.addSlot(new OutputSlot(itemHandler, ArcaneEscalatorBlockEntity.INPUT_SLOT_COUNT, 126, 35));

        InventoryUtils.createPlayerSlots(inv, 8, 84).forEach(this::addSlot);
        this.addUpgradeSlots();
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack output = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemInSlot = slot.getItem();
            output = itemInSlot.copy();

            final int inventorySize = this.tileEntity.getContainerSize();
            final int playerInventoryEnd = inventorySize + 27;
            final int playerHotbarEnd = playerInventoryEnd + 9;

            if (index == inventorySize - 1) {
                if (!this.moveItemStackTo(itemInSlot, inventorySize, playerHotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemInSlot, output);
            } else if (index >= inventorySize) {
                if (this.isArcaneEscalatingIngredient(itemInSlot)) {
                    if (!this.moveItemStackTo(itemInSlot, 0, inventorySize - 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < playerInventoryEnd) {
                    if (!this.moveItemStackTo(itemInSlot, playerInventoryEnd, playerHotbarEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < playerHotbarEnd && !this.moveItemStackTo(itemInSlot, inventorySize, playerInventoryEnd, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemInSlot, inventorySize, playerHotbarEnd, false)) {
                return ItemStack.EMPTY;
            }

            if (itemInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemInSlot.getCount() == output.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemInSlot);
        }

        return output;
    }

    private boolean isArcaneEscalatingIngredient(ItemStack stack) {
        // TODO
        return true;
    }

    @Override
    public MachineTier getTier() {
        return tier;
    }
}
