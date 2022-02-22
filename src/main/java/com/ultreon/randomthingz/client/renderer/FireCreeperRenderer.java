package com.ultreon.randomthingz.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.renderer.layers.CustomCreeperPowerLayer;
import com.ultreon.randomthingz.entity.FireCreeper;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
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
public class FireCreeperRenderer extends MobRenderer<FireCreeper, CreeperModel<FireCreeper>> {
    private static final ResourceLocation CREEPER_TEXTURES = new ResourceLocation(RandomThingz.MOD_ID, "textures/entity/creeper/fire.png");
//    private static final RenderType RENDER_TYPE = RenderType.getEyes(new ResourceLocation(RandomThingz.MOD_ID, "textures/entity/creeper/fire_eyes.png"));

    public FireCreeperRenderer(EntityRendererProvider.Context p_173958_) {
        super(p_173958_, new CreeperModel<>(p_173958_.bakeLayer(ModelLayers.CREEPER)), .5f);
        this.addLayer(new CustomCreeperPowerLayer<>(this, p_173958_.getModelSet()));
    }

    @Override
    protected void scale(FireCreeper entityLivingBaseIn, PoseStack matrixStackIn, float partialTickTime) {
        float f = entityLivingBaseIn.getSwelling(partialTickTime);
        float f1 = 1f + Mth.sin(f * 100f) * f * .01f;
        f = Mth.clamp(f, 0f, 1f);
        f = f * f;
        f = f * f;
        float f2 = (1f + f * .4f) * f1;
        float f3 = (1f + f * .1f) / f1;
        matrixStackIn.scale(f2, f3, f2);
    }

    @Override
    protected float getWhiteOverlayProgress(FireCreeper livingEntityIn, float partialTicks) {
        float f = livingEntityIn.getSwelling(partialTicks);
        return (int) (f * 10f) % 2 == 0 ? 0f : Mth.clamp(f, .5f, 1f);
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FireCreeper entity) {
        return CREEPER_TEXTURES;
    }
}
