package com.ultreon.randomthingz.block.entity;

import com.ultreon.randomthingz.pc.common.computerapi.Computer;
import com.ultreon.randomthingz.pc.common.device.AbstractBios;
import com.ultreon.randomthingz.pc.common.device.PowerController;

public class UltreonBios extends AbstractBios {
    public UltreonBios(Computer computer, ComputerTileEntity te) {
        super(computer, new PowerController(computer) {
            @Override
            public void tick() {

            }
        });
    }
}
