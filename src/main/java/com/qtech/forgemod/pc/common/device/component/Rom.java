package com.qtech.forgemod.pc.common.device.component;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Rom {
    private final byte[] bytecode = new byte[0xffff];

    public static Rom empty() {
        return new Rom(new byte[0xffff]);
    }

    private Rom(byte[] bytecode) {
        System.arraycopy(bytecode, 0, this.bytecode, 0, bytecode.length);
    }

    public static Rom readFile(Path path) throws IOException {
        return new Rom(Files.readAllBytes(path));
    }

    public static Rom readFile(String filePath) throws IOException {
        return new Rom(Files.readAllBytes(Paths.get(filePath)));
    }

    public static Rom readFile(File file) throws IOException {
        return new Rom(Files.readAllBytes(file.toPath()));
    }

    public static Rom of(byte[] bytes) {
        return new Rom(bytes);
    }

    public static Rom fromHex(String hex) throws DecoderException {
        return new Rom(Hex.decodeHex(hex));
    }

    public void set(int index, byte val) {
        bytecode[index] = val;
    }

    public byte get(int index) {
        return bytecode[index];
    }

    public byte[] getBytecode() {
        return bytecode;
    }
}
