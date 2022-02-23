package com.ultreon.randomthingz.client;

import com.mojang.blaze3d.platform.NativeImage;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.util.BufferUtil;
import com.ultreon.randomthingz.util.RTTexture;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TextureGenerator {
    @Getter
    private static final TextureGenerator instance = new TextureGenerator();
    private final Map<ResourceLocation, RTTexture> textures = new HashMap<>();

    private TextureGenerator() {

    }

    public void addTexture(RTTexture texture, ResourceLocation rl) {
        this.textures.put(rl, texture);
    }

    public void generate() {
        for (Map.Entry<ResourceLocation, RTTexture> texture : this.textures.entrySet()) {
            try {
                Minecraft.getInstance().getTextureManager().register(texture.getKey(), new DynamicTexture(NativeImage.read(BufferUtil.toByteBuffer(texture.getValue().render()))));
            } catch (IOException e) {
                RandomThingz.LOGGER.error("Couldn't generate texture: " + texture.getKey(), e);
            }
        }
    }
}
