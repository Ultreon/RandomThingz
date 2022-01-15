package com.ultreon.texturedmodels.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.model.IModelLoader;

public class ButtonFrameModelLoader implements IModelLoader<ButtonFrameModelGeometry> {
    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {

    }

    @Override
    public ButtonFrameModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
        return new ButtonFrameModelGeometry();
    }
}
