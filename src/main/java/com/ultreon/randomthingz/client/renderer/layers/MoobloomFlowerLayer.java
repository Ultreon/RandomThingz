package com.ultreon.randomthingz.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.ultreon.randomthingz.entity.MoobloomEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
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
@SuppressWarnings("deprecation")
@OnlyIn(Dist.CLIENT)
public class MoobloomFlowerLayer<T extends MoobloomEntity> extends RenderLayer<T, CowModel<T>> {
    public MoobloomFlowerLayer(RenderLayerParent<T, CowModel<T>> rendererIn) {
        super(rendererIn);
    }

    public void render(@NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn, @SuppressWarnings("SpellCheckingInspection") T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.isBaby() && !entitylivingbaseIn.isInvisible()) {
            BlockRenderDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRenderer();
            BlockState blockState = entitylivingbaseIn.getMoobloomType().getRenderState();

            int i = LivingEntityRenderer.getOverlayCoords(entitylivingbaseIn, 0.0f);
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.2F, -0.35F, 0.5D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-48.0f));
            matrixStackIn.scale(-1.0f, -1.0f, 1.0f);
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            blockrendererdispatcher.renderSingleBlock(blockState, matrixStackIn, bufferIn, packedLightIn, i);
            matrixStackIn.popPose();

            matrixStackIn.pushPose();
            matrixStackIn.translate(0.2F, -0.35F, 0.5D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(42.0f));
            matrixStackIn.translate(0.1F, 0.0D, -0.6F);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-48.0f));
            matrixStackIn.scale(-1.0f, -1.0f, 1.0f);
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            blockrendererdispatcher.renderSingleBlock(blockState, matrixStackIn, bufferIn, packedLightIn, i);
            matrixStackIn.popPose();

            matrixStackIn.pushPose();
            this.getParentModel().getHead().translateAndRotate(matrixStackIn);
            matrixStackIn.translate(0.0D, -0.7F, -0.2F);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-78.0f));
            matrixStackIn.scale(-1.0f, -1.0f, 1.0f);
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            blockrendererdispatcher.renderSingleBlock(blockState, matrixStackIn, bufferIn, packedLightIn, i);
            matrixStackIn.popPose();
        }
    }
}
