package com.qtech.randomthingz.pc.common.computerapi;

import java.io.IOException;

public abstract class FSRequest<T, V> {
    public static final FSRequest<String, Void> MKDIRS = new FSRequest<String, Void>(String.class, Void.class) {
        @Override
        public void execute(String req, Void val) throws IOException {
//            Files.createDirectories(Paths.get(QForgeMod.getInstance().onServerStarting(), req))
        }
    };

    private final Class<? extends V> value;
    private final Class<? extends T> request;

    public FSRequest(Class<? extends T> req, Class<? extends V> val) {
        this.request = req;
        this.value = val;
    }

    public abstract void execute(T req, V val) throws IOException;

    public Class<? extends V> getValue() {
        return value;
    }

    public Class<? extends T> getRequest() {
        return request;
    }
}
