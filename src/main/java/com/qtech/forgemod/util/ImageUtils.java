package com.qtech.forgemod.util;

import net.minecraft.client.renderer.texture.NativeImage;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtils {
    public static NativeImage toNativeImage(BufferedImage image) throws IOException {
        return NativeImage.read(BufferUtil.toByteBuffer(image));
    }
}
