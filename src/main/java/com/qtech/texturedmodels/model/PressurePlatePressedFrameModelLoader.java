package com.qtech.texturedmodels.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.client.model.IModelLoader;

public class PressurePlatePressedFrameModelLoader implements IModelLoader<PressurePlatePressedFrameModelGeometry> {
    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    @Override
    public PressurePlatePressedFrameModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
        return new PressurePlatePressedFrameModelGeometry();
    }
}
