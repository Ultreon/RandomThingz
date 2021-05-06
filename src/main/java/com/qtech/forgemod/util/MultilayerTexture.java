package com.qtech.forgemod.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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
