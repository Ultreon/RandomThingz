package com.ultreon.texturedmodels.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.model.IModelLoader;

public class IllusionBedModelLoader implements IModelLoader<IllusionBedModelGeometry> {
    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {

    }

    @Override
    public IllusionBedModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
        return new IllusionBedModelGeometry();
    }
}
