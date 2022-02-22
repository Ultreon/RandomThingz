package com.ultreon.randomthingz.pc.common.device;

import com.ultreon.randomthingz.tileentity.ComputerTileEntity;

public class SecureBios extends AbstractBios {
    public SecureBios(ComputerTileEntity te, PowerController controller) {
        super(te.getComputer(), controller);
    }

    public void boot() {

    }

    public void forceShutdown() {

    }
}
