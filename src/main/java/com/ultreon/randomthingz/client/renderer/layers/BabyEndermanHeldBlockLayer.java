package com.ultreon.randomthingz.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.ultreon.randomthingz.client.model.BabyEndermanModel;
import com.ultreon.randomthingz.entity.baby.BabyEnderman;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BabyEndermanHeldBlockLayer extends RenderLayer<BabyEnderman, BabyEndermanModel> {

    public BabyEndermanHeldBlockLayer(RenderLayerParent<BabyEnderman, BabyEndermanModel> renderer) {
        super(renderer);
    }

    @Override
    public void render(@NotNull PoseStack matrix, @NotNull MultiBufferSource renderer, int light, BabyEnderman enderman, float limbSwing, float limbSwingAmount,
                       float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        BlockState blockstate = enderman.getCarriedBlock();
        if (blockstate != null) {
            matrix.pushPose();
            matrix.translate(0, 0.6875, -0.75);
            matrix.mulPose(Vector3f.XP.rotationDegrees(20));
            matrix.mulPose(Vector3f.YP.rotationDegrees(45));
            matrix.translate(0.25, 0.1875, 0.25);
            //Modify scale of block to be 3/4 of what it is for the adult enderman
            float scale = .375f;
            matrix.scale(-scale, -scale, scale);
            matrix.mulPose(Vector3f.YP.rotationDegrees(90));
            //Adjust the position of the block to actually look more like it is in the enderman's hands
            matrix.translate(0, -1, 0.25);
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(blockstate, matrix, renderer, light, OverlayTexture.NO_OVERLAY);
            matrix.popPose();
        }
    }
}