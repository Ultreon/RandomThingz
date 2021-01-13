package com.qsoftware.forgemod.pc.disk;

import com.google.common.annotations.Beta;
import com.qsoftware.forgemod.pc.Disk;

@Beta
public abstract class FileSystem {
    protected abstract void setPos(int pos);
    protected abstract int getPos();
    protected abstract void write(byte data);
    protected abstract byte read();
    public abstract AbstractFile getFileAt(String path);
}
