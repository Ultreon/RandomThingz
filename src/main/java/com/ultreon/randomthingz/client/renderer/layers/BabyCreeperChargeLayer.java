package com.ultreon.randomthingz.client.renderer.layers;

import com.ultreon.randomthingz.client.model.BabyCreeperModel;
import com.ultreon.randomthingz.entity.baby.BabyCreeperEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class BabyCreeperChargeLayer extends EnergySwirlLayer<BabyCreeperEntity, BabyCreeperModel> {

    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final BabyCreeperModel creeperModel = new BabyCreeperModel(1);//Note: Use 1 instead of 2 for size

    public BabyCreeperChargeLayer(RenderLayerParent<BabyCreeperEntity, BabyCreeperModel> renderer) {
        super(renderer);
    }

    @Override
    protected float xOffset(float modifier) {
        return modifier * 0.01F;
    }

    @Nonnull
    @Override
    protected ResourceLocation getTextureLocation() {
        return LIGHTNING_TEXTURE;
    }

    @Nonnull
    @Override
    protected EntityModel<BabyCreeperEntity> model() {
        return this.creeperModel;
    }
}