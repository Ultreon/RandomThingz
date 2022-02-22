package com.ultreon.randomthingz.client.renderer.layers;

import com.ultreon.randomthingz.client.model.BabyEndermanModel;
import com.ultreon.randomthingz.entity.baby.BabyEnderman;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BabyEndermanEyesLayer extends EyesLayer<BabyEnderman, BabyEndermanModel> {

    private static final RenderType RENDER_TYPE = RenderType.eyes(new ResourceLocation("textures/entity/enderman/enderman_eyes.png"));

    public BabyEndermanEyesLayer(RenderLayerParent<BabyEnderman, BabyEndermanModel> renderer) {
        super(renderer);
    }

    @NotNull
    @Override
    public RenderType renderType() {
        return RENDER_TYPE;
    }
}