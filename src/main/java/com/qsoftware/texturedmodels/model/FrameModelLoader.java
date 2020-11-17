package com.qsoftware.texturedmodels.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.client.model.IModelLoader;

public class FrameModelLoader implements IModelLoader<FrameModelGeometry> {
    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    @Override
    public FrameModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
        return new FrameModelGeometry();
    }
}
