package com.qtech.forgemod.pc.common.computerapi;

import com.google.common.annotations.Beta;
import com.qtech.forgemod.pc.PCError;
import com.qtech.forgemod.pc.common.device.component.Disk;
import com.qtech.forgemod.pc.disk.AbstractFile;

@Beta
public class DesktopImage {
    private final AbstractFile file;

    public DesktopImage(Disk disk, AbstractFile file) {
        if (!disk.isMainDisk()) {
            throw new PCError(0, "Disk is not main disk.");
        }

        this.file = file;
    }
}
