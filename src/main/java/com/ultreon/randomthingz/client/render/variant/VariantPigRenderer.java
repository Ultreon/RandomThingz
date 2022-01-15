package com.ultreon.randomthingz.client.render.variant;

import com.ultreon.randomthingz.client.MobVariantsModule;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Pig;

public class VariantPigRenderer extends PigRenderer {

    public VariantPigRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(Pig entity) {
        return MobVariantsModule.getTextureOrShiny(entity, MobVariantsModule.VariantTextureType.PIG, MobVariantsModule.enablePig);
    }

}
