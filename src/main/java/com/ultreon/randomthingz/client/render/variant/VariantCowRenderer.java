package com.ultreon.randomthingz.client.render.variant;

import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import com.ultreon.randomthingz.client.MobVariantsModule;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class VariantCowRenderer extends CowRenderer {

    public VariantCowRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(CowEntity entity) {
        return MobVariantsModule.getTextureOrShiny(entity, MobVariantsModule.VariantTextureType.COW, MobVariantsModule.enableCow);
    }

}
