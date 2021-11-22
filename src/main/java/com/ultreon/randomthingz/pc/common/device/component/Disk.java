package com.ultreon.randomthingz.pc.common.device.component;

import com.google.common.annotations.Beta;
import com.ultreon.randomthingz.pc.disk.PartitionTable;
import com.ultreon.randomthingz.pc.disk.QFMFileLock;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.UUID;

@Beta
public class Disk {
    private static final int DATA_OFFSET = 2048;
    private final FileChannel channel;
    private final FileReader reader;
    private final FileWriter writer;
    private final FileInputStream input;
    private final FileOutputStream output;
    ;
    private final long size;
    private final UUID guid;
    private PartitionTable partitionTable;

    public Disk(File file) throws IOException {
        this.input = new FileInputStream(file);
        this.output = new FileOutputStream(file);
        this.reader = new FileReader(this.input.getFD());
        this.writer = new FileWriter(this.output.getFD());
        this.channel = this.input.getChannel();
        this.size = file.length();
        this.channel.position(0);

        FileLock lock = this.channel.lock(0, 32, false);
        this.guid = readUUID();
        lock.release();

        this.partitionTable = new PartitionTable(this, this.channel.lock(size - 4096, 4096, false));
    }

    private void eject() throws IOException {
        this.partitionTable.close();
        this.input.close();
        this.output.close();
    }

    public UUID readUUID() {
        long msb = readLong();
        long lsb = readLong();

        return new UUID(msb, lsb);
    }

    private long readLong() {
        byte[] bytes = this.readBytes(16);

        ByteBuffer bb = ByteBuffer.wrap(bytes);
        return bb.getLong();
    }

    private byte[] readBytes(int len) {
        return new byte[len];
    }

    private PartitionTable getPartitionTable() {
        return this.partitionTable;
    }

    public boolean isMainDisk() {
        return false;
    }

    public UUID getGUID() {
        return guid;
    }

    public byte readByte(int pos) throws IOException {
        this.channel.position(pos + DATA_OFFSET);
        return (byte) this.input.read();
    }

    public Reader getReader() {
        return reader;
    }

    public Writer getWriter() {
        return writer;
    }

    public InputStream getInput() {
        return input;
    }

    public OutputStream getOutput() {
        return output;
    }

    public FileChannel getChannel() {
        return channel;
    }

    public long size() {
        return size;
    }

    public QFMFileLock lock() throws IOException {
        return new QFMFileLock(this.channel.lock());
    }

    public QFMFileLock lock(long pos, long size) throws IOException {
        return new QFMFileLock(this.channel.lock(pos, size, false));
    }

    public QFMFileLock lock(long pos, long size, boolean shared) throws IOException {
        return new QFMFileLock(this.channel.lock(pos, size, shared));
    }

    public QFMFileLock tryLock() throws IOException {
        return new QFMFileLock(this.channel.tryLock());
    }

    public QFMFileLock tryLock(long pos, long size) throws IOException {
        return new QFMFileLock(this.channel.tryLock(pos, size, false));
    }

    public QFMFileLock tryLock(long pos, long size, boolean shared) throws IOException {
        return new QFMFileLock(this.channel.tryLock(pos, size, shared));
    }
}
