package com.ultreon.randomthingz.client.render.variant;

import com.ultreon.randomthingz.client.MobVariantsModule;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Llama;
import org.jetbrains.annotations.NotNull;

public class VariantLlamaRenderer extends LlamaRenderer {

    public VariantLlamaRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, ModelLayers.LLAMA);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Llama entity) {
        return MobVariantsModule.getTextureOrShiny(entity, MobVariantsModule.VariantTextureType.LLAMA, () -> super.getTextureLocation(entity));
    }

}
