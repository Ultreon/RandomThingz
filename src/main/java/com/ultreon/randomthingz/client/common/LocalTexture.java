package com.ultreon.randomthingz.client.common;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LocalTexture extends DynamicTexture {
    public LocalTexture(File file) throws IOException {
        super(getNativeImage(file));
    }

    private static NativeImage getNativeImage(File file) throws IOException {
        Minecraft mc = Minecraft.getInstance();

        InputStream input = new FileInputStream(file);
        NativeImage nativeImage = NativeImage.read(input);
        input.close();
        return nativeImage;
    }
}
