package com.qsoftware.forgemod.pc;

import com.google.common.annotations.Beta;
import com.qsoftware.forgemod.pc.disk.Partition;

@Beta
public class Disk {
    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private final Partition[] partitions;

    public Disk() {
        partitions = new Partition[255];
    }

    public Partition[] getPartitions() {
        return partitions.clone();
    }

    public boolean isMainDisk() {
        return false;
    }
}
