package com.qtech.forgemod.pc.disk.qfs;

import com.qtech.forgemod.commons.IntSize;
import com.qtech.forgemod.pc.disk.AbstractFile;
import com.qtech.forgemod.pc.disk.Partition;

public class QFSFile extends AbstractFile {
    public QFSFile(Partition partition, String path) {
        super(partition, path);

        if (partition.getFileSystem() instanceof QFileSystem) {

        }
    }

    @Override
    public void rename(String to) {

    }

    @Override
    public void create(IntSize size) {

    }
}
