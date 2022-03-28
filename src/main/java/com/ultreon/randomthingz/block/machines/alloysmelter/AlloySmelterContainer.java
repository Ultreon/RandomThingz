package com.ultreon.randomthingz.block.machines.alloysmelter;

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

public class AlloySmelterContainer extends MachineContainer<AlloySmelterBlockEntity> {
    private final MachineTier tier;

    public AlloySmelterContainer(int id, Inventory inv, MachineTier tier) {
        this(id, inv, tier, new UmlItemStackHandler(AlloySmelterBlockEntity.INPUT_SLOT_COUNT + 1 + 2), BlockPos.ZERO, new SimpleContainerData(MachineBlockEntity.FIELDS_COUNT));

        System.out.println("inv.player.level.isClientSide = " + inv.player.level.isClientSide);
    }

    protected AlloySmelterContainer(int id, Inventory inv, MachineTier tier, UmlItemStackHandler itemHandler, BlockPos pos, ContainerData fields) {
        super(MachineType.ALLOY_SMELTER.getContainerType(tier), id, inv, itemHandler, pos, fields);

        this.tier = tier;

        for (int i = 0; i < AlloySmelterBlockEntity.INPUT_SLOT_COUNT; ++i) {
            this.addSlot(new SlotItemHandler(itemHandler, i, 17 + 18 * i, 35));
        }
        this.addSlot(new OutputSlot(itemHandler, AlloySmelterBlockEntity.INPUT_SLOT_COUNT, 126, 35));

        InventoryUtils.createPlayerSlots(inv, 8, 84).forEach(this::addSlot);
        this.addUpgradeSlots();
    }

    @Override
    public int getUpgradeSlots() {
        return 2;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack output = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            output = stack.copy();

            final int size = this.stackHandler.getSlots();
            final int invEnd = size + 27;
            final int hotbarEnd = invEnd + 9;

            if (index == size - 1) {
                if (!this.moveItemStackTo(stack, size, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(stack, output);
            } else if (index >= size) {
                if (this.isAlloySmeltingIngredient(stack)) {
                    if (!this.moveItemStackTo(stack, 0, size - 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < invEnd) {
                    if (!this.moveItemStackTo(stack, invEnd, hotbarEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < hotbarEnd && !this.moveItemStackTo(stack, size, invEnd, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, size, hotbarEnd, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == output.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return output;
    }

    private boolean isAlloySmeltingIngredient(ItemStack stack) {
        // TODO
        return true;
    }

    @Override
    public MachineTier getTier() {
        return tier;
    }
}
