package com.qtech.randomthingz.client.renderer.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.qtech.randomthingz.client.model.FreeEndermanModel;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.util.math.vector.Vector3f;
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
public class CustomHeldBlockLayer<T extends EndermanEntity, M extends FreeEndermanModel<T>> extends LayerRenderer<T, M> {
    public CustomHeldBlockLayer(IEntityRenderer<T, M> p_i50949_1_) {
        super(p_i50949_1_);
    }

    public void render(@NotNull MatrixStack matrixStackIn, @NotNull IRenderTypeBuffer bufferIn, int packedLightIn, T entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        BlockState blockstate = entityLivingBaseIn.getHeldBlockState();
        if (blockstate != null) {
            matrixStackIn.push();
            matrixStackIn.translate(0.0D, 0.6875D, -0.75D);
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(20.0F));
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(45.0F));
            matrixStackIn.translate(0.25D, 0.1875D, 0.25D);

            //noinspection unused
            float f = 0.5F;

            matrixStackIn.scale(-0.5F, -0.5F, 0.5F);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F));
            Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(blockstate, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
            matrixStackIn.pop();
        }
    }
}