package com.ultreon.randomthingz.client.renderer.layers;

import com.ultreon.randomthingz.client.model.BabyCreeperModel;
import com.ultreon.randomthingz.entity.baby.BabyCreeper;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BabyCreeperChargeLayer<T extends BabyCreeper> extends EnergySwirlLayer<T, BabyCreeperModel<T>> {

    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final BabyCreeperModel<T> creeperModel;

    public BabyCreeperChargeLayer(BabyCreeperModel<T> model, RenderLayerParent<T, BabyCreeperModel<T>> renderer) {
        super(renderer);

        this.creeperModel = model;
    }

    @Override
    protected float xOffset(float modifier) {
        return modifier * .01f;
    }

    @NotNull
    @Override
    protected ResourceLocation getTextureLocation() {
        return LIGHTNING_TEXTURE;
    }

    @Override
    protected @NotNull EntityModel<T> model() {
        return this.creeperModel;
    }
}