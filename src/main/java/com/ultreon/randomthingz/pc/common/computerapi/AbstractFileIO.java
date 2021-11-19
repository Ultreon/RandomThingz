package com.ultreon.randomthingz.pc.common.computerapi;

public abstract class AbstractFileIO {
    public abstract byte read();
    public abstract byte[] read(int len);
    public abstract void write(byte b);
    public abstract void write(byte[] bytes);

    public abstract void seek(long offset);
    public abstract long tell();
}
