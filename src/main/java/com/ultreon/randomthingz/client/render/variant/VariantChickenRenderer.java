package com.ultreon.randomthingz.client.render.variant;

import com.ultreon.randomthingz.client.MobVariantsModule;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Chicken;

public class VariantChickenRenderer extends ChickenRenderer {

    public VariantChickenRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(Chicken entity) {
        return MobVariantsModule.getTextureOrShiny(entity, MobVariantsModule.VariantTextureType.CHICKEN, MobVariantsModule.enableChicken);
    }

}
