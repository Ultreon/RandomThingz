package com.qsoftware.forgemod.pc.disk;

import com.google.common.annotations.Beta;
import com.qsoftware.forgemod.pc.Disk;
import org.jetbrains.annotations.NotNull;

@Beta
public class Partition {
    private final Disk disk;
    private final FileSystem fileSystem;
    private final char driveLetter;

    public Partition(Disk disk, FileSystem fileSystem, char driveLetter) {
        this.disk = disk;
        this.fileSystem = fileSystem;
        this.driveLetter = driveLetter;
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }

    public File getFile() {
        return null;
    }

    public Disk getDisk() {
        return disk;
    }

    @NotNull
    public Character getDriveLetter() {
        return driveLetter;
    }
}
