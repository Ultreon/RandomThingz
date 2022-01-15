package com.ultreon.randomthingz.client.render.variant;

import com.ultreon.randomthingz.client.MobVariantsModule;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Rabbit;

public class VariantRabbitRenderer extends RabbitRenderer {

    public VariantRabbitRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(Rabbit entity) {
        return MobVariantsModule.getTextureOrShiny(entity, MobVariantsModule.VariantTextureType.RABBIT, () -> super.getTextureLocation(entity));
    }

}
