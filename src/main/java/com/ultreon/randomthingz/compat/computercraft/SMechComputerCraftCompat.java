package com.ultreon.randomthingz.compat.computercraft;
/*

import com.ultreon.forgemod.blocks.machines.AbstractMachineBaseTileEntity;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;

public final class SMechComputerCraftCompat {
    private SMechComputerCraftCompat() {
    }

    public static void initialize() {
        ComputerCraftAPI.registerPeripheralProvider(SMechComputerCraftCompat::getPeripheral);
    }

    @SuppressWarnings("TypeMayBeWeakened")
    private static LazyOptional<IPeripheral> getPeripheral(Level dimension, BlockPos pos, Direction side) {
        BlockEntity tileEntity = dimension.getTileEntity(pos);
        if (tileEntity instanceof AbstractMachineBaseTileEntity) {
            return LazyOptional.of(() -> new MachinePeripheral((AbstractMachineBaseTileEntity) tileEntity));
        }
        return LazyOptional.empty();
    }
}
*/
