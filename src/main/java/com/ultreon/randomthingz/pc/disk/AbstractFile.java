package com.ultreon.randomthingz.pc.disk;

import com.google.common.annotations.Beta;
import com.ultreon.randomthingz.common.IntSize;

@Beta
public abstract class AbstractFile {
    private final Partition partition;
    public String path;

    public AbstractFile(Partition partition, String path) {
        this.partition = partition;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public abstract void rename(String to);

    public abstract void create(IntSize size);

    public String getFileName() {
        String[] split = path.split("/");
        return split[split.length - 1];
    }

    public Partition getPartition() {
        return partition;
    }
}
