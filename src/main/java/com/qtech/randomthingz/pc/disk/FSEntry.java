package com.qtech.randomthingz.pc.disk;

import com.qtech.randomthingz.pc.IStream;

public interface FSEntry extends IStream {
    int length();
    void pos(int pos);
    int pos();
    byte read();
    void write(byte b);
}
