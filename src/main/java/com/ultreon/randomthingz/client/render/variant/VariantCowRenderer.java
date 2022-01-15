package com.ultreon.randomthingz.client.render.variant;

import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import com.ultreon.randomthingz.client.MobVariantsModule;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cow;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class VariantCowRenderer extends CowRenderer {

    public VariantCowRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(Cow entity) {
        return MobVariantsModule.getTextureOrShiny(entity, MobVariantsModule.VariantTextureType.COW, MobVariantsModule.enableCow);
    }

}
