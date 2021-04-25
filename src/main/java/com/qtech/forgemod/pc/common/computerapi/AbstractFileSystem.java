package com.qtech.forgemod.pc.common.computerapi;

import org.lwjgl.system.CallbackI;

public abstract class AbstractFileSystem {
    public abstract <T, V> void request(FSRequest<T, V> reqType, T req, V val);

    public abstract AbstractFileIO getFile(File file);
}
