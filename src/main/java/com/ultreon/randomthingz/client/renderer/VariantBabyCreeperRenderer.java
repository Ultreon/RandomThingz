package com.ultreon.randomthingz.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.client.MobVariantsModule;
import com.ultreon.randomthingz.client.model.BabyCreeperModel;
import com.ultreon.randomthingz.client.renderer.layers.BabyCreeperChargeLayer;
import com.ultreon.randomthingz.entity.baby.BabyCreeperEntity;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.annotation.Nonnull;

/**
 * Copy of vanilla's creeper render, modified to use our own model/layer that is properly scaled
 */
public class VariantBabyCreeperRenderer extends MobRenderer<BabyCreeperEntity, BabyCreeperModel> {

    private static final ResourceLocation CREEPER_TEXTURES = new ResourceLocation("textures/entity/creeper/creeper.png");

    public VariantBabyCreeperRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn, new BabyCreeperModel(), 0.5F);
        this.addLayer(new BabyCreeperChargeLayer(this));
    }

    @Override
    protected void scale(BabyCreeperEntity creeper, PoseStack matrix, float partialTicks) {
        float f = creeper.getSwelling(partialTicks);
        float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        matrix.scale(f2, f3, f2);
    }

    @Override
    protected float getWhiteOverlayProgress(BabyCreeperEntity creeper, float partialTicks) {
        float f = creeper.getSwelling(partialTicks);
        return (int) (f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull BabyCreeperEntity entity) {
        return MobVariantsModule.getTextureOrShiny(entity, MobVariantsModule.VariantTextureType.CREEPER, MobVariantsModule.enableCreeper);
    }
}