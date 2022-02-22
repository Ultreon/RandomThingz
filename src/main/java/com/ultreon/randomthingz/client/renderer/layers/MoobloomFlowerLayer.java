package com.ultreon.randomthingz.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.ultreon.randomthingz.client.model.MoobloomModel;
import com.ultreon.randomthingz.entity.Moobloom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Moobloom flower layer class.
 *
 * @author Qboi123
 */
@SuppressWarnings({"deprecation", "unused"})
@OnlyIn(Dist.CLIENT)
public class MoobloomFlowerLayer<T extends Moobloom> extends RenderLayer<T, MoobloomModel<T>> {
    public MoobloomFlowerLayer(RenderLayerParent<T, MoobloomModel<T>> rendererIn) {
        super(rendererIn);
    }

    public void render(@NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn, @SuppressWarnings("SpellCheckingInspection") T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.isBaby() && !entitylivingbaseIn.isInvisible()) {
            BlockRenderDispatcher blockRenderDispatcher = Minecraft.getInstance().getBlockRenderer();
            BlockState blockState = entitylivingbaseIn.getMoobloomType().getRenderState();

            int i = LivingEntityRenderer.getOverlayCoords(entitylivingbaseIn, 0f);
            matrixStackIn.pushPose();
            matrixStackIn.translate(.2f, -.35f, 0.5D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-48f));
            matrixStackIn.scale(-1f, -1f, 1f);
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            blockRenderDispatcher.renderSingleBlock(blockState, matrixStackIn, bufferIn, packedLightIn, i);
            matrixStackIn.popPose();

            matrixStackIn.pushPose();
            matrixStackIn.translate(.2f, -.35f, 0.5D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(42f));
            matrixStackIn.translate(.1f, 0.0D, -.6f);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-48f));
            matrixStackIn.scale(-1f, -1f, 1f);
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            blockRenderDispatcher.renderSingleBlock(blockState, matrixStackIn, bufferIn, packedLightIn, i);
            matrixStackIn.popPose();

            matrixStackIn.pushPose();
            this.getParentModel().getHead().translateAndRotate(matrixStackIn);
            matrixStackIn.translate(0.0D, -.7f, -.2f);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-78f));
            matrixStackIn.scale(-1f, -1f, 1f);
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            blockRenderDispatcher.renderSingleBlock(blockState, matrixStackIn, bufferIn, packedLightIn, i);
            matrixStackIn.popPose();
        }
    }
}
