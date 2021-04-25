package com.qtech.forgemod.compat.computercraft;
/*

import com.qtech.forgemod.modules.tiles.blocks.machines.AbstractMachineBaseTileEntity;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public final class SMechComputerCraftCompat {
    private SMechComputerCraftCompat() {
    }

    public static void initialize() {
        ComputerCraftAPI.registerPeripheralProvider(SMechComputerCraftCompat::getPeripheral);
    }

    @SuppressWarnings("TypeMayBeWeakened")
    private static LazyOptional<IPeripheral> getPeripheral(World dimension, BlockPos pos, Direction side) {
        TileEntity tileEntity = dimension.getTileEntity(pos);
        if (tileEntity instanceof AbstractMachineBaseTileEntity) {
            return LazyOptional.of(() -> new MachinePeripheral((AbstractMachineBaseTileEntity) tileEntity));
        }
        return LazyOptional.empty();
    }
}
*/
