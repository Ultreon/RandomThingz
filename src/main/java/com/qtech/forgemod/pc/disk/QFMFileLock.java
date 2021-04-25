package com.qtech.forgemod.pc.disk;

import java.io.IOException;
import java.nio.channels.FileLock;

public class QFMFileLock {
    private final FileLock lock;

    public QFMFileLock(FileLock lock) {
        this.lock = lock;
    }

    public void unlock() throws IOException {
        lock.release();
    }
}
