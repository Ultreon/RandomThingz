package com.qsoftware.forgemod.client.renderer.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.qsoftware.forgemod.client.renderer.layers.MoobloomFlowerLayer;
import com.qsoftware.forgemod.init.ModBlocks;
import com.qsoftware.forgemod.objects.entities.MoobloomEntityOld;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Moobloom flower layer class.
 *
 * @deprecated Use {@link MoobloomFlowerLayer} instead.
 * @author Qboi123
 */
@Deprecated
@OnlyIn(Dist.CLIENT)
public class MoobloomFlowerLayerOld<T extends MoobloomEntityOld> extends LayerRenderer<T, CowModel<T>> {
    public MoobloomFlowerLayerOld(IEntityRenderer<T, CowModel<T>> rendererIn) {
        super(rendererIn);
    }

    public void render(@NotNull MatrixStack matrixStackIn, @NotNull IRenderTypeBuffer bufferIn, int packedLightIn, @SuppressWarnings("SpellCheckingInspection") T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.isChild() && !entitylivingbaseIn.isInvisible()) {
            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
            BlockState blockstate = ModBlocks.BUTTERCUP.get().getDefaultState();
//         BlockState blockstate = Blocks.TORCH.getDefaultState();
            int i = LivingRenderer.getPackedOverlay(entitylivingbaseIn, 0.0F);
            matrixStackIn.push();
            matrixStackIn.translate(0.2F, -0.35F, 0.5D);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-48.0F));
            matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            blockrendererdispatcher.renderBlock(blockstate, matrixStackIn, bufferIn, packedLightIn, i);
            matrixStackIn.pop();
            matrixStackIn.push();
            matrixStackIn.translate(0.2F, -0.35F, 0.5D);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(42.0F));
            matrixStackIn.translate(0.1F, 0.0D, -0.6F);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-48.0F));
            matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            blockrendererdispatcher.renderBlock(blockstate, matrixStackIn, bufferIn, packedLightIn, i);
            matrixStackIn.pop();
            matrixStackIn.push();
            this.getEntityModel().getHead().translateRotate(matrixStackIn);
            matrixStackIn.translate(0.0D, -0.7F, -0.2F);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-78.0F));
            matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            blockrendererdispatcher.renderBlock(blockstate, matrixStackIn, bufferIn, packedLightIn, i);
            matrixStackIn.pop();
        }
    }
}
