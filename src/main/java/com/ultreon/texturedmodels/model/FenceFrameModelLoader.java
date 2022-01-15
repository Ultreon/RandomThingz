package com.ultreon.texturedmodels.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.model.IModelLoader;

public class FenceFrameModelLoader implements IModelLoader<FenceFrameModelGeometry> {
    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {

    }

    @Override
    public FenceFrameModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
        return new FenceFrameModelGeometry();
    }
}
