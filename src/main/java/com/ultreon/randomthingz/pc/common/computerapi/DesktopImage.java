package com.ultreon.randomthingz.pc.common.computerapi;

import com.google.common.annotations.Beta;
import com.ultreon.randomthingz.pc.PCError;
import com.ultreon.randomthingz.pc.common.device.component.Disk;
import com.ultreon.randomthingz.pc.disk.AbstractFile;

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
