package com.ultreon.randomthingz.block.entity;

import com.ultreon.randomthingz.pc.common.computerapi.Computer;
import com.ultreon.randomthingz.pc.common.device.AbstractBios;

public class UltreonPC extends Computer {
    public UltreonPC(ComputerTileEntity tileEntity) {
        super(tileEntity);
    }

    @Override
    public AbstractBios createBios() {
        return new UltreonBios(this, tileEntity);
    }

    @Override
    public void requestShutdown() {
        tileEntity.shutdown();
    }
}
