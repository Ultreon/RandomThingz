package com.qtech.forgemod.pc.disk;

import com.qtech.forgemod.pc.common.device.component.Disk;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.channels.FileLock;

public class PartitionTable {
    private final Disk disk;
    private final FileLock lock;

    public PartitionTable(Disk disk, FileLock lock) {
        this.disk = disk;
        this.lock = lock;
    }

    public Partition getPartition(int index) throws IOException {
        if (index > 255) {
            return null;
        }
        if (index < 0) {
            return null;
        }

        disk.getChannel().position(disk.size() - ((index + 1) * 32L));
        ObjectInputStream objectInputStream = new ObjectInputStream(disk.getInput());
        long offset = objectInputStream.readLong();
        long length = objectInputStream.readLong();

        return new Partition(disk, offset, length);
    }

    public void close() throws IOException {
        this.lock.release();
    }
}
