package com.qtech.forgemod.client;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.common.java.lists.DynamicList;
import com.qtech.forgemod.util.BufferUtil;
import com.qtech.forgemod.util.QFMTexture;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextureGenerator {
    @Getter
    private static final TextureGenerator instance = new TextureGenerator();
    private final Map<ResourceLocation, QFMTexture> textures = new HashMap<>();

    private TextureGenerator() {

    }

    public void addTexture(QFMTexture texture, ResourceLocation rl) {
        this.textures.put(rl, texture);
    }

    public void generate() {
        for (Map.Entry<ResourceLocation, QFMTexture> texture : this.textures.entrySet()) {
            try {
                Minecraft.getInstance().getTextureManager().readTexture(texture.getKey(), new DynamicTexture(NativeImage.read(BufferUtil.toByteBuffer(texture.getValue().render()))));
            } catch (IOException e) {
                QForgeMod.LOGGER.error("Couldn't generate texture: " + texture.getKey(), e);
            }
        }
    }
}