package com.ultreon.randomthingz.client.renderer.layers;

import com.ultreon.randomthingz.client.model.BabyEnderman;
import com.ultreon.randomthingz.entity.baby.BabyEndermanEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class BabyEndermanEyesLayer extends EyesLayer<BabyEndermanEntity, BabyEnderman> {

    private static final RenderType RENDER_TYPE = RenderType.eyes(new ResourceLocation("textures/entity/enderman/enderman_eyes.png"));

    public BabyEndermanEyesLayer(RenderLayerParent<BabyEndermanEntity, BabyEnderman> renderer) {
        super(renderer);
    }

    @Nonnull
    @Override
    public RenderType renderType() {
        return RENDER_TYPE;
    }
}