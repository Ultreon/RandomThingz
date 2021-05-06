package com.qtech.forgemod.pc.disk;

import com.qtech.forgemod.pc.IStream;

public interface FSEntry extends IStream {
    int length();
    void pos(int pos);
    int pos();
    byte read();
    void write(byte b);
}
