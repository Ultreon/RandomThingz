package com.ultreon.texturedmodels.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.client.model.IModelLoader;

public class PressurePlateFrameModelLoader implements IModelLoader<PressurePlateFrameModelGeometry> {
    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    @Override
    public PressurePlateFrameModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
        return new PressurePlateFrameModelGeometry();
    }
}
