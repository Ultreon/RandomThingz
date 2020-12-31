package com.qsoftware.forgemod.objects.blocks.machines.quarry;

import com.qsoftware.forgemod.init.ModContainers;
import com.qsoftware.forgemod.objects.blocks.machines.AbstractMachineBaseContainer;
import com.qsoftware.silent.lib.util.InventoryUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("unused")
public class QuarryContainer extends AbstractMachineBaseContainer<QuarryTileEntity> {
    public QuarryContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new QuarryTileEntity(), new IntArray(QuarryTileEntity.FIELDS_COUNT));
    }

    public QuarryContainer(int id, PlayerInventory playerInventory, QuarryTileEntity tileEntity, IIntArray fields) {
        super(ModContainers.quarry, id, tileEntity, fields);

        InventoryUtils.createPlayerSlots(playerInventory, 8, 84).forEach(this::addSlot);

        this.addUpgradeSlots();
    }

    public int getCurrentX() {
        int upper = fields.get(7) & 0xFFFF;
        int lower = fields.get(6) & 0xFFFF;
        return (upper << 16) + lower;
    }

    public int getCurrentY() {
        int upper = fields.get(9) & 0xFFFF;
        int lower = fields.get(8) & 0xFFFF;
        return (upper << 16) + lower;
    }

    public int getCurrentZ() {
        int upper = fields.get(11) & 0xFFFF;
        int lower = fields.get(10) & 0xFFFF;
        return (upper << 16) + lower;
    }

    public int getBlocksRemaining() {
        int upper = fields.get(15) & 0xFFFF;
        int lower = fields.get(14) & 0xFFFF;
        return (upper << 16) + lower;
    }

    public int getTotalBlocks() {
        int upper = fields.get(17) & 0xFFFF;
        int lower = fields.get(16) & 0xFFFF;
        return (upper << 16) + lower;
    }

//    public boolean isInitialized() {
//        int value = fields.get(12) & 0xFFFF;
//        return value == 1;
//    }

//    public boolean isDone() {
//        int value = fields.get(13) & 0xFFFF;
//        return value == 1;
//    }

    public QuarryTileEntity.Status getStatus() {
        int upper = fields.get(21) & 0xFFFF;
        int lower = fields.get(20) & 0xFFFF;
        int i = (upper << 16) + lower;
        return QuarryTileEntity.Status.values()[i];
    }

//    public boolean isIllegalPosition() {
//        int value = fields.get(18) & 0xFFFF;
//        return value == 1;
//    }

    public BlockPos getCurrentPos() {
        return new BlockPos(getCurrentX(), getCurrentY(), getCurrentZ());
    }
}
