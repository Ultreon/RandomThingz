package com.qtech.randomthingz.pc.memory;

import net.minecraft.nbt.ByteArrayNBT;

public class Memory {
    private final int totalSize;
    private final byte[] data;

    public Memory(int totalSize) {
        this.totalSize = totalSize;
        this.data = new byte[totalSize];
    }

    public Memory(int totalSize, byte[] data) {
        this.totalSize = totalSize;
        this.data = data;
    }

    public Memory(int totalSize, ByteArrayNBT data) {
        this.totalSize = totalSize;
        this.data = data.getByteArray();
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void write(int offset, int b) {
        data[offset] = (byte) b;
    }

    public byte read(int offset) {
        return data[offset];
    }
}
