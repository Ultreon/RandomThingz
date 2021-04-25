package com.qtech.forgemod.modules.environment.client.renderer.layers;

import com.qtech.forgemod.modules.environment.client.model.BabyEnderman;
import com.qtech.forgemod.modules.environment.entities.baby.BabyEndermanEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class BabyEndermanEyesLayer extends AbstractEyesLayer<BabyEndermanEntity, BabyEnderman> {

    private static final RenderType RENDER_TYPE = RenderType.getEyes(new ResourceLocation("textures/entity/enderman/enderman_eyes.png"));

    public BabyEndermanEyesLayer(IEntityRenderer<BabyEndermanEntity, BabyEnderman> renderer) {
        super(renderer);
    }

    @Nonnull
    @Override
    public RenderType getRenderType() {
        return RENDER_TYPE;
    }
}