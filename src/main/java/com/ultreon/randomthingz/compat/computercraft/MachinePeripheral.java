package com.ultreon.randomthingz.compat.computercraft;

/*
import com.ultreon.forgemod.RandomThingz;
import com.ultreon.forgemod.blocks.machines.AbstractMachineBaseTileEntity;
import com.ultreon.modlib.api.RedstoneMode;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MachinePeripheral implements IPeripheral {
    private static final String TYPE = RandomThingz.rl("machine").toString();

    private final AbstractMachineBaseTileEntity machine;

    public MachinePeripheral(AbstractMachineBaseTileEntity machine) {
        this.machine = machine;
    }

    @NotNull
    @Override
    public String getType() {
        return TYPE;
    }

    @LuaFunction
    public final int getEnergy() {
        return machine.getEnergyStored();
    }

    @LuaFunction
    public final int getMaxEnergy() {
        return machine.getMaxEnergyStored();
    }

    @LuaFunction
    public final String getRedstoneMode() {
        return machine.getRedstoneMode().toString();
    }

    @LuaFunction
    public final void setRedstoneMode(String mode) throws LuaException {
        RedstoneMode redstoneMode = RedstoneMode.byName(mode);
        if (redstoneMode == null) {
            String validModes = Arrays.stream(RedstoneMode.values()).map(RedstoneMode::toString).collect(Collectors.joining(", "));
            throw new LuaException(String.format("Unknown redstone mode: %s (valid modes: %s)", mode, validModes));
        }
        machine.setRedstoneMode(redstoneMode);
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        if (other instanceof MachinePeripheral)
            return machine.equals(((MachinePeripheral) other).machine);
        return false;
    }
}
*/
