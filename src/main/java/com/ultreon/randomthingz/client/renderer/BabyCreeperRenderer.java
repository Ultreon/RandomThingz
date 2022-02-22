package com.ultreon.randomthingz.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.client.model.BabyCreeperModel;
import com.ultreon.randomthingz.client.renderer.layers.BabyCreeperChargeLayer;
import com.ultreon.randomthingz.entity.baby.BabyCreeper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

/**
 * Copy of vanilla's creeper render, modified to use our own model/layer that is properly scaled
 */
public class BabyCreeperRenderer extends MobRenderer<BabyCreeper, BabyCreeperModel<BabyCreeper>> {

    private static final ResourceLocation CREEPER_TEXTURES = new ResourceLocation("textures/entity/creeper/creeper.png");

    public BabyCreeperRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new BabyCreeperModel<>(ctx.bakeLayer(BabyCreeperModel.LAYER_LOCATION)), .5f);
        this.addLayer(new BabyCreeperChargeLayer<>(model, this));
    }

    @Override
    protected void scale(BabyCreeper creeper, PoseStack matrix, float partialTicks) {
        float swelling = creeper.getSwelling(partialTicks);
        float scaleBase = 1f + Mth.sin(swelling * 100f) * swelling * .01f;
        swelling = Mth.clamp(swelling, 0f, 1f);
        swelling = swelling * swelling;
        swelling = swelling * swelling;
        float horizontalScale = (1f + swelling * .4f) * scaleBase;
        float verticalScale = (1f + swelling * .1f) / scaleBase;
        matrix.scale(horizontalScale, verticalScale, horizontalScale);
    }

    @Override
    protected float getWhiteOverlayProgress(BabyCreeper creeper, float partialTicks) {
        float swelling = creeper.getSwelling(partialTicks);
        return (int) (swelling * 10f) % 2 == 0 ? 0f : Mth.clamp(swelling, .5f, 1f);
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull BabyCreeper entity) {
        return CREEPER_TEXTURES;
    }
}