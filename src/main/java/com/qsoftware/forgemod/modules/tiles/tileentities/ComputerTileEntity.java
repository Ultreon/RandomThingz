package com.qsoftware.forgemod.modules.tiles.tileentities;

import com.qsoftware.forgemod.client.gui.settings.SettingsScreen;
import com.qsoftware.forgemod.pc.common.computerapi.Computer;
import com.qsoftware.forgemod.pc.common.device.PowerSwitch;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class ComputerTileEntity extends TileEntity implements ITickableTileEntity {
    private PowerSwitch powerSwitch;

    public ComputerTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick() {
        this.powerSwitch.tick();
    }
}
