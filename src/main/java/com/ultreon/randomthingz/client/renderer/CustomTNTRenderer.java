package com.ultreon.randomthingz.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.ultreon.randomthingz.entity.custom.CustomPrimedTnt;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class CustomTNTRenderer extends EntityRenderer<CustomPrimedTnt> {
    public CustomTNTRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn);
        this.shadowRadius = 0.5F;
    }

    public void render(CustomPrimedTnt entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.0D, 0.5D, 0.0D);
        if ((float) entityIn.getLife() - partialTicks + 1.0f < 10.0f) {
            float f = 1.0f - ((float) entityIn.getLife() - partialTicks + 1.0f) / 10.0f;
            f = Mth.clamp(f, 0.0f, 1.0f);
            f = f * f;
            f = f * f;
            float f1 = 1.0f + f * 0.3F;
            matrixStackIn.scale(f1, f1, f1);
        }

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-90.0f));
        matrixStackIn.translate(-0.5D, -0.5D, 0.5D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90.0f));
        TntMinecartRenderer.renderWhiteSolidBlock(entityIn.getState(), matrixStackIn, bufferIn, packedLightIn, entityIn.getLife() / 5 % 2 == 0);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    /**
     * Returns the location of an entity's texture.
     */
    @SuppressWarnings("deprecation")
    public @NotNull ResourceLocation getTextureLocation(@NotNull CustomPrimedTnt entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
