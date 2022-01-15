package com.ultreon.randomthingz.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.renderer.layers.CustomCreeperChargeLayer;
import com.ultreon.randomthingz.entity.FireCreeperEntity;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Fire creeper entity renderer class.
 * Todo: make eyes emissive.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class FireCreeperRenderer extends MobRenderer<FireCreeperEntity, CreeperModel<FireCreeperEntity>> {
    private static final ResourceLocation CREEPER_TEXTURES = new ResourceLocation(RandomThingz.MOD_ID, "textures/entity/creeper/fire.png");
//    private static final RenderType RENDER_TYPE = RenderType.getEyes(new ResourceLocation(RandomThingz.MOD_ID, "textures/entity/creeper/fire_eyes.png"));

    public FireCreeperRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn, new CreeperModel<>(), 0.5F);
        this.addLayer(new CustomCreeperChargeLayer<>(this, model));
    }

    @Override
    protected void scale(FireCreeperEntity entityLivingBaseIn, PoseStack matrixStackIn, float partialTickTime) {
        float f = entityLivingBaseIn.getSwelling(partialTickTime);
        float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        matrixStackIn.scale(f2, f3, f2);
    }

    @Override
    protected float getWhiteOverlayProgress(FireCreeperEntity livingEntityIn, float partialTicks) {
        float f = livingEntityIn.getSwelling(partialTicks);
        return (int) (f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FireCreeperEntity entity) {
        return CREEPER_TEXTURES;
    }
}
