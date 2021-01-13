package com.qsoftware.forgemod.pc.disk;

import com.google.common.annotations.Beta;
import com.qsoftware.forgemod.pc.Disk;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;
import sun.misc.IOUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.UUID;

@Beta
public class Partition {
    private final Disk disk;
    private final long offset;
    private final long length;
    private final UUID id;

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
}
