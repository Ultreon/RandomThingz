package com.qsoftware.forgemod.pc.disk;

import com.qsoftware.forgemod.pc.IStream;

public interface FSEntry extends IStream {
    int length();
    void pos(int pos);
    int pos();
    byte read();
    void write(byte b);
}
