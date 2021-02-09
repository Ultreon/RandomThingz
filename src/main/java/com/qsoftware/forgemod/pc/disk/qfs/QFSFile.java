package com.qsoftware.forgemod.pc.disk.qfs;

import com.qsoftware.forgemod.common.IntSize;
import com.qsoftware.forgemod.pc.disk.AbstractFile;
import com.qsoftware.forgemod.pc.disk.Partition;

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
