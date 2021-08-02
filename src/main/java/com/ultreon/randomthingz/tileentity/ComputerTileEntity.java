package com.ultreon.randomthingz.tileentity;

import com.ultreon.randomthingz.pc.common.device.PowerSwitch;
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
