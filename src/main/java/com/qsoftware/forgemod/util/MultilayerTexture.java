package com.qsoftware.forgemod.util;

import com.google.common.collect.Maps;
import com.qsoftware.forgemod.common.java.lists.DynamicList;
import org.apache.logging.log4j.util.SortedArrayStringMap;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

public class MultilayerTexture extends QFMTexture {
    private final List<QFMTexture> textures = new ArrayList<>();

    public MultilayerTexture() {

    }

    public void addTexture(QFMTexture texture) {
        this.textures.add(texture);
    }

    public BufferedImage render() {
        return render(16, 16);
    }

    public BufferedImage render(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(new Color(0, 0, 0, 0));
        graphics.fillRect(0, 0, width, height);

        for (QFMTexture texture : textures) {
            BufferedImage render = texture.render();
            graphics.drawImage(render, 0, 0, render.getWidth(), render.getHeight(), null);
        }

        return image;
    }
}
