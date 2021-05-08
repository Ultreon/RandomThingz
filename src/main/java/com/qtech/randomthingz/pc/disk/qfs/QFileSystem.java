package com.qtech.randomthingz.pc.disk.qfs;

import com.qtech.randomthingz.pc.common.device.component.Disk;
import com.qtech.randomthingz.pc.disk.AbstractFile;
import com.qtech.randomthingz.pc.disk.FileSystem;

import java.io.IOException;

public class QFileSystem extends FileSystem {
    private final Disk disk;
    private int pos;

    public QFileSystem(Disk disk) {
//        disk.readByte(disk);
        this.disk = disk;
    }

    @Override
    protected void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    protected int getPos() {
        return this.pos;
    }

    @Override
    protected void write(byte data) throws IOException {
        this.disk.getChannel().position(getPos());
        this.disk.getOutput().write(data);
    }

    @Override
    protected byte read() {
        return 0;
    }

    @Override
    public AbstractFile getFileAt(String path) {
        return null;
    }
}
