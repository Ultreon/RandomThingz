package com.ultreon.randomthingz.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.EmptyModelData;
import org.jetbrains.annotations.NotNull;

/**
 * Free held block layer class.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class CustomHeldBlockLayer<T extends EnderMan, M extends EndermanModel<T>> extends RenderLayer<T, M> {
    public CustomHeldBlockLayer(RenderLayerParent<T, M> p_i50949_1_) {
        super(p_i50949_1_);
    }

    public void render(@NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        BlockState blockstate = entityLivingBaseIn.getCarriedBlock();
        if (blockstate != null) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.0D, 0.6875D, -0.75D);
            matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(20.0f));
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(45.0f));
            matrixStackIn.translate(0.25D, 0.1875D, 0.25D);

            //noinspection unused
            float f = 0.5F;

            matrixStackIn.scale(-0.5F, -0.5F, 0.5F);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90.0f));
            Minecraft.getInstance().getBlockRenderer().renderBlock(blockstate, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
            matrixStackIn.popPose();
        }
    }
}