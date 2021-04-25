package com.qtech.forgemod.pc.disk;

import com.google.common.annotations.Beta;
import com.qtech.forgemod.pc.common.device.component.Disk;

import java.io.IOException;
import java.util.UUID;

@Beta
public class Partition {
    private final Disk disk;
    private final long offset;
    private final long length;
    private final UUID id;
    private FileSystem fileSystem;

    public Partition(Disk disk, long offset, long length) throws IOException {
        this.disk = disk;

        this.offset = offset;
        this.length = length;

        disk.getChannel().position(offset);
        this.id = disk.readUUID();
    }

    public AbstractFile getFile() {
        return null;
    }

    public Disk getDisk() {
        return disk;
    }

    public long getOffset() {
        return offset;
    }

    public long getLength() {
        return length;
    }

    public UUID getId() {
        return id;
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }
}
