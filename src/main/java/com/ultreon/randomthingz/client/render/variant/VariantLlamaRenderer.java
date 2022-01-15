package com.ultreon.randomthingz.client.render.variant;

import com.ultreon.randomthingz.client.MobVariantsModule;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Llama;

public class VariantLlamaRenderer extends LlamaRenderer {

    public VariantLlamaRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(Llama entity) {
        return MobVariantsModule.getTextureOrShiny(entity, MobVariantsModule.VariantTextureType.LLAMA, () -> super.getTextureLocation(entity));
    }

}
