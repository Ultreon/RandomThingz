package com.ultreon.randomthingz.pc.disk;

import java.io.IOException;
import java.nio.channels.FileLock;

public class RTFileLock {
    private final FileLock lock;

    public RTFileLock(FileLock lock) {
        this.lock = lock;
    }

    public void unlock() throws IOException {
        lock.release();
    }
}
