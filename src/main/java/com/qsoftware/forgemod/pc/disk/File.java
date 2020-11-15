package com.qsoftware.forgemod.pc.disk;

import com.google.common.annotations.Beta;
import com.qsoftware.forgemod.pc.Disk;

@Beta
public class File {
    private final Partition partition;
    public String path;

    public File(Partition partition, String path) {
        this.partition = partition;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void rename(String to) {

    }

    public String getFileName() {
        String[] split = path.split("/");
        return split[split.length - 1];
    }

    public Partition getPartition() {
        return partition;
    }
}
