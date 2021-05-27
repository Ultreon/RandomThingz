package com.qtech.randomthingz.pc.disk;

import com.google.common.annotations.Beta;

import java.io.IOException;

@Beta
public abstract class FileSystem {
    protected abstract void setPos(int pos);
    protected abstract int getPos();
    protected abstract void write(byte data) throws IOException;
    protected abstract byte read();
    public abstract AbstractFile getFileAt(String path);
}
