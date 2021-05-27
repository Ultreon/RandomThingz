package com.qtech.randomthingz.pc.disk.qfs;

import com.qtech.randomthingz.commons.IntSize;
import com.qtech.randomthingz.pc.disk.AbstractFile;
import com.qtech.randomthingz.pc.disk.Partition;

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
