package com.ultreon.randomthingz.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.client.MobVariantsModule;
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
public class BabyCreeperVariantRenderer extends MobRenderer<BabyCreeper, BabyCreeperModel<BabyCreeper>> {

    private static final ResourceLocation CREEPER_TEXTURES = new ResourceLocation("textures/entity/creeper/creeper.png");

    public BabyCreeperVariantRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new BabyCreeperModel<>(ctx.bakeLayer(BabyCreeperModel.LAYER_LOCATION)), .5f);
        this.addLayer(new BabyCreeperChargeLayer<>(model, this));
    }

    @Override
    protected void scale(BabyCreeper creeper, PoseStack matrix, float partialTicks) {
        float f = creeper.getSwelling(partialTicks);
        float f1 = 1f + Mth.sin(f * 100f) * f * .01f;
        f = Mth.clamp(f, 0f, 1f);
        f = f * f;
        f = f * f;
        float f2 = (1f + f * .4f) * f1;
        float f3 = (1f + f * .1f) / f1;
        matrix.scale(f2, f3, f2);
    }

    @Override
    protected float getWhiteOverlayProgress(BabyCreeper creeper, float partialTicks) {
        float f = creeper.getSwelling(partialTicks);
        return (int) (f * 10f) % 2 == 0 ? 0f : Mth.clamp(f, .5f, 1f);
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull BabyCreeper entity) {
        return MobVariantsModule.getTextureOrShiny(entity, MobVariantsModule.VariantTextureType.CREEPER, MobVariantsModule.enableCreeper);
    }
}