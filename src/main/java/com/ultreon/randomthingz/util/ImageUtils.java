package com.ultreon.randomthingz.util;

import com.mojang.blaze3d.platform.NativeImage;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtils {
    public static NativeImage toNativeImage(BufferedImage image) throws IOException {
        return NativeImage.read(BufferUtil.toByteBuffer(image));
    }
}
