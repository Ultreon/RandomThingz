package com.ultreon.randomthingz.pc.disk.qfs;

import com.ultreon.randomthingz.commons.IntSize;
import com.ultreon.randomthingz.pc.disk.AbstractFile;
import com.ultreon.randomthingz.pc.disk.Partition;

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
