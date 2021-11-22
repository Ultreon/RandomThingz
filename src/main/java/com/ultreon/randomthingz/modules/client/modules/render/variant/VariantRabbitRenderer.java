package com.ultreon.randomthingz.modules.client.modules.render.variant;

import com.ultreon.randomthingz.modules.client.modules.MobVariantsModule;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.ResourceLocation;

public class VariantRabbitRenderer extends RabbitRenderer {

    public VariantRabbitRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(RabbitEntity entity) {
        return MobVariantsModule.getTextureOrShiny(entity, MobVariantsModule.VariantTextureType.RABBIT, () -> super.getEntityTexture(entity));
    }

}
