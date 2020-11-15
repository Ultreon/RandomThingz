package com.qsoftware.forgemod.pc;

import com.google.common.annotations.Beta;
import com.qsoftware.forgemod.pc.disk.File;

@Beta
public class DesktopImage {
    private final File file;

    public DesktopImage(Disk disk, File file) {
        if (!disk.isMainDisk()) {
            throw new PCError(0, "Disk is not main disk.");
        }

        this.file = file;
    }
}
