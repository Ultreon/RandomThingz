package com.qsoftware.forgemod.modules.entities.renderer.layers;

import com.qsoftware.forgemod.modules.entities.model.ModelBabyEnderman;
import com.qsoftware.forgemod.modules.entities.objects.baby.EntityBabyEnderman;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class BabyEndermanEyesLayer extends AbstractEyesLayer<EntityBabyEnderman, ModelBabyEnderman> {

    private static final RenderType RENDER_TYPE = RenderType.getEyes(new ResourceLocation("textures/entity/enderman/enderman_eyes.png"));

    public BabyEndermanEyesLayer(IEntityRenderer<EntityBabyEnderman, ModelBabyEnderman> renderer) {
        super(renderer);
    }

    @Nonnull
    @Override
    public RenderType getRenderType() {
        return RENDER_TYPE;
    }
}