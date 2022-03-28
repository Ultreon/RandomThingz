package com.ultreon.randomthingz.pc.common.device;

import com.ultreon.randomthingz.block.entity.ComputerBlockEntity;

public class SecureBios extends AbstractBios {
    public SecureBios(ComputerBlockEntity te, PowerController controller) {
        super(te.getComputer(), controller);
    }

    public void boot() {

    }

    public void forceShutdown() {

    }
}
