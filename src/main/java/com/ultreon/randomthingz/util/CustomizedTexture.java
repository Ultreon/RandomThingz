package com.ultreon.randomthingz.util;

import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CustomizedTexture extends QFMTexture {
    private final List<Consumer<Graphics2D>> actions = new ArrayList<>();
    private final InputStream stream;

    public CustomizedTexture(ResourceLocation location) throws IOException {
        Resource resource = Minecraft.getInstance().getResourceManager().getResource(location);
        stream = resource.getInputStream();
    }


    @SneakyThrows
    @Override
    public BufferedImage render() {
        BufferedImage image = ImageIO.read(this.stream);
        Graphics2D graphics = image.createGraphics();
        for (Consumer<Graphics2D> action : this.actions) {
            action.accept(graphics);
        }

        return image;
    }

    public InputStream getStream() {
        return stream;
    }
}
