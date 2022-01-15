package com.ultreon.randomthingz.pc.memory;

import net.minecraft.nbt.ByteArrayTag;

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

    public Memory(int totalSize, ByteArrayTag data) {
        this.totalSize = totalSize;
        this.data = data.getAsByteArray();
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
